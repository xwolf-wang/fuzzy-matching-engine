package com.xwolf.os.service;

import com.xwolf.os.domain.*;
import com.xwolf.os.utils.MatchingResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        List<List<FuzzyTrade>> response = matchingSvc.process(trade);
        return response;
    }


    public String match(String tradeType, Map fieldMap) {
        Trade trade = tradeSvc.saveFromChanel(tradeType,fieldMap);
        List<List<FuzzyTrade>> response = match(trade);
        return MatchingResultUtil.print(response);
    }

    public String view(String chanelName, String primaryKey) {
        Trade trade = tradeSvc.findTradesByTradeTypeAndKey(chanelName,primaryKey).stream().findFirst().orElse(null);
        List<List<FuzzyTrade>> response = match(trade);
        return MatchingResultUtil.print(response);
    }
}
