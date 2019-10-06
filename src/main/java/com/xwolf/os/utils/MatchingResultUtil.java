package com.xwolf.os.utils;

import com.xwolf.os.domain.FuzzyTrade;

import java.util.List;

/**
 * @author ming
 * @Description:
 * @create 2019-10-03 9:19 AM
 **/
public class MatchingResultUtil {
    public static String print(List<List<FuzzyTrade>> aggregateResult){
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
}
