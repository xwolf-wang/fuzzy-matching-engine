package com.xwolf.os;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class classMatcherTest {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("\\d+");

        // matches()对整个字符串进行匹配
        // 返回false，因为bb不能被\d+匹配，导致整个字符串匹配未成功。
        Matcher m = p.matcher("22bb23");
        System.out.println(m.matches());
        m = p.matcher("2223");
        // 返回true，因为\d+匹配到了整个字符串
        System.out.println(m.matches());

        // lookingAt()对字符串前缀进行匹配
        m = p.matcher("22bb23");
        // 返回true,因为\d+匹配到了前面的22
        System.out.println(m.lookingAt());
        m = p.matcher("aa2223");
        // 返回false,因为\d+不能匹配前面的aa
        System.out.println(m.lookingAt());

        // find()对字符串进行匹配,匹配到的字符串可以在任何位置。
        m = p.matcher("22bb23");
        System.out.println(m.find()); // true
        m = p.matcher("aa2223");
        System.out.println(m.find()); // true
        m = p.matcher("aabb");
        System.out.println(m.find()); // false
        // 当匹配器匹配失败时，使用返回匹配器状态的方法将出错，例如：m.start();

        m = p.matcher("aa2223bb");
        System.out.println(m.find()); // true

        System.out.println(m.start()); // 2
        System.out.println(m.end()); // 6
        System.out.println(m.group()); // 2223

        p = Pattern.compile("([a-z]+)(\\d+)");
        m = p.matcher("aaa2223bb");
        // 匹配aaa2223
        m.find();
        // 返回2,因为有2组
        System.out.println(m.groupCount());
        // 返回0, 返回第一组匹配到的子字符串在字符串中的索引号
        System.out.println(m.start(1));
        // 返回3
        System.out.println(m.start(2));
        // 返回3 返回第一组匹配到的子字符串的最后一个字符在字符串中的索引位置.
        System.out.println(m.end(1));
        // 返回2223,返回第二组匹配到的子字符串
        System.out.println(m.group(2));
    }



}
