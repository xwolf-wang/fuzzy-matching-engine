package com.xwolf.os.service;

import com.xwolf.os.domain.FuzzyTrade;
import com.xwolf.os.domain.MatchResponse;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.matching.AggregationMatchingLogic;
import com.xwolf.os.matching.AverageMatchingLogic;
import com.xwolf.os.matching.FuzzyMatchingLogic;
import com.xwolf.os.matching.MandatoryMatchingLogic;
import com.xwolf.os.utils.AggregationPrint;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public List<List<FuzzyTrade>> process(Trade trade){

        //get the other side trade list
        List<Trade> candidateList = tradeSvc.findCandidateTrades(trade.getTradeType());

        //do mandatory match
        List<Trade> mandatoryMatchingResult = mandatoryMatchingLogic.process(trade,candidateList);

        //do fuzzy match and show the ratio
        List<FuzzyTrade> fuzzyMatchResult = fuzzyMatchingLogic.process(trade,mandatoryMatchingResult);

        //do aggregation
        List<List<FuzzyTrade>> aggregationMatchResult = aggregationMatchingLogic.process(trade,fuzzyMatchResult);
        System.out.println("aggregationMatchResult result: size - " + aggregationMatchResult.size());
        AggregationPrint.print(aggregationMatchResult);

        //do average calculation
        List<List<FuzzyTrade>> averageMatchResult = averageMatchingLogic.process(trade,aggregationMatchResult);
        System.out.println("averageMatchingLogic result: size - " + averageMatchResult.size());
        AggregationPrint.print(averageMatchResult);

        return averageMatchResult;
    }
}
