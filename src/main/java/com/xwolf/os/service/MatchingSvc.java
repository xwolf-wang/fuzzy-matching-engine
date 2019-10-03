package com.xwolf.os.service;

import com.xwolf.os.domain.FuzzyTrade;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.matching.AggregationMatchingLogic;
import com.xwolf.os.matching.AverageMatchingLogic;
import com.xwolf.os.matching.FuzzyMatchingLogic;
import com.xwolf.os.matching.MandatoryMatchingLogic;
import com.xwolf.os.utils.MatchingResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    AverageMatchingLogic averageMatchingLogic;

    public List<List<FuzzyTrade>> process(Trade tradeSideA){

        //get the other side trade list
        List<Trade> candidateSideBTrades = tradeSvc.findCandidateSideBTrades(tradeSideA.getTradeType());

        //do mandatory match
        List<Trade> mandatoryMatchingResult = mandatoryMatchingLogic.process(tradeSideA,candidateSideBTrades);

        //do fuzzy match and show the ratio
        List<FuzzyTrade> fuzzyMatchResult = fuzzyMatchingLogic.process(tradeSideA,mandatoryMatchingResult);

        //do aggregation
        List<List<FuzzyTrade>> aggregationMatchResult = aggregationMatchingLogic.process(tradeSideA,fuzzyMatchResult);
        System.out.println("aggregationMatchResult result: size - " + aggregationMatchResult.size());
        MatchingResultUtil.print(aggregationMatchResult);

        //do average calculation
        List<List<FuzzyTrade>> averageMatchResult = averageMatchingLogic.process(tradeSideA,aggregationMatchResult);
        System.out.println("averageMatchingLogic result: size - " + averageMatchResult.size());
        MatchingResultUtil.print(averageMatchResult);

        return averageMatchResult;
    }
}
