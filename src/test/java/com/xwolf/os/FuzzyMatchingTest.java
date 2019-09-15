package com.xwolf.os;

import com.xwolf.os.matching.AverageFuzzyMatchingStratergy;
import com.xwolf.os.matching.AverageFuzzyMatchingResult;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @author ming
 * @Description:
 * @create 2019-09-13 11:37 PM
 **/
public class FuzzyMatchingTest {

    @Test
    public void match(){
        String a="11111.111111101";
        String b = "11111.111111111";
        AverageFuzzyMatchingResult result = AverageFuzzyMatchingStratergy.match(a,b);
        System.out.println(result.toString());
        System.out.println(result.average());


        int v = (int) ((new BigDecimal((float) (1000.0001111 - 1000.000111)/0.000001).setScale(7, BigDecimal.ROUND_DOWN).doubleValue())*100);
        String str = v+"%";
        System.out.println(str);

    }
}
