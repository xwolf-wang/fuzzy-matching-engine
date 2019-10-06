package com.xwolf.os;

import com.xwolf.os.service.RuleConfigSvc;
import com.xwolf.os.service.TradeSvc;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author ming
 * @Description:
 * @create 2019-10-02 12:19 PM
 **/
public class TradeSvcTest {


    @Test
    public void tes(){
        TradeSvc tradeSvc = new TradeSvc();
        TestUtils.init_trades(tradeSvc, "1-N");
        System.out.println("all trade list:");
        System.out.println(tradeSvc.getAllTrades());

        RuleConfigSvc ruleConfigSvc = new RuleConfigSvc();
        TestUtils.init_rule(ruleConfigSvc);

        tradeSvc.setRuleConfigSvc(ruleConfigSvc);

        List fillList = tradeSvc.findCandidateSideBTrades("channel2");
        System.out.println("channel2 trade list:");
        System.out.println(fillList);

        List execList = tradeSvc.findCandidateSideBTrades("channel1");
        System.out.println("channel1 trade list:");
        System.out.println(execList);

    }
}
