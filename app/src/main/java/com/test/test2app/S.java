package com.test.test2app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * packaged LOG class
 */
public class S {

    public static boolean DEBUG = true;

    protected static final String tag = "abcd";
    protected static final String tag1 = "abcde";
    protected static final String tag2 = "abcdef";
    protected static final String tag3 = "abcdefg";

    public static final int D = 0;
    public static final int E = 1;

    private static void log(String tag, Object o, int type) {
        if (!DEBUG) {
            return;
        }

        final Throwable t =  new Throwable() ;
        final StackTraceElement[] elements = t != null ? t.getStackTrace() : null;

        String callerClassName = (elements != null && elements.length > 3) ? elements[3].getClassName() : "N/A";
        String callerMethodName = (elements != null && elements.length > 3) ? elements[3].getMethodName() : "N/A";
        String callerLineNumber = (elements != null && elements.length > 3) ? String.valueOf(elements[3].getLineNumber()): "N/A";

        int pos = callerClassName.lastIndexOf('.');
        if (pos >= 0) {
            callerClassName = callerClassName.substring(pos + 1);
        }


        if (o == null) {
            o = "null";
        } else if (o instanceof Exception) {
            o = ((Exception) (o)).getMessage();
        }

        String usingSource = "<" + callerClassName + "." + callerMethodName + " "+callerLineNumber+"> ";

        StackTraceElement[] traceElements=Thread.currentThread().getStackTrace();
        StringBuilder taskName = new StringBuilder();
        if (traceElements != null && traceElements.length > 6) {
            StackTraceElement traceElement = traceElements[5];
            taskName.append("(").append(traceElement.getFileName()).append(":").append(traceElement.getLineNumber()).append(")  ");
            taskName.append(traceElement.getMethodName());
            usingSource=taskName.toString();
        }


        String tagTmp = "|" + tag + "|   ";
        switch (type) {
            case E:
                Log.e(tagTmp , usingSource);
                Log.e(tagTmp , o.toString());
                break;
            case D:
                Log.i(tagTmp , usingSource);
                Log.d(tagTmp , o.toString());
                break;
        }

    }


    class Lock {
    }
    //internal method--------------------------------------

    private static void s_self(Object o, int type) {
        log(tag, o, type);
    }

    private static void s_self(String tag, Object o, int type) {
        log(tag, o, type);
    }

    private static void ss_self(Object o, int type) {
        log(tag1, o, type);
    }

    private static void sss_self(Object o, int type) {
        log(tag2, o, type);
    }

    private static void ssss_self(Object o, int type) {
        log(tag3, o, type);
    }

    private static void e_self(Object o, int type) {
        log(tag, o, type);
    }

    private static void e_self(String tag, Object o, int type) {
        log(tag, o, type);
    }

    private static void ee_self(Object o, int type) {
        log(tag1, o, type);
    }

    private static void eee_self(Object o, int type) {
        log(tag2, o, type);
    }

    private static void eeee_self(Object o, int type) {
        log(tag3, o, type);
    }


    //open method------------------------------------------
    //normal log just show
    public static void l() {
        s("------------------");
    }

    public static void ll() {
        s("-----------------------------------------");
    }

    public static void lll() {
        s("-----------------------------------------------------------------------");
    }

    public static void s(Object o) {
        s_self(o, D);
    }

    public static void s(String tag, Object o) {
        s_self(tag, o, D);
    }

    public static void ss(Object o) {
        ss_self(o, D);
    }

    public static void sss(Object o) {
        sss_self(o, D);
    }

    public static void ssss(Object o) {
        ssss_self(o, D);
    }

    //normal log just show

    public static void e(Object o) {
        e_self(o, E);
    }

    public static void e(String tag, Object o) {
        e_self(tag, o, E);
    }

    public static void ee(Object o) {
        ee_self(o, E);
    }

    public static void eee(Object o) {
        eee_self(o, E);
    }

    public static void eeee(Object o) {
        eeee_self(o, E);
    }


    private final static String ip_regular = "((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))" + "\\" + ".){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))";
    private final static String port_regular = "^([1-9][0-9]{0," + "3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]{1}|6553[0-5])$";
    private final static String reg_url = "(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";

