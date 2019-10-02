package com.xwolf.os.service;

import com.xwolf.os.domain.MatchResponse;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.matching.AggregationMatchingLogic;
import com.xwolf.os.matching.FuzzyMatchingLogic;
import com.xwolf.os.matching.MandatoryMatchingLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ming
 * @Description:
 * @create 2019-10-02 7:46 AM
 **/
@Component
public class MatchingSvc {

    @Autowired
    private TradeSvc tradeSvc;

    @Autowired
    private MandatoryMatchingLogic mandatoryMatchingLogic;

    @Autowired
    FuzzyMatchingLogic fuzzyMatchingLogic;

    @Autowired
    AggregationMatchingLogic aggregationMatchingLogic;

    public MatchResponse process(Trade trade){

        //get the other side trade list
        List<Trade> candidateList = tradeSvc.findCandidateTrades(trade.getTradeType());

        //do mandatory match
        List<Trade> mandatoryMatchingResult = mandatoryMatchingLogic.process(trade,candidateList);

        //do fuzzy match and show the ratio
        List fuzzyMatchResult = fuzzyMatchingLogic.process(trade,mandatoryMatchingResult);

        //do aggregation
        List aggregationMatchResult = aggregationMatchingLogic.process(trade,fuzzyMatchResult);

        //do average calculation
        return null;
    }
}
