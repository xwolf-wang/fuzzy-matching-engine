package com.xwolf.os.matching;

import com.xwolf.os.domain.MatchField;
import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.RuleConfigSvc;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
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
    public List<List<Trade>> process(Trade trade, Map<Trade, ExtractedResult> fuzzyMatchResult) {
        return searchSingle(trade,new ArrayList<>(fuzzyMatchResult.keySet()));
    }

    @Autowired
    RuleConfigSvc ruleConfigSvc;

    private Optional<Integer> getAggregateFieldValue(Trade trade, MatchRule rule){
        if(rule.getLeftTradeType().equals(trade.getTradeType())){
            return rule.getMatchFields().stream()
                    .filter(e -> e.getMatchingType().equals(MatchField.MANDATORY_AGGREGATE))
                    .map(e -> trade.getFields().get(e.getLeftField())).findFirst().map(Integer::valueOf);
        }

        if(rule.getRightTradeType().equals(trade.getTradeType())){
            return rule.getMatchFields().stream()
                    .filter(e -> e.getMatchingType().equals(MatchField.MANDATORY_AGGREGATE))
                    .map(e -> trade.getFields().get(e.getRightField())).findFirst().map(Integer::valueOf);
        }

        return Optional.empty();
    }

    public List<List<Trade>> searchSingle(Trade target, List<Trade> list){
        MatchRule rule = ruleConfigSvc.findMatchRule(target.getTradeType()).get();

        Optional<Integer> resultValue = getAggregateFieldValue(target,rule);
        if(!resultValue.isPresent())
            return null;

        List<List<Trade>> results = new ArrayList<>();
        Collections.sort(list, new Comparator<Trade>() {
            @Override
            public int compare(Trade o1, Trade o2) {
                return getAggregateFieldValue(o1,rule).get() - getAggregateFieldValue(o2,rule).get();
            }
        });

        searchGroup(resultValue.get(), 0,list,results, new ArrayList<> (), rule);
        return results;
    }

    public void searchGroup(int result, int index, List<Trade> list, List<List<Trade>> results, List<Trade> resultList, MatchRule rule){
        if(index + 1 > list.size()){
            return;
        }
        int flag = sum(resultList,getAggregateFieldValue(list.get(index),rule).get(),result, rule);
        if(0 == flag){
            if(index < list.size()){
                List<Trade> copyList = new ArrayList<>(resultList);
                searchGroup(result, index + 1, list,results,copyList, rule);
            }
            resultList.add(list.get(index));
            results.add(resultList);
        }else if (-1 == flag){
            List<Trade> copyList = new ArrayList<>(resultList);
            searchGroup(result, index + 1, list, results, copyList, rule);
            resultList.add(list.get(index));
            searchGroup(result, index + 1, list, results, resultList, rule);
        }else{
            searchGroup(result, index + 1, list, results, resultList, rule);
        }
    }

    public int sum(List<Trade> list, int element, int result, MatchRule rule){
        int sum = element;
        for(Trade temp : list){
            sum += getAggregateFieldValue(temp,rule).get();
        }
        if(sum == result){
            return 0;
        }else{
            return sum > result ? 1 : -1;
        }
    }
}
