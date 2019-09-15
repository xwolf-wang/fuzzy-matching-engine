package com.xwolf.os.service;

import com.xwolf.os.db.RuleRepository;
import com.xwolf.os.db.TradeEntity;
import com.xwolf.os.db.TradeRepository;
import com.xwolf.os.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @author ming
 * @Description:
 * @create 2019-09-13 10:14 PM
 **/
@Service
public class FuzzyMatchingSvc {

    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    RuleRepository ruleRepository;


    public MatchResponse match(Trade trade){

        String index = generateMatchIndex(trade);
        saveTradeWithIndex(trade,index);

        MatchResponse response = new MatchResponse();


        return response;
    }

    private void saveTradeWithIndex(Trade trade, String index) {
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


    private boolean isMatchedRightField(Field field, MatchRule rule) {
        return rule.getMatchFields().stream().anyMatch(e -> StringUtils.equals(field.getName(), e.getRightField()));
    }

    private boolean isMatchedLeftField(Field field, MatchRule rule) {
        return rule.getMatchFields().stream().anyMatch(e -> StringUtils.equals(field.getName(), e.getLeftField()));
    }

    private MatchRule findMatchRule(Trade trade) {
        MatchRule rule = new MatchRule();
        rule.setSourceSystem("fusion");
        rule.setLeftTradeType("fill");
        rule.setRightTradeType("excution");
        rule.setPrecision(13);
        rule.getMatchFields().add(new MatchField("tradePrice","trdPrice"));
        rule.getMatchFields().add(new MatchField("quantity","qty"));
        rule.getMatchFields().add(new MatchField("firm", "firm"));
        rule.getMatchFields().add(new MatchField("cust", "customer"));

        return rule;
    }
}