    public static byte[] longToByteArr(long b) {
        byte[] tmp = new byte[8];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = (byte) ((b >> (8 * i)) & 0xff);
        }
        return tmp;
    }

    public static long byteArrToLong(byte[] c) {
        long tmp = 0;
        if (c != null && c.length == 8) {
            for (int i = 0; i < 8; i++) {
                tmp |= ((c[i] & 0xff) << (8 * i));
            }
        }
        return tmp;
    }

    public static byte[] intToByteArr(int b) {
        byte[] tmp = new byte[4];
        for (int i = 0; i < tmp.length; i++) {
            tmp[i] = (byte) ((b >> (4 * i)) & 0xff);
        }
        return tmp;
    }

    public static int byteArrToInt(byte[] c) {
        int tmp = 0;
        if (c != null && c.length == 4) {
            for (int i = 0; i < 4; i++) {
                tmp |= ((c[i] & 0xff) << (4 * i));
            }
        }
        return tmp;
    }
    /**
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str) {
        return !TextUtils.isEmpty(str);
    }

    public static boolean isIp(String ip) {
        if (S.isEmpty(ip)) {
            return false;
        }
        if (Pattern.compile(ip_regular).matcher(ip + "").matches()) {
            return true;
        } else {
            // e("错误的ip格式");
            return false;
        }
    }

    public static boolean isUrl(String url) {
        if (S.isEmpty(url)) {
            return false;
        }
        if (Pattern.compile(reg_url).matcher(url + "").matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isPort(int port) {
        if (Pattern.compile(port_regular).matcher(port + "").matches()) {
            return true;
        } else {
            return false;
        }
    }


    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static int[] concat(int[] first, int[] second) {
        return concat(first, second, second.length);
    }

    public static int[] concat(int[] first, int[] second, int length_second) {
        int[] result = Arrays.copyOf(first, first.length + length_second);
        System.arraycopy(second, 0, result, first.length, length_second);
        return result;
    }

    public static byte[] concat(byte[] first, byte[] second) {
        return concat(first, second, second.length);
    }

    public static byte[] concat(byte[] first, byte[] second, int length_second) {
        byte[] result = Arrays.copyOf(first, first.length + length_second);
        System.arraycopy(second, 0, result, first.length, length_second);
        return result;
    }

    public static <T> void cpArr(T[] src, int srcPos, T[] dest, int destPos, int length) {
        try {
            System.arraycopy(src, srcPos, dest, destPos, length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] copyOf(byte[] arr, int i) {
        try {
            return Arrays.copyOf(arr, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] copyOfRange(byte[] arr, int start, int end) {
        try {
            return Arrays.copyOfRange(arr, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String addTab(String name, int num) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.length(); ) {
            if (i + num < name.length()) {
                sb.append(name.substring(i, i + num) + "\n");
                i += num;
            } else {
                sb.append(name.substring(i, name.length()));
                break;
            }
        }
        return sb.toString();
    }

    public static String a2s(byte[] arr_head) {
        return Arrays.toString(arr_head);
    }

    public static void cpArr(int[] src, int srcPos, int[] dest, int destPos, int length) {
        try {
            System.arraycopy(src, srcPos, dest, destPos, length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cpArr(byte[] src, int srcPos, byte[] dest, int destPos, int length) {
        try {
            System.arraycopy(src, srcPos, dest, destPos, length);
        } catch (Exception e) {
//            S.e("Arr copy err:ArryaIndexOutOfBoudsException");
//			e.printStackTrace();
        }
    }

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * local to utc
     *
     * @return
     */
    public static String local2UTC() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        String gmtTime = sdf.format(new Date());
        return gmtTime;
    }

    /**
     * utc to local
     *
     * @param utcTime
     * @return
     */
    public static String utc2Local(String utcTime) {
        SimpleDateFormat utcFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//UTC
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//local
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }

    public static long currentTimeSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    public static String formatDate(long time, String formation) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formation);
        return simpleDateFormat.format(new Date(time));
    }

    public static String now() {
        return formatDate(currentTimeMillis(), "yyyy-MM-dd hh:mm:ss:ss");
    }

    public static String now_hms() {
        return formatDate(currentTimeMillis(), "hh:mm:ss");
    }

    public static String formatNumber(double number) {
        return formatNumber(number, "#.#");
    }

    public static String formatNumber(double number, String formation) {
        DecimalFormat decimalFormat = new DecimalFormat(formation);
        return decimalFormat.format(number);
    }

    public static String getRegexString(String srcString, String regexString) {
        Pattern pattern = Pattern.compile(regexString);
        Matcher m = pattern.matcher(srcString);
        while (m.find()) {
            return m.group(1);
        }
        return "";
    }


    public static String format(String dateSrc, String format_src, String format_des) {
        if (S.isEmpty(dateSrc) || S.isEmpty(format_src) || S.isEmpty(format_des)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat_src = new SimpleDateFormat(format_src);
        SimpleDateFormat simpleDateFormat_des = new SimpleDateFormat(format_des);
        try {
            Date date_src = simpleDateFormat_src.parse(dateSrc);
            return simpleDateFormat_des.format(date_src);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * more days of date2 than date1
     *
     * @return
     */
    public static long differentDays(String day_small, String day_big) {
        if (day_small == null || day_big == null || !Pattern.compile("\\d{8}").matcher(day_small).matches() || !Pattern.compile("\\d{8}").matcher(day_big).matches()) {
            return -1;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        long count = 0;
        try {
            Date date_before = simpleDateFormat.parse(day_small);
            Date date_after = simpleDateFormat.parse(day_big);
            count = (date_after.getTime() - date_before.getTime()) / 86400000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return count;
    }

}
