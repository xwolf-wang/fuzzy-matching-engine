package com.xwolf.os.utils;

import com.xwolf.os.domain.FuzzyTrade;
import com.xwolf.os.domain.Trade;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ming
 * @Description:
 * @create 2019-10-03 9:19 AM
 **/
public class MatchingResultUtil {
    public static String formatFuzzyGroup(List<List<FuzzyTrade>> aggregateResult){
        StringBuffer sb = new StringBuffer();
        int i=1;
        sb.append("Matching result group size: " + aggregateResult.size() + "\n");
        for(List<FuzzyTrade> result : aggregateResult){
            sb.append("group : " + i + " \n");
            sb.append("------- \n");
            result.stream().forEach(e -> sb.append(e + "\n"));
            i++;
        }

        return sb.toString();
    }

    public static String format(Map<List<Trade>,List<Trade>> matchResult){
       return matchResult.entrySet().stream().map(
                e->formatSingle(e.getKey(),e.getValue())
        ).collect(Collectors.joining("\n"));
    }

    private static String formatSingle(List<Trade> key, List<Trade> value) {
        StringBuffer sb = new StringBuffer();
        int i=1;
        sb.append("########################\n " );
        sb.append("left side: \n");
        sb.append(format(key));
        sb.append("right side: \n");
        sb.append(format(value));
        sb.append("########################\n " );


        return sb.toString();
    }

    public static String format(List<Trade> list) {
        StringBuffer sb = new StringBuffer();
        int i=1;
        sb.append("--------------- \n");
        for(Trade trade : list){
            sb.append(trade.getFields() + " \n");
        }
        sb.append("--------------- \n");
        return sb.toString();
    }
}
