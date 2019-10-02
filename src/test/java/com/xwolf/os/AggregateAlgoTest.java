package com.xwolf.os;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.xwolf.os.algo.AggregateAlgo.searchSingle;

/**
 * @author ming
 * @Description:
 * @create 2019-10-01 7:36 PM
 **/
public class AggregateAlgoTest {
    @Test
    public void test1(){
        Integer[] intArrays = {1,1,2,2,3,4,5,6,7,8,10};
        List<Integer> list = new ArrayList<>(intArrays.length);
        Collections.addAll(list, intArrays);
        List<List<Integer>> results = searchSingle(8,list);
        for(List<Integer> result : results){
            System.out.println(result);
        }
    }
}
