package com.xwolf.os.matching;

import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.RuleConfigSvc;
import com.xwolf.os.utils.EngineConstants;
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

    public List<List<Trade>> process(Trade tradeSideA, List<Trade> tradesSideB,int count) {
        return searchSingle(tradeSideA, tradesSideB, 10);
    }

    public List<Trade> processSingle(Trade tradeSideA, List<Trade> tradesSideB) {
        return searchSingle(tradeSideA, tradesSideB, 1).stream().findFirst().orElse(Collections.emptyList());
    }


    private List<List<Trade>> searchSingle(Trade target, List<Trade> list, int resultCount) {
        MatchRule rule = ruleConfigSvc.findMatchRule(target.getTradeType()).get();

        Optional<Integer> resultValue = getAggregateFieldValue(target, rule);
        if (!resultValue.isPresent())
            return null;

        List<List<Trade>> results = new ArrayList<>();
        Collections.sort(list, new Comparator<Trade>() {
            @Override
            public int compare(Trade o1, Trade o2) {
                return getAggregateFieldValue(o1, rule).get() - getAggregateFieldValue(o2, rule).get();
            }
        });

        searchGroup(resultValue.get(), 0, list, results, new ArrayList<>(), rule, resultCount);
        return results;
    }


    private void searchGroup(int result, int index, List<Trade> list, List<List<Trade>> results, List<Trade> resultList, MatchRule rule, int resultCount) {
        if (index + 1 > list.size() || results.size() >= resultCount) {
            return;
        }
        int flag = sum(resultList, getAggregateFieldValue(list.get(index), rule).get(), result, rule);
        if (0 == flag) {
            if (index < list.size()) {
                List<Trade> copyList = new ArrayList<>(resultList);
                searchGroup(result, index + 1, list, results, copyList, rule, resultCount);
            }
            resultList.add(list.get(index));
            results.add(resultList);
        } else if (-1 == flag) {
            List<Trade> copyList = new ArrayList<>(resultList);
            searchGroup(result, index + 1, list, results, copyList, rule, resultCount);
            resultList.add(list.get(index));
            searchGroup(result, index + 1, list, results, resultList, rule, resultCount);
        } else {
            searchGroup(result, index + 1, list, results, resultList, rule, resultCount);
        }
    }

    private int sum(List<Trade> list, int element, int result, MatchRule rule) {
        int sum = element;
        for (Trade temp : list) {
            sum += getAggregateFieldValue(temp, rule).get();
        }
        if (sum == result) {
            return 0;
        } else {
            return sum > result ? 1 : -1;
        }
    }

    public Optional<Integer> getAggregateFieldValue(Trade trade, MatchRule rule) {
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

    public void setAggregateFieldValue(Trade trade, MatchRule rule, String value) {
        if (rule.getLeftTradeType().equals(trade.getTradeType())) {
             rule.getMatchFields().stream()
                    .filter(e -> e.getMatchingType().equals(EngineConstants.MANDATORY_AGGREGATE))
                    .map(e -> trade.getFields().put(e.getLeftField(),value)).findFirst();
        }

        if (rule.getRightTradeType().equals(trade.getTradeType())) {
             rule.getMatchFields().stream()
                    .filter(e -> e.getMatchingType().equals(EngineConstants.MANDATORY_AGGREGATE))
                    .map(e -> trade.getFields().put(e.getRightField(),value)).findFirst();
        }

    }
}
