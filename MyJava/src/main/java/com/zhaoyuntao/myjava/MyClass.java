package com.zhaoyuntao.myjava;

import java.util.List;
import java.util.regex.Pattern;

public class MyClass {
    public static final String PATTERN_PHONE_NUMBER = "\\d{1,4}[ \\-]?\\d{1,18}";

    public static void main(String[] args) {
//        S.s(Pattern.compile(PATTERN_PHONE_NUMBER).matcher("a222-2192929").matches());
        List<BluePatternGroupBean> list = PatternUtils.match(PATTERN_PHONE_NUMBER, "asdsadsa2222 2111 1111111sadasd");
        for (BluePatternGroupBean bluePatternGroupBean : list) {
            S.s("---------------------------[" + bluePatternGroupBean.getContent() + "]");
            List<BluePatternGroupItemBean> items = bluePatternGroupBean.getGroupItemBeans();
            for (BluePatternGroupItemBean bluePatternGroupItemBean : items) {
                S.s(bluePatternGroupItemBean.getContent());
            }
        }
    }
}