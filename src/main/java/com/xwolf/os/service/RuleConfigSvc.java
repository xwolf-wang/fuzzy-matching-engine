package com.xwolf.os.service;

import com.xwolf.os.domain.MatchField;
import com.xwolf.os.domain.MatchRule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ming
 * @Description:
 * @create 2019-10-01 9:48 PM
 **/
@Service
public class RuleConfigSvc {

    private List<MatchRule> rules = new ArrayList<>();

    @PostConstruct
    public void init(){
        MatchRule rule = new MatchRule();
        rule.setLeftTradeType("fusion_fill");
        rule.setLeftPrimaryKey("oraderId");
        rule.setRightTradeType("fusion_execution");
        rule.setRightPrimaryKey("tradeId");
        rule.getMatchFields().add(new MatchField("tradePrice","trdPrice","avg"));
        rule.getMatchFields().add(new MatchField("quantity","qty","mandatory_aggregate"));
        rule.getMatchFields().add(new MatchField("firm", "firm","mandatory"));
        rule.getMatchFields().add(new MatchField("cust", "customer","mandatory"));
        rule.getMatchFields().add(new MatchField("user", "user","fuzzy"));
        rule.getMatchFields().add(new MatchField("exec", "execBy","fuzzy"));

        rules.add(rule);
    }

    public Optional<MatchRule> findMatchRule(String tradeType) {
        return rules.stream().filter(e->isMatched(e,tradeType))
                .findFirst();
    }

    public Optional<String> findMatchedTradeType(String tradeType){
        return this.findMatchRule(tradeType).map(e -> findMatchedTradeType(e, tradeType));
    }

    private String findMatchedTradeType(MatchRule rule, String tradeType) {
        if(StringUtils.equals(rule.getLeftTradeType(),tradeType))
            return rule.getRightTradeType();

        if(StringUtils.equals(rule.getRightTradeType(),tradeType))
            return rule.getLeftTradeType();

        return null;
    }


    private boolean isMatched(MatchRule rule, String tradeType) {

        if(StringUtils.equals(rule.getLeftTradeType(),tradeType))
            return true;

        if(StringUtils.equals(rule.getRightTradeType(),tradeType))
            return true;

        return false;
    }
}