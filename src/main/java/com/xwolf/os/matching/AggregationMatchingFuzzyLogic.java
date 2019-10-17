package com.xwolf.os.matching;

import com.xwolf.os.domain.FuzzyTrade;
import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.RuleConfigSvc;
import com.xwolf.os.utils.EngineConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ming
 * @Description:
 * @create 2019-10-02 7:36 AM
 **/
@Component
public class AggregationMatchingFuzzyLogic {
    @Autowired
    RuleConfigSvc ruleConfigSvc;

    public List<List<FuzzyTrade>> process(Trade tradeSideA, List<FuzzyTrade> tradesSideB) {
        return searchSingle(tradeSideA, tradesSideB,10);
    }

    public List<FuzzyTrade> processSingle(Trade tradeSideA, List<Trade> tradesSideB) {
        List<FuzzyTrade> fuzzyTradeList = tradesSideB.stream().map(e -> convertToFuzzyTrade(e)).collect(Collectors.toList());
        return searchSingle(tradeSideA, fuzzyTradeList,1).stream().findFirst().orElse(Collections.emptyList());
    }

    private FuzzyTrade convertToFuzzyTrade(Trade trade) {
        FuzzyTrade fuzzyTrade = new FuzzyTrade();
        fuzzyTrade.setTrade(trade);
        return fuzzyTrade;
    }

    private List<List<FuzzyTrade>> searchSingle(Trade target, List<FuzzyTrade> list, int resultCount) {
        MatchRule rule = ruleConfigSvc.findMatchRule(target.getTradeType()).get();

        Optional<Integer> resultValue = getAggregateFieldValue(target, rule);
        if (!resultValue.isPresent())
            return null;

        List<List<FuzzyTrade>> results = new ArrayList<>();
        Collections.sort(list, new Comparator<FuzzyTrade>() {
            @Override
            public int compare(FuzzyTrade o1, FuzzyTrade o2) {
                return getAggregateFieldValue(o1.getTrade(), rule).get() - getAggregateFieldValue(o2.getTrade(), rule).get();
            }
        });

        searchGroup(resultValue.get(), 0, list, results, new ArrayList<>(), rule, resultCount);
        return results;
    }

    private void searchGroup(int result, int index, List<FuzzyTrade> list, List<List<FuzzyTrade>> results, List<FuzzyTrade> resultList, MatchRule rule, int resultCount) {
        if (index + 1 > list.size() || resultList.size() >= resultCount) {
            return;
        }
        int flag = sum(resultList, getAggregateFieldValue(list.get(index).getTrade(), rule).get(), result, rule);
        if (0 == flag) {
            if (index < list.size()) {
                List<FuzzyTrade> copyList = new ArrayList<>(resultList);
                searchGroup(result, index + 1, list, results, copyList, rule, resultCount);
            }
            resultList.add(list.get(index));
            results.add(resultList);
        } else if (-1 == flag) {
            List<FuzzyTrade> copyList = new ArrayList<>(resultList);
            searchGroup(result, index + 1, list, results, copyList, rule, resultCount);
            resultList.add(list.get(index));
            searchGroup(result, index + 1, list, results, resultList, rule, resultCount);
        } else {
            searchGroup(result, index + 1, list, results, resultList, rule, resultCount);
        }
    }

    private int sum(List<FuzzyTrade> list, int element, int result, MatchRule rule) {
        int sum = element;
        for (FuzzyTrade temp : list) {
            sum += getAggregateFieldValue(temp.getTrade(), rule).get();
        }
        if (sum == result) {
            return 0;
        } else {
            return sum > result ? 1 : -1;
        }
    }

    private Optional<Integer> getAggregateFieldValue(Trade trade, MatchRule rule) {
        if (rule.getLeftTradeType().equals(trade.getTradeType())) {
            return rule.getMatchFields().stream()
                    .filter(e -> e.getMatchingType().equals(EngineConstants.MANDATORY_AGGREGATE))
                    .map(e -> trade.getFields().get(e.getLeftField())).findFirst().map(Integer::valueOf);
        }

        if (rule.getRightTradeType().equals(trade.getTradeType())) {
            return rule.getMatchFields().stream()
                    .filter(e -> e.getMatchingType().equals(EngineConstants.MANDATORY_AGGREGATE))
                    .map(e -> trade.getFields().get(e.getRightField())).findFirst().map(Integer::valueOf);
        }

        return Optional.empty();
    }
}
