
package com.test.test2app.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.BreakIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@SuppressLint("DefaultLocale")
public class StringUtils {

    /***
     * 判断是否纯数字
     * @param str
     * @return
     */
    public static boolean isPhone(String str) {

        //使用正则表达式
        String regex = "[0-9]+";
        return str.matches(regex) && str.length() >= 7 && str.length() <= 20;
    }

    public static String transMapToString(Map<String, Long> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            Long value = map.get(key);
            String vs = value == null ? "0" : value.toString();
            sb.append(key).append(",").append(vs).append("$");
        }
        if (sb.length() >= 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static Map<String, Long> transStringToMap(String mapString) {
        Map<String, Long> map = new HashMap<String, Long>();

        if (TextUtils.isEmpty(mapString)) {
            return map;
        }
        StringTokenizer items;
        StringTokenizer entries = new StringTokenizer(mapString, "$");

        while (entries.hasMoreTokens()) {
            items = new StringTokenizer(entries.nextToken(), ",");
            try {
                map.put(items.nextToken(), Long.valueOf(items.nextToken()));
            } catch (NumberFormatException ignore) {
            }
        }
        return map;
    }

    /**
     * version:2020-02-26
     *
     * @param source
     * @return
     */
    public static int getCharCount(String source, CharIterator charIterator) {
        BreakIterator breakIterator = BreakIterator.getCharacterInstance();
        breakIterator.setText(source);
        int start = breakIterator.first();
        int count = 0;
        for (int end = breakIterator.next(); end != BreakIterator.DONE; start = end, end = breakIterator.next()) {
            count++;
            if (charIterator != null) {
                String oneChar = source.substring(start, end);
                if (TextUtils.isEmpty(oneChar)) {
                    continue;
                }
                if (!charIterator.getCharString(source.substring(start, end), Character.codePointAt(source, start))) {
                    break;
                }
            }
        }
        return count;
    }

    public static int getCharCount(String source) {
        return getCharCount(source, null);
    }

    public interface CharIterator {
        boolean getCharString(String item, int codePoint);
    }
}
