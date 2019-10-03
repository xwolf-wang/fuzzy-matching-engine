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
        tradeSvc.init();
        System.out.println("all trade list:");
        System.out.println(tradeSvc.getAllTrades());


        RuleConfigSvc ruleConfigSvc = new RuleConfigSvc();
        ruleConfigSvc.init();

        tradeSvc.setRuleConfigSvc(ruleConfigSvc);

        List fillList = tradeSvc.findCandidateSideBTrades("fusion_execution");
        System.out.println("fill trade list:");
        System.out.println(fillList);

        List execList = tradeSvc.findCandidateSideBTrades("fusion_fill");
        System.out.println("execuation trade list:");
        System.out.println(execList);

    }
}
