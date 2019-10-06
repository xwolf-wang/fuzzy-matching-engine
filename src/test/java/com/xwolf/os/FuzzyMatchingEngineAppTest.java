package com.xwolf.os;

import com.xwolf.os.domain.MatchField;
import com.xwolf.os.domain.MatchRule;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.service.MatchingSvc;
import com.xwolf.os.service.RuleConfigSvc;
import com.xwolf.os.service.TradeSvc;
import com.xwolf.os.utils.CsvUtil;
import com.xwolf.os.utils.EngineConstants;
import com.xwolf.os.utils.MatchingResultUtil;
import com.xwolf.os.utils.TradeConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        TestUtils.init_trades(tradeSvc);

        Trade trade = tradeSvc.getAllTrades().stream().filter(e -> e.getTradeType().equals("channel2")).findFirst().orElse(null);
        System.out.println(trade);
        System.out.println(MatchingResultUtil.print(matchingSvc.process(trade)));


    }
}
