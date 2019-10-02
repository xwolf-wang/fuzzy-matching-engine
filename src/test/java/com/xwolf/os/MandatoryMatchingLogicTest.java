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

    private void init(){
        TradeSvc tradeSvc = new TradeSvc();
        tradeSvc.init();
        System.out.println("all trade list:");
        System.out.println(tradeSvc.getAllTrades());


        RuleConfigSvc ruleConfigSvc = new RuleConfigSvc();
        ruleConfigSvc.init();

        tradeSvc.setRuleConfigSvc(ruleConfigSvc);

        mandatoryMatchingLogic.setRuleConfigSvc(ruleConfigSvc);
    }

    @Test
    public void process(){

    }
}
