package com.xwolf.os.service;

import com.xwolf.os.domain.*;
import com.xwolf.os.utils.MatchingResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author ming
 * @Description:
 * @create 2019-09-13 10:14 PM
 **/
@Service
public class FuzzyMatchingEngineSvc {


    @Autowired
    private TradeSvc tradeSvc;

    @Autowired
    private MatchingSvc matchingSvc;

    public List<List<FuzzyTrade>> match(Trade trade){
        //get the other side trade list
        List<Trade> candidateSideBTrades = tradeSvc.findCandidateSideBTrades(trade.getTradeType());

        List<List<FuzzyTrade>> response = matchingSvc.process(trade, candidateSideBTrades);
        return response;
    }

    public Map<List<FuzzyTrade>, List<FuzzyTrade>> match (List<Trade> tradeAList, List<Trade> tradeBList)
    {
        try {
            List<Trade> unMatchedA = new ArrayList<>();
            List<Trade> unMatchedB = new ArrayList<>();
            Map<List<Trade>, List<Trade>> response = matchingSvc.process(tradeAList, tradeBList,unMatchedA,unMatchedB);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String match(String tradeType, Map fieldMap) {
        Trade trade = tradeSvc.save(tradeType,fieldMap);
        List<List<FuzzyTrade>> response = match(trade);
        return MatchingResultUtil.print(response);
    }

    public String view(String channelName, String tradeKey) {
        Trade trade = tradeSvc.findTradesByTradeTypeAndKey(channelName,tradeKey);
        List<List<FuzzyTrade>> response = match(trade);
        return MatchingResultUtil.print(response);
    }

    public String viewAll(String channelNameA, String channelNameB) {
        //get ChannelA trades
        List<Trade> trades = tradeSvc.getAllTrades();
        //get channelB trades
        List<Trade> unMatchedA = new ArrayList<>();
        List<Trade> unMatchedB = new ArrayList<>();
        //
        try {
            Map<List<Trade>, List<Trade>> matchResult = matchingSvc.process(
                    trades.stream().filter(e -> e.getTradeType().equals("channel1")).collect(Collectors.toList()),
                    trades.stream().filter(e -> e.getTradeType().equals("channel2")).collect(Collectors.toList()),
                    unMatchedA,
                    unMatchedB
            );


            System.out.println("----unmatched trade List A");
            System.out.println(MatchingResultUtil.format(unMatchedA));

            System.out.println("----unmatched trade List B");
            System.out.println(MatchingResultUtil.format(unMatchedB));

            System.out.println(MatchingResultUtil.format(matchResult));

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
