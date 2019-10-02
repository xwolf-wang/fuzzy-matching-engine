package com.xwolf.os.matching;

import com.xwolf.os.domain.MatchField;
import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.RuleConfigSvc;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ming
 * @Description:
 * @create 2019-10-02 7:40 AM
 **/
@Component
public class FuzzyMatchingLogic {

    @Autowired
    RuleConfigSvc ruleConfigSvc;

    public static Integer cutoff = 90;

    public Map<Trade, ExtractedResult> process(Trade trade, List<Trade> mandatoryMatchingResult) {

        List<String> indexList= new ArrayList<>();
        MatchRule rule = ruleConfigSvc.findMatchRule(trade.getTradeType()).get();
        for(Trade trd : mandatoryMatchingResult){
            indexList.add(generateMatchIndexByRule(trd,rule));
        }

        List<ExtractedResult> results = FuzzySearch.extractAll(generateMatchIndexByRule(trade,rule),indexList,cutoff);

        Map<Trade, ExtractedResult> map = new HashMap<>();

        for(ExtractedResult result: results){
            map.put(mandatoryMatchingResult.get(result.getIndex()),result);
        }
        System.out.println(map);

        return map;
    }



    private String generateMatchIndexByRule(Trade trade, MatchRule rule) {
        if (StringUtils.equals(trade.getTradeType(), rule.getLeftTradeType())) {
            return trade.getFields().entrySet().stream()
                    .filter(e -> isMatchedLeftField(e.getKey(), rule))
                    .map(e -> e.getValue())
                    .collect(Collectors.joining(""));
        }

        if (StringUtils.equals(trade.getTradeType(), rule.getRightTradeType())) {
            return trade.getFields().entrySet().stream()
                    .filter(e -> isMatchedRightField(e.getKey(), rule))
                    .map(e -> e.getValue())
                    .collect(Collectors.joining(""));
        }

        return null;
    }

    private boolean isMatchedRightField(String fieldName, MatchRule rule) {
        return rule.getMatchFields().stream()
                .filter(e -> e.getMatchingType().equals(MatchField.FUZZY))
                .anyMatch(e -> StringUtils.equals(fieldName, e.getRightField()));
    }

    private boolean isMatchedLeftField(String fieldName, MatchRule rule) {
        return rule.getMatchFields().stream()
                .filter(e -> e.getMatchingType().equals(MatchField.FUZZY))
                .anyMatch(e -> StringUtils.equals(fieldName, e.getLeftField()));
    }
}
