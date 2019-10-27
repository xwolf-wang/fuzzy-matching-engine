package com.xwolf.os.matching;

import com.xwolf.os.domain.FuzzyTrade;
import com.xwolf.os.domain.MatchField;
import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.RuleConfigSvc;
import com.xwolf.os.utils.EngineConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ming
 * @Description:
 * @create 2019-10-03 12:38 AM
 **/
@Component
@Log4j2
public class AverageMatchingLogic {
    @Autowired
    RuleConfigSvc ruleConfigSvc;

    public List<List<Trade>> process(Trade trade, List<List<Trade>> aggregationMatchResult) {
        MatchRule rule = ruleConfigSvc.findMatchRule(trade.getTradeType()).get();

        Optional<Double> targetValue = getAverageFieldValue(trade, rule);
        if(!targetValue.isPresent())
            return null;
        log.debug("the target value is:" + targetValue.get());
        return aggregationMatchResult.stream()
                .filter(group -> isAcceptableAveragePrecision(targetValue.get(),group, rule))
                .collect(Collectors.toList());
    }

    public List<Trade> processSingle(Trade trade, List<Trade> aggregationMatchResult) {
        if(aggregationMatchResult.isEmpty())
            return Collections.EMPTY_LIST;

        List<List<Trade>> aggregation = new ArrayList<>();
        aggregation.add(aggregationMatchResult);
        return process(trade,aggregation).stream().findFirst().orElse(Collections.emptyList());
    }

    private boolean isAcceptableAveragePrecision(Double targetValue, List<Trade> group, MatchRule rule) {
        Double groupAvg = group.stream().map(e->getAverageFieldValue(e,rule)).collect(Collectors.averagingDouble(p -> p.get()));
        log.debug("group average value is:" + groupAvg);
        if(Math.abs(targetValue - groupAvg) <= rule.getAvgPrecision())
            return true;

        return false;
    }

    public Optional<Double> getAverageFieldValue(Trade trade, MatchRule rule) {
        if (rule.getLeftTradeType().equals(trade.getTradeType())) {
            return rule.getMatchFields().stream()
                    .filter(e -> e.getMatchingType().equals(EngineConstants.AVG))
                    .map(e -> trade.getFields().get(e.getLeftField())).findFirst().map(Double::valueOf);
        }

        if (rule.getRightTradeType().equals(trade.getTradeType())) {
            return rule.getMatchFields().stream()
                    .filter(e -> e.getMatchingType().equals(EngineConstants.AVG))
                    .map(e -> trade.getFields().get(e.getRightField())).findFirst().map(Double::valueOf);
        }

        return Optional.empty();
    }

    public void setAverageFieldValue(Trade trade, MatchRule rule, Double value) {
        if (rule.getLeftTradeType().equals(trade.getTradeType())) {
            rule.getMatchFields().stream()
                    .filter(e -> e.getMatchingType().equals(EngineConstants.AVG))
                    .map(e -> trade.getFields().put(e.getLeftField(),value.toString())).findFirst();
        }

        if (rule.getRightTradeType().equals(trade.getTradeType())) {
            rule.getMatchFields().stream()
                    .filter(e -> e.getMatchingType().equals(EngineConstants.AVG))
                    .map(e -> trade.getFields().put(e.getLeftField(),value.toString())).findFirst();
        }
    }
}
