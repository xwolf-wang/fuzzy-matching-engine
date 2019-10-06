package com.xwolf.os;

import com.xwolf.os.matching.MandatoryMatchingLogic;
import com.xwolf.os.service.RuleConfigSvc;
import com.xwolf.os.service.TradeSvc;
import org.junit.jupiter.api.Test;

/**
 * @author ming
 * @Description:
 * @create 2019-10-02 8:16 PM
 **/
public class MandatoryMatchingLogicTest {

    MandatoryMatchingLogic mandatoryMatchingLogic = new MandatoryMatchingLogic();


    @Test
    public void process(){
        TradeSvc tradeSvc = new TradeSvc();
        TestUtils.init_trades(tradeSvc, "1-N");
        System.out.println("all trade list:");
        System.out.println(tradeSvc.getAllTrades());


        RuleConfigSvc ruleConfigSvc = new RuleConfigSvc();
        TestUtils.init_rule(ruleConfigSvc);
        tradeSvc.setRuleConfigSvc(ruleConfigSvc);

        mandatoryMatchingLogic.setRuleConfigSvc(ruleConfigSvc);
    }
}
