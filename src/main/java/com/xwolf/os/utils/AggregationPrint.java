package com.xwolf.os.utils;

import com.xwolf.os.domain.FuzzyTrade;

import java.util.List;

/**
 * @author ming
 * @Description:
 * @create 2019-10-03 9:19 AM
 **/
public class AggregationPrint {
    public static void print(List<List<FuzzyTrade>> aggregateResult){
        int i=1;
        for(List<FuzzyTrade> result : aggregateResult){
            System.out.println("group " + i);
            System.out.println(result);
            i++;
        }
    }
}
