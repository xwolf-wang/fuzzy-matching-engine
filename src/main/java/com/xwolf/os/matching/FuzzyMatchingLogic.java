package com.xwolf.os.matching;

import com.xwolf.os.domain.FuzzyTrade;
import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.RuleConfigSvc;
import com.xwolf.os.utils.EngineConstants;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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

    public List<FuzzyTrade> process(Trade tradeSideA, List<Trade> tradesSideB) {
        MatchRule rule = ruleConfigSvc.findMatchRule(tradeSideA.getTradeType()).get();

        List<String> indexList = new ArrayList<>();
        for (Trade trd : tradesSideB) {
            indexList.add(generateMatchIndexByRule(trd, rule));

        }

        List<ExtractedResult> results = FuzzySearch.extractAll(generateMatchIndexByRule(tradeSideA, rule), indexList, rule.getCutoffRatio());


        List<FuzzyTrade> fuzzyTradeList = new ArrayList<>();

        for (ExtractedResult result : results) {
            FuzzyTrade fuzzyTrade = new FuzzyTrade();
            fuzzyTrade.setTrade(tradesSideB.get(result.getIndex()));
            fuzzyTrade.setFuzzyMatchInfo(result);
            fuzzyTradeList.add(fuzzyTrade);
        }


        return fuzzyTradeList;
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
                .filter(e -> e.getMatchingType().equals(EngineConstants.FUZZY))
                .anyMatch(e -> StringUtils.equals(fieldName, e.getRightField()));
    }

    private boolean isMatchedLeftField(String fieldName, MatchRule rule) {
        return rule.getMatchFields().stream()
                .filter(e -> e.getMatchingType().equals(EngineConstants.FUZZY))
                .anyMatch(e -> StringUtils.equals(fieldName, e.getLeftField()));
    }
}
