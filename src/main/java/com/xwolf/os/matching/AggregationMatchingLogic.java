package com.xwolf.os.matching;

import com.xwolf.os.domain.FuzzyTrade;
import com.xwolf.os.domain.MatchField;
import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.RuleConfigSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author ming
 * @Description:
 * @create 2019-10-02 7:36 AM
 **/
@Component
public class AggregationMatchingLogic {
    @Autowired
    RuleConfigSvc ruleConfigSvc;

    public List<List<FuzzyTrade>> process(Trade tradeSideA, List<FuzzyTrade> tradesSideB) {
        return searchSingle(tradeSideA, tradesSideB);
    }

    private List<List<FuzzyTrade>> searchSingle(Trade target, List<FuzzyTrade> list) {
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

        searchGroup(resultValue.get(), 0, list, results, new ArrayList<>(), rule);
        return results;
    }

    private void searchGroup(int result, int index, List<FuzzyTrade> list, List<List<FuzzyTrade>> results, List<FuzzyTrade> resultList, MatchRule rule) {
        if (index + 1 > list.size()) {
            return;
        }
        int flag = sum(resultList, getAggregateFieldValue(list.get(index).getTrade(), rule).get(), result, rule);
        if (0 == flag) {
            if (index < list.size()) {
                List<FuzzyTrade> copyList = new ArrayList<>(resultList);
                searchGroup(result, index + 1, list, results, copyList, rule);
            }
            resultList.add(list.get(index));
            results.add(resultList);
        } else if (-1 == flag) {
            List<FuzzyTrade> copyList = new ArrayList<>(resultList);
            searchGroup(result, index + 1, list, results, copyList, rule);
            resultList.add(list.get(index));
            searchGroup(result, index + 1, list, results, resultList, rule);
        } else {
            searchGroup(result, index + 1, list, results, resultList, rule);
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
                    .filter(e -> e.getMatchingType().equals(MatchField.MANDATORY_AGGREGATE))
                    .map(e -> trade.getFields().get(e.getLeftField())).findFirst().map(Integer::valueOf);
        }

        if (rule.getRightTradeType().equals(trade.getTradeType())) {
            return rule.getMatchFields().stream()
                    .filter(e -> e.getMatchingType().equals(MatchField.MANDATORY_AGGREGATE))
                    .map(e -> trade.getFields().get(e.getRightField())).findFirst().map(Integer::valueOf);
        }

        return Optional.empty();
    }
}
