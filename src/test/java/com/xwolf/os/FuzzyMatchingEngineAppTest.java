package com.xwolf.os;

import com.xwolf.os.domain.FuzzyTrade;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.matching.AggregationMatchingLogic;
import com.xwolf.os.matching.FuzzyMatchingLogic;
import com.xwolf.os.matching.MandatoryMatchingLogic;
import com.xwolf.os.service.TradeSvc;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * @author ming
 * @Description:
 * @create 2019-10-02 7:22 PM
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class FuzzyMatchingEngineAppTest {
    @Autowired
    MandatoryMatchingLogic mandatoryMatchingLogic;

    @Autowired
    FuzzyMatchingLogic fuzzyMatchingLogic;

    @Autowired
    AggregationMatchingLogic aggregationMatchingLogic;

    @Autowired
    TradeSvc tradeSvc;

    @Test
    public void contextLoads() {
        Trade trade = tradeSvc.getAllTrades().stream().filter(e -> e.getTradeType().equals("fusion_execution")).findFirst().orElse(null);
        List candidateTrades = tradeSvc.findCandidateTrades(trade.getTradeType());
        List mandatoryList = mandatoryMatchingLogic.process(trade, candidateTrades);
        System.out.println(trade);
        System.out.println("mandatoryList is:");
        System.out.println(mandatoryList.size());

        List<FuzzyTrade> fuzzyResult = fuzzyMatchingLogic.process(trade,mandatoryList);

        List<List<FuzzyTrade>> aggregateResult = aggregationMatchingLogic.process(trade,fuzzyResult);

        int i=1;
        for(List<FuzzyTrade> result : aggregateResult){
            System.out.println("group " + i);
            System.out.println(result);
            i++;
        }


    }
}
