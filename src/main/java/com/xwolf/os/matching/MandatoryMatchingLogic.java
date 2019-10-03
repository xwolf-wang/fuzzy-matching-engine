package com.xwolf.os.matching;

import com.xwolf.os.domain.MatchField;
import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.RuleConfigSvc;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ming
 * @Description:
 * @create 2019-10-02 7:35 AM
 **/
@Component
@Setter
public class MandatoryMatchingLogic {
    @Autowired
    RuleConfigSvc ruleConfigSvc;

    public List<Trade> process(Trade tradeSideA, List<Trade> candidateSideBList) {

        return candidateSideBList.stream().filter(e->isMandatoryFieldsMatched(tradeSideA, e))
                .collect(Collectors.toList());

    }

    private boolean isMandatoryFieldsMatched(Trade tradeSideA, Trade tradeSideB) {
        MatchRule rule = ruleConfigSvc.findMatchRule(tradeSideA.getTradeType()).get();

        if(rule.getLeftTradeType().equals(tradeSideA.getTradeType()))
        {
            return rule.getMatchFields().stream()
                    .filter(e -> e.matchingType.equals(MatchField.MANDATORY))
                    .allMatch(e -> tradeSideA.getFields().get(e.getLeftField()).equals(tradeSideB.getFields().get(e.getRightField())));

        }

        if(rule.getRightTradeType().equals(tradeSideA.getTradeType()))
        {
            return rule.getMatchFields().stream()
                    .filter(e -> e.matchingType.equals(MatchField.MANDATORY))
                    .allMatch(e -> tradeSideA.getFields().get(e.getRightField()).equals(tradeSideB.getFields().get(e.getLeftField())));

        }

        return false;
    }


}
