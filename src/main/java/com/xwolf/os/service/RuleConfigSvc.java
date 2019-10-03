package com.xwolf.os.service;

import com.xwolf.os.domain.MatchField;
import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.utils.EngineConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.xwolf.os.domain.MatchField.*;

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
        rule.setRuleName("fusionRule");
        rule.setLeftTradeType("fusion_fill");
        rule.setLeftPrimaryKey("oraderId");
        rule.setRightTradeType("fusion_execution");
        rule.setRightPrimaryKey("tradeId");
        rule.setCutoffRatio(90);
        rule.setAvgPrecision(0.2);
        rule.getMatchFields().add(new MatchField("tradePrice","trdPrice", EngineConstants.AVG));
        rule.getMatchFields().add(new MatchField("quantity","qty", EngineConstants.MANDATORY_AGGREGATE));
        rule.getMatchFields().add(new MatchField("firm", "firm", EngineConstants.MANDATORY));
        rule.getMatchFields().add(new MatchField("cust", "customer", EngineConstants.MANDATORY));
        rule.getMatchFields().add(new MatchField("user", "user", EngineConstants.FUZZY));
        rule.getMatchFields().add(new MatchField("exec", "execBy",EngineConstants.FUZZY));

        System.out.println(rule);
        rules.add(rule);
    }

    public void saveRule(MatchRule rule){
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

    public List<MatchRule> findAll() {
        return rules;
    }

    public String findPrimaryKeyName(String tradeType) {
        MatchRule rule =  findMatchRule(tradeType).get();
        if(rule.getLeftTradeType().equals(tradeType))
            return rule.getLeftPrimaryKey();

        if(rule.getRightTradeType().equals(tradeType))
            return rule.getRightPrimaryKey();

        return null;
    }
}
