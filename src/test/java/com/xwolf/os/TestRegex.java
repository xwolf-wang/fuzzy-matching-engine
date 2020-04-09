package com.xwolf.os;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {
    public static void main(String[] args) {
        //Pattern(正则表达式编译)、Matcher（匹配）
        String str ="INSERT INTO dept (deptno,dname,loc) VALUES (#{deptno},#{dname},#{loc})";
        //要求获取#{}中的所有内容
        String regex ="#\\{\\w+\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            System.out.println(matcher.group(0).replaceAll("#|\\{|\\}",""));
        }

    }
}
