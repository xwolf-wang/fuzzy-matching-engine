package com.xwolf.os;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class classIdentityMatch {
    public static void main(String[] args) {
        // 测试是否为合法的身份证号码
        String[]id_cards = { "130681198712092019","13068119871209201x","13068119871209201","123456789012345",
                "12345678901234x","1234567890123"};

        // 测试是否为合法身份证的正则表达式
        Pattern pattern = Pattern.compile("(\\d{17}[0-9a-zA-Z]|\\d{14}[0-9a-zA-Z])");

        // 用于提取出生日字符串的正则表达式
        Pattern pattern1 = Pattern.compile("\\d{6}(\\d{8}).*");
        // 用于将生日字符串分解为年月日的正则表达式
        Pattern pattern2 = Pattern.compile("(\\d{4})(\\d{2})(\\d{2})");

        Matcher matcher = pattern.matcher("");
        for (int i = 0; i < id_cards.length; i++) {
            matcher.reset(id_cards[i]);
            System.out.println(id_cards[i] + " is id cards：" + matcher.matches());

            // 如果它是一个合法的身份证号，提取出出生的年月日
            if (matcher.matches()) {
                Matcher matcher1 = pattern1.matcher(id_cards[i]);
                matcher1.lookingAt();
                String birthday = matcher1.group(1);

                Matcher matcher2 = pattern2.matcher(birthday);
                if (matcher2.find()) {
                    System.out.println("它对应的出生年月日为：" + matcher2.group(1) + "年" + matcher2.group(2) + "月"
                            +matcher2.group(3) + "日");
                }

            }

            System.out.println();
        }
    }

}
