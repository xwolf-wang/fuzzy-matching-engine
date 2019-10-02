package com.xwolf.os.algo;

import org.springframework.core.OrderComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author ming
 * @Description:
 * @create 2019-10-01 7:35 PM
 **/
public class AggregateAlgo {
    /**
     * 搜索result在list中，有多少种组合
     * @param result
     * @param list
     * @return
     */
    public static List<List<Integer>> searchSingle(int result, List<Integer> list){
        List<List<Integer>> results = new ArrayList<>();
        Collections.sort(list,new OrderComparator());
        searchGroup(result, 0,list,results, new ArrayList<> ());
        return results;
    }
    /**
     *
     * @param result 目标结果
     * @param index 从集合list那个位置开始搜索
     * @param list 源数据
     * @param results 能组成目标结果的结果集
     * @param resultList 中间结果
     */
    public static void searchGroup(int result, int index, List<Integer> list, List<List<Integer>> results, List<Integer> resultList){
        if(index + 1 > list.size()){
            return;
        }
        int flag = sum(resultList,list.get(index),result);
        if(0 == flag){
            if(index < list.size()){
                List<Integer> copyList = new ArrayList<>(resultList);
                searchGroup(result, index + 1, list,results,copyList);
            }
            resultList.add(list.get(index));
            results.add(resultList);
        }else if (-1 == flag){
            List<Integer> copyList = new ArrayList<>(resultList);
            searchGroup(result, index + 1, list, results, copyList);
            resultList.add(list.get(index));
            searchGroup(result, index + 1, list, results, resultList);
        }else{
            searchGroup(result, index + 1, list, results, resultList);
        }
    }
    /**
     * 判断list中的元素和加上element的和与result的关系
     * 1.如果等于result返回0，
     * 2.如果大于result返回1，
     * 3.如果小于result返回-1。
     * @param list
     * @param element
     * @param result
     * @return
     */
    public static int sum(List<Integer> list, int element, int result){
        int sum = element;
        for(int temp : list){
            sum += temp;
        }
        if(sum == result){
            return 0;
        }else{
            return sum > result ? 1 : -1;
        }
    }


}
