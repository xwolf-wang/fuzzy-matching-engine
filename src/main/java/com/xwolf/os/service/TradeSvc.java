package com.xwolf.os.service;

import com.xwolf.os.domain.Trade;
import com.xwolf.os.utils.CsvUtil;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author ming
 * @Description:
 * @create 2019-10-02 7:23 AM
 **/
@Service
@Setter
public class TradeSvc {

    @Autowired
    RuleConfigSvc ruleConfigSvc;

    List<Trade> tradeList = new ArrayList<>();

    @PostConstruct
    public void init() {
        String filepath = "/Users/ming/IdeaProjects/fuzzy-matching-engine/target/test-classes/fusion_fill.csv";

        List<Map<String, String>> list = null;
        try {
            list = CsvUtil.readObjectsFromCsv(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tradeList.addAll(list.stream().map(e -> convertMap2Trade(e,"fusion_fill","oraderId")).collect(Collectors.toList()));

        filepath = "/Users/ming/IdeaProjects/fuzzy-matching-engine/target/test-classes/fusion_execution.csv";

        List<Map<String, String>> execlist = null;
        try {
            execlist = CsvUtil.readObjectsFromCsv(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tradeList.addAll(execlist.stream().map(e -> convertMap2Trade(e,"fusion_execution","tradeId")).collect(Collectors.toList()));

    }

    private Trade convertMap2Trade(Map<String, String> map, String tradeType, String primaryKey) {
        Trade trade = new Trade();

        trade.setTradeType(tradeType);
        trade.setUuid(tradeType + "_" + map.get(primaryKey));
        trade.setFields(map);

        return trade;
    }

    public void save(Trade trade) {
    }

    public List<Trade> findCandidateSideBTrades(String tradeType) {
        Optional<String> rule = ruleConfigSvc.findMatchedTradeType(tradeType);

        return tradeList.stream().filter(trade -> StringUtils.equals(trade.getTradeType(), rule.get()))
                .collect(Collectors.toList());

    }

    public List<Trade> getAllTrades(){
        return tradeList;
    }

    public Trade saveFromChanel(String chanelName, Map fieldMap) {
        Optional<String> rule = ruleConfigSvc.findMatchedTradeType(chanelName);
        Trade trade = convertMap2Trade(fieldMap,chanelName,ruleConfigSvc.findPrimaryKeyName(chanelName));
        tradeList.add(trade);
        return trade;
    }
}
