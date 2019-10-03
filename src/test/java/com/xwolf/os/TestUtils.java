package com.xwolf.os;

import com.xwolf.os.domain.MatchField;
import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.service.RuleConfigSvc;
import com.xwolf.os.service.TradeSvc;
import com.xwolf.os.utils.CsvUtil;
import com.xwolf.os.utils.EngineConstants;
import com.xwolf.os.utils.TradeConverter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ming
 * @Description:
 * @create 2019-10-03 9:53 PM
 **/
public class TestUtils {

    public static void init_rule(RuleConfigSvc ruleConfigSvc) {
        MatchRule rule = new MatchRule();
        rule.setRuleName("fusionRule");
        rule.setLeftTradeType("fusion_fill");
        rule.setLeftPrimaryKey("oraderId");
        rule.setRightTradeType("fusion_execution");
        rule.setRightPrimaryKey("tradeId");
        rule.setCutoffRatio(90);
        rule.setAvgPrecision(0.2);
        rule.getMatchFields().add(new MatchField("tradePrice", "trdPrice", EngineConstants.AVG));
        rule.getMatchFields().add(new MatchField("quantity", "qty", EngineConstants.MANDATORY_AGGREGATE));
        rule.getMatchFields().add(new MatchField("firm", "firm", EngineConstants.MANDATORY));
        rule.getMatchFields().add(new MatchField("cust", "customer", EngineConstants.MANDATORY));
        rule.getMatchFields().add(new MatchField("user", "user", EngineConstants.FUZZY));
        rule.getMatchFields().add(new MatchField("exec", "execBy", EngineConstants.FUZZY));

        System.out.println(rule);
        ruleConfigSvc.saveRule(rule);
    }

    public static void init_trades(TradeSvc tradeSvc){
        String filepath = "./target/test-classes/fusion_fill.csv";

        List<Map<String, String>> list = null;
        try {
            list = CsvUtil.readObjectsFromCsv(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tradeSvc.addAll(list.stream().map(e -> TradeConverter.convertMap2Trade(e, "fusion_fill", "oraderId")).collect(Collectors.toList()));

        filepath = "./target/test-classes/fusion_execution.csv";

        List<Map<String, String>> execlist = null;
        try {
            execlist = CsvUtil.readObjectsFromCsv(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tradeSvc.addAll(execlist.stream().map(e -> TradeConverter.convertMap2Trade(e, "fusion_execution", "tradeId")).collect(Collectors.toList()));

    }
}
