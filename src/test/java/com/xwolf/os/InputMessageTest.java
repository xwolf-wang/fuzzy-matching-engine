package com.xwolf.os;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xwolf.os.db.RuleEntity;
import com.xwolf.os.domain.Field;
import com.xwolf.os.domain.Trade;
import com.xwolf.os.matching.AverageFuzzyMatchingStratergy;
import com.xwolf.os.matching.AverageFuzzyMatchingResult;
import com.xwolf.os.service.FuzzyMatchingSvc;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ming
 * @Description:
 * @create 2019-09-13 10:59 PM
 **/
public class InputMessageTest {
    @ParameterizedTest
    @CsvFileSource(resources = "/Vehicle-Similarity.csv", numLinesToSkip = 1)
    public void similarity(final boolean isSimilar,
                           final String make, final String model, final String compareToMake,
                           final String compareToModel) {


        assertEquals(isSimilar, false,
                format("Expected isSimilar: %s for %s compared to %s", isSimilar, make, model));
    }


    @Test
    public void testInputMessage() throws JsonProcessingException {
        Trade trade = new Trade();
        trade.setSourceSystem("fusion");
        trade.setTradeType("fill");
        trade.getFields().add(new Field("tradePrice","1000.0"));
        trade.getFields().add(new Field("quantity","10000000"));
        trade.getFields().add(new Field("firm","FXAA"));
        trade.getFields().add(new Field("cust","3M"));
        trade.getFields().add(new Field("user","Ming"));
        trade.getFields().add(new Field("lastModified","10-19-2019"));


        Trade excutionTrade = new Trade();
        excutionTrade.setSourceSystem("fusion");
        excutionTrade.setTradeType("excution");
        excutionTrade.getFields().add(new Field("trdPrice","1000.01"));
        excutionTrade.getFields().add(new Field("qty","10000000"));
        excutionTrade.getFields().add(new Field("firm","FXAA4"));
        excutionTrade.getFields().add(new Field("customer","3M"));
        excutionTrade.getFields().add(new Field("user","GGG"));
        excutionTrade.getFields().add(new Field("lastModified","10-20-2019"));

        FuzzyMatchingSvc fuzzyMatchingSvc = new FuzzyMatchingSvc();
        String index = fuzzyMatchingSvc.generateMatchIndex(trade);
        System.out.println(index);

        String executionIndex = fuzzyMatchingSvc.generateMatchIndex(excutionTrade);
        System.out.println(executionIndex);

        AverageFuzzyMatchingResult result = AverageFuzzyMatchingStratergy.match(index,executionIndex);
        System.out.println(result);
        System.out.println(result.average());

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(trade);
        System.out.println(json);

        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setSourceSystem("fusion");
        ruleEntity.setLeftTradeType("fill");
        ruleEntity.setLeftField("quantity");
        ruleEntity.setRightTradeType("excution");
        ruleEntity.setRightField("qty");

        String json1 = mapper.writeValueAsString(ruleEntity);
        System.out.println(json1);
        //{"id":null,"sourceSystem":"fusion","leftTradeType":"fill","leftField":"quantity","rightTradeType":"excution","rightField":"qty"}

    }
}
