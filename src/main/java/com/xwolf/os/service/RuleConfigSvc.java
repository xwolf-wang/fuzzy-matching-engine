package com.xwolf.os.service;

import com.xwolf.os.domain.MatchRule;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author ming
 * @Description:
 * @create 2019-10-01 9:48 PM
 **/
@Service
public class RuleConfigSvc {


    private List<MatchRule> rules = new ArrayList<>();

    public void saveRule(MatchRule rule) {
        Optional<MatchRule> existRule = rules.stream().filter(e -> e.getRuleName().equals(rule.getRuleName()))
                .findFirst();
        existRule.ifPresent(e -> rules.remove(e));
        rules.add(rule);
    }

    public Optional<MatchRule> findMatchRule(String tradeType) {
        return rules.stream().filter(e -> isMatched(e, tradeType))
                .findFirst();
    }

    public Optional<String> findMatchedTradeType(String tradeType) {
        return this.findMatchRule(tradeType).map(e -> findMatchedTradeType(e, tradeType));
    }


    private String findMatchedTradeType(MatchRule rule, String tradeType) {
        if (StringUtils.equals(rule.getLeftTradeType(), tradeType))
            return rule.getRightTradeType();

        if (StringUtils.equals(rule.getRightTradeType(), tradeType))
            return rule.getLeftTradeType();

        return null;
    }


    private boolean isMatched(MatchRule rule, String tradeType) {

        if (StringUtils.equals(rule.getLeftTradeType(), tradeType))
            return true;

        if (StringUtils.equals(rule.getRightTradeType(), tradeType))
            return true;

        return false;
    }

    public List<MatchRule> findAll() {
        return rules;
    }

    public String findPrimaryKeyName(String tradeType) {
        MatchRule rule = findMatchRule(tradeType).get();
        if (rule.getLeftTradeType().equals(tradeType))
            return rule.getLeftTradeKey();

        if (rule.getRightTradeType().equals(tradeType))
            return rule.getRightTradeKey();

        return null;
    }
}
