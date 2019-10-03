package com.xwolf.os.utils;

import com.xwolf.os.domain.Trade;

import java.util.Map;

/**
 * @author ming
 * @Description:
 * @create 2019-10-03 9:45 PM
 **/
public class TradeConverter {
    public static Trade convertMap2Trade(Map<String, String> map, String tradeType, String primaryKey) {
        Trade trade = new Trade();

        trade.setTradeType(tradeType);
        trade.setUuid(tradeType + "_" + map.get(primaryKey));
        trade.setFields(map);
        return trade;
    }
}
