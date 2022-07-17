package com.ziroom.suzaku.main.utils;

import java.math.BigDecimal;

/**
 * @author libingsi
 * @date 2021/6/22 10:59
 */
public class StringUtils {
    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return org.apache.commons.lang3.StringUtils.isBlank(str);
    }

    /**
     * 判断字符串是否非空
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static boolean isEmpty(Object str){
        return str == null || "".equals(str);
    }

    /**
     * 判断对象是否存在数据库
     * @param
     * @return
     */
    public static boolean isExist(Object obj) {
        if (obj instanceof Integer) {
            int value = ((Integer) obj).intValue();
            return value != 0 && isEmpty(value);
        }
        if (obj instanceof String) {
            String str = (String) obj;
            return !isBlank(str);
        }
        if (obj instanceof Long) {
            long value = (long) obj;
            return value != 0 && isEmpty(value);
        }

        return false;
    }

    /**
     * 转换为String类型
     * @param obj
     * @return
     */
    public static String toString(Object obj){
        if(obj == null){
            return "";
        }else{
            return obj.toString();
        }
    }

    /**
     * 左补齐
     * @param s
     * @param n
     * @param replace
     * @return
     */
    public static String lpad(String s, int n, String replace) {
        while (s.length() < n) {
            s = replace+s;
        }
        return s;
    }

    /**
     * 右补齐
     * @param s
     * @param n
     * @param replace
     * @return
     */
    public static String rpad(String s, int n, String replace) {
        while (s.length() < n) {
            s = s+replace;
        }
        return s;
    }
}
