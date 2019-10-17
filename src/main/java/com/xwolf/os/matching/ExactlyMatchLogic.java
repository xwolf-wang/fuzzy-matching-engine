package com.xwolf.os.matching;

import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.RuleConfigSvc;
import com.xwolf.os.utils.EngineConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ming
 * @Description:
 * @create 2019-10-13 6:03 PM
 **/
@Component
public class ExactlyMatchLogic {
    @Autowired
    RuleConfigSvc ruleConfigSvc;

    public List<Trade> process(Trade tradeSideA, List<Trade> candidateSideBList, List<String> matchingTypes) {

        return candidateSideBList.stream().filter(e -> isMandatoryFieldsMatched(tradeSideA, e, matchingTypes))
                .collect(Collectors.toList());

    }

    public List<Trade> processSameSide(Trade tradeSideA, List<Trade> tradeAList, List<String> matchingTypes) {

        return tradeAList.stream().filter(e -> isMandatorySameSideFieldsMatched(tradeSideA, e, matchingTypes))
                .collect(Collectors.toList());

    }

    private boolean isMandatoryFieldsMatched(Trade tradeSideA, Trade tradeSideB, List<String> matchingTypes) {
        MatchRule rule = ruleConfigSvc.findMatchRule(tradeSideA.getTradeType()).get();

        if (rule.getLeftTradeType().equals(tradeSideA.getTradeType())) {
            return rule.getMatchFields().stream()
                    .filter(e -> matchingTypes.contains(e.matchingType))
                    .allMatch(e -> tradeSideA.getFields().get(e.getLeftField()).equals(tradeSideB.getFields().get(e.getRightField())));

        }

        if (rule.getRightTradeType().equals(tradeSideA.getTradeType())) {
            return rule.getMatchFields().stream()
                    .filter(e -> matchingTypes.contains(e.matchingType))
                    .allMatch(e -> tradeSideA.getFields().get(e.getRightField()).equals(tradeSideB.getFields().get(e.getLeftField())));

        }

        return false;
    }

    private boolean isMandatorySameSideFieldsMatched(Trade tradeA, Trade tradeB, List<String> matchingTypes) {
        MatchRule rule = ruleConfigSvc.findMatchRule(tradeA.getTradeType()).get();

        if (rule.getLeftTradeType().equals(tradeA.getTradeType())) {
            return rule.getMatchFields().stream()
                    .filter(e -> matchingTypes.contains(e.matchingType))
                    .allMatch(e -> tradeA.getFields().get(e.getLeftField()).equals(tradeB.getFields().get(e.getLeftField())));

        }

        if (rule.getRightTradeType().equals(tradeA.getTradeType())) {
            return rule.getMatchFields().stream()
                    .filter(e -> matchingTypes.contains(e.matchingType))
                    .allMatch(e -> tradeA.getFields().get(e.getRightField()).equals(tradeB.getFields().get(e.getRightField())));

        }

        return false;
    }
}
