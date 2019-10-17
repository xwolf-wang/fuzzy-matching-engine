package com.xwolf.os.service;

import com.xwolf.os.domain.FuzzyTrade;
import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.matching.*;
import com.xwolf.os.utils.EngineConstants;
import com.xwolf.os.utils.MatchingResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author ming
 * @Description:
 * @create 2019-10-02 7:46 AM
 **/
@Service
public class MatchingSvc {

    @Autowired
    private TradeSvc tradeSvc;

    @Autowired
    private MandatoryMatchingLogic mandatoryMatchingLogic;

    @Autowired
    FuzzyMatchingLogic fuzzyMatchingLogic;

    @Autowired
    AggregationMatchingLogic aggregationMatchingLogic;

    @Autowired
    AggregationMatchingFuzzyLogic aggregationMatchingFuzzyLogic;

    @Autowired
    AverageMatchingLogic averageMatchingLogic;

    @Autowired
    AverageMatchingFuzzyLogic averageMatchingFuzzyLogic;

    @Autowired
    ExactlyMatchLogic exactlyMatchLogic;

    @Autowired
    RuleConfigSvc ruleConfigSvc;

    public List<List<FuzzyTrade>> process(Trade tradeSideA, List<Trade> tradeBList){

        //do mandatory match
        List<Trade> mandatoryMatchingResult = exactlyMatchLogic.process(tradeSideA,tradeBList,Arrays.asList(EngineConstants.MANDATORY));

        //do fuzzy match and show the ratio
        List<FuzzyTrade> fuzzyMatchResult = fuzzyMatchingLogic.process(tradeSideA,mandatoryMatchingResult);

        //do aggregation
        List<List<FuzzyTrade>> aggregationMatchResult = aggregationMatchingFuzzyLogic.process(tradeSideA,fuzzyMatchResult);
        System.out.println("aggregationMatchResult result: size - " + aggregationMatchResult.size());
        MatchingResultUtil.print(aggregationMatchResult);

        //do average calculation
        List<List<FuzzyTrade>> averageMatchResult = averageMatchingFuzzyLogic.process(tradeSideA,aggregationMatchResult);

        return averageMatchResult;
    }

    public List<Trade> processSingle(Trade tradeSideA, List<Trade> tradeBList){

        //do mandatory match
        List<Trade> mandatoryMatchingResult = exactlyMatchLogic.process(tradeSideA,tradeBList,Arrays.asList(EngineConstants.MANDATORY, EngineConstants.FUZZY));

        //do aggregation
        List<Trade> aggregationMatchResult = aggregationMatchingLogic.processSingle(tradeSideA,mandatoryMatchingResult);
        System.out.println("aggregationMatchResult result: size - " + aggregationMatchResult.size());

        //do average calculation
        List<Trade> averageMatchResult = averageMatchingLogic.processSingle(tradeSideA,aggregationMatchResult);

        return averageMatchResult;
    }

    public Map<List<Trade>, List<Trade>> process(List<Trade> tradeAList, List<Trade> tradeBList) throws CloneNotSupportedException {

        Map<List<Trade>, List<Trade>> finalmatchresult = new HashMap<>();

        List<Trade> matchedList = new ArrayList<>();
        //get All exactly 1:1 or 1:N match
        for(Trade trade: tradeAList){
            List<Trade> result = processSingle(trade, tradeBList);
            if(!result.isEmpty()) {

                finalmatchresult.put(Arrays.asList(trade),result);

                matchedList.add(trade);
                //remove matched trade
                tradeBList.removeAll(result);
            }
        }
        //remove matched trades
        tradeAList.removeAll(matchedList);


        List<Trade> unMatchedList = new ArrayList<>();

        // get All N:N match
        while(tradeAList.size()>0) {
            Trade trade = tradeAList.get(0);

            List<Trade> mandatorySameSideMatchingResult = exactlyMatchLogic.processSameSide(trade, tradeAList, Arrays.asList(EngineConstants.MANDATORY, EngineConstants.FUZZY));

            //remove all calculated trades
            tradeAList.removeAll(mandatorySameSideMatchingResult);

            Map<List<Trade>,List<Trade>> subMatchTrades = new HashMap<>();

            //if no more
            if(mandatorySameSideMatchingResult.size() == 1)
                //add to unMatchedList
                unMatchedList.add(trade);
            else{
                MatchRule rule = ruleConfigSvc.findMatchRule(trade.getTradeType()).get();

                //get all subArrays
                List<List<Trade>> subArrays = getSubArrays(mandatorySameSideMatchingResult);

                while (subArrays.size() > 0)
                {
                    List<Trade> sub = subArrays.remove(0);
                    //get total
                    Integer sum = sub.stream().map(e -> aggregationMatchingLogic.getAggregateFieldValue(e, rule).get()).reduce(Integer::sum).get();
                    //get average
                    Double groupAvg = sub.stream().map(e->averageMatchingLogic.getAverageFieldValue(e,rule)).collect(Collectors.averagingDouble(p -> p.get()));
                    Trade targetTrade = (Trade)trade.clone();
                    aggregationMatchingLogic.setAggregateFieldValue(targetTrade, rule, sum.toString());
                    averageMatchingLogic.setAverageFieldValue(targetTrade,rule,groupAvg);
                    List<Trade> subResult = processSingle(targetTrade, tradeBList);


                    if(!subResult.isEmpty()) {
                        subMatchTrades.put(sub, subResult);
                        //delete matched trades
                        tradeBList.removeAll(subResult);
                        //remove sub from mandatory match result
                        mandatorySameSideMatchingResult.removeAll(sub);
                        //get all subArrays again
                        subArrays = getSubArrays(mandatorySameSideMatchingResult);
                    }

                }

            }
            //
            finalmatchresult.putAll(subMatchTrades);
            //set into unmatch list
            unMatchedList.addAll(mandatorySameSideMatchingResult);

        }

        System.out.println("N:N - " + finalmatchresult);

        return finalmatchresult;
    }

    public List<List<Trade>> getSubArrays(List<Trade> trades){
        int count = (int)Math.pow(2,trades.size());
        List<List<Trade>> arrays = new ArrayList<List<Trade>>();
        for(int i=1;i<count;i++){
            List<Trade> subarray = new ArrayList<Trade>();
            int temp = i;
            int index = 0;
            while (temp!=0){
                if((temp&1)==1){
                    subarray.add(trades.get(index));
                }
                index++;
                temp = temp >>1;
            }
            arrays.add(subarray);
        }
        return arrays;
    }


}
