package com.xwolf.os;

import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.MatchingSvc;
import com.xwolf.os.service.RuleConfigSvc;
import com.xwolf.os.service.TradeSvc;
import com.xwolf.os.utils.MatchingResultUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ming
 * @Description:
 * @create 2019-10-02 7:22 PM
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class FuzzyMatchingEngineAppTest {

    @Autowired
    MatchingSvc matchingSvc;

    @Autowired
    TradeSvc tradeSvc;

    @Autowired
    RuleConfigSvc ruleConfigSvc;


    @Test
    public void contextLoads() {
        TestUtils.init_rule(ruleConfigSvc);
        TestUtils.init_trades(tradeSvc, "1-N");

        Trade trade = tradeSvc.getAllTrades().stream().filter(e -> e.getTradeType().equals("channel2")).findFirst().orElse(null);
        System.out.println(trade);
        System.out.println(MatchingResultUtil.print(matchingSvc.process(trade)));


    }
}
