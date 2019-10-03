package com.xwolf.os.matching;

import com.xwolf.os.domain.FuzzyTrade;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.RuleConfigSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ming
 * @Description:
 * @create 2019-10-03 12:38 AM
 **/
@Component
public class AverageMatchingLogic {
    @Autowired
    RuleConfigSvc ruleConfigSvc;

    public List<List<FuzzyTrade>> process(Trade trade, List<List<FuzzyTrade>> aggregationMatchResult) {
        return null;
    }
}
