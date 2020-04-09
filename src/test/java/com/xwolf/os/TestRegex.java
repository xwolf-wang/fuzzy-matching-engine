package com.xwolf.os;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {
    public static void main(String[] args) {
        //Pattern(正则表达式编译)、Matcher（匹配）
        String str ="<fintrade><interfaceId>interfaceid-23r3--dfa</interfaceId><version>22</version></fintrade>";
        //要求获取#{}中的所有内容
        String regex ="<interfaceId>(.*?)</interfaceId>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(0));
//            System.out.println(matcher.group().replaceAll("<interfaceId>","").replaceAll("</interfaceId>",""));
//            System.out.println(matcher.group(0).replaceAll("#|\\{|\\}",""));
        }

    }
}
