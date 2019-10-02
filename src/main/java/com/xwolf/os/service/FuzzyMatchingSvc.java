package com.xwolf.os.service;

import com.xwolf.os.db.RuleRepository;
import com.xwolf.os.db.TradeEntity;
import com.xwolf.os.db.TradeRepository;
import com.xwolf.os.domain.*;
import com.xwolf.os.matching.AggregationMatchingLogic;
import com.xwolf.os.matching.FuzzyMatchingLogic;
import com.xwolf.os.matching.MandatoryMatchingLogic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ming
 * @Description:
 * @create 2019-09-13 10:14 PM
 **/
@Service
public class FuzzyMatchingSvc {


    @Autowired
    private TradeSvc tradeSvc;

    @Autowired
    private MatchingSvc matchingSvc;






    public MatchResponse match(Trade trade){

        //get the matching rule based on trade type

        //populate primary key and save
        tradeSvc.save(trade);

        MatchResponse response = matchingSvc.process(trade);

        //show match result



        return response;
    }

    /*private void saveTradeWithIndex(Trade trade, String index) {
        TradeEntity tradeEntity = new TradeEntity();
        tradeEntity.setSourceSystem(trade.getSourceSystem());
        tradeEntity.setTradeType(trade.getTradeType());
        tradeEntity.setTradeJsonStr(trade.toString());
        tradeEntity.setUuid(trade.getUuid());
        tradeRepository.save(tradeEntity);
    }

    public String generateMatchIndex(Trade trade) {
        MatchRule rule = findMatchRule(trade);
        return generateMatchIndexByRule(trade,rule);
    }

    private String generateMatchIndexByRule(Trade trade, MatchRule rule) {
        if(StringUtils.equals(trade.getTradeType(),rule.getLeftTradeType()))
        {
            return trade.getFields().stream()
                    .filter(e-> isMatchedLeftField(e,rule))
                    .map(e->e.getValue())
                    .collect(Collectors.joining("|"));
        }

        if(StringUtils.equals(trade.getTradeType(),rule.getRightTradeType()))
        {
            return trade.getFields().stream()
                    .filter(e-> isMatchedRightField(e,rule))
                    .map(e->e.getValue())
                    .collect(Collectors.joining("|"));
        }

        return null;
    }


    private boolean isMatchedRightField(Map field, MatchRule rule) {
        return rule.getMatchFields().stream().anyMatch(e -> StringUtils.equals(field.entrySet(), e.getRightField()));
    }

    private boolean isMatchedLeftField(Map field, MatchRule rule) {
        return rule.getMatchFields().stream().anyMatch(e -> StringUtils.equals(field., e.getLeftField()));
    }*/

}
