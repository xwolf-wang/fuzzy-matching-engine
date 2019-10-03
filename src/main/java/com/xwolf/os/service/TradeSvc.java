package com.xwolf.os.service;

import com.xwolf.os.domain.Trade;
import com.xwolf.os.utils.TradeConverter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void save(Trade trade) {
    }

    public List<Trade> findCandidateSideBTrades(String tradeTypeA) {
        Optional<String> tradeTypeB = ruleConfigSvc.findMatchedTradeType(tradeTypeA);

        return tradeList.stream().filter(trade -> StringUtils.equals(trade.getTradeType(), tradeTypeB.get()))
                .collect(Collectors.toList());

    }

    public List<Trade> getAllTrades() {
        return tradeList;
    }

    public Trade save(String chanelName, Map<String, String> fieldMap) {
        List<Map<String,String>> trades = new ArrayList<>();
        trades.add(fieldMap);
        return save(chanelName,trades).stream().findFirst().orElse(null);

    }

    public Trade findTradesByTradeTypeAndKey(String chanelName, String primaryKey) {
        String uuid = chanelName + "_" + primaryKey;
        Optional<Trade> trade = tradeList.stream().filter(e -> e.getUuid().equals(uuid))
                .findFirst();

        return trade.orElse(null);
    }

    public void deletAll() {
        tradeList.clear();
    }

    public Trade delete(String chanelName, String primaryKey) {
        String uuid = chanelName + "_" + primaryKey;
        return delete(uuid);
    }

    public Trade delete(String uuid) {
        Optional<Trade> trade = tradeList.stream().filter(e -> e.getUuid().equals(uuid))
                .findFirst();
        trade.ifPresent(e -> tradeList.remove(e));
        return trade.orElse(null);
    }

    public void addAll(List<Trade> trades){
        tradeList.addAll(trades);
    }

    public List<Trade> save(String chanelName, List<Map<String, String>> mapList) {
        Optional<String> rule = ruleConfigSvc.findMatchedTradeType(chanelName);
        String primaryKey = ruleConfigSvc.findPrimaryKeyName(chanelName);
        List<Trade> trades = mapList.stream().map(e -> TradeConverter.convertMap2Trade(e, chanelName, primaryKey))
                .collect(Collectors.toList());

        tradeList.addAll(trades);

        return trades;
    }
}
