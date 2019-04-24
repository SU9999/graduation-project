package com.su.util;

/**
 * 处理数学相关的工具类
 */
public class MathUtil {

    public static double PRECISION = 0.01;

    /**
     * 用于判断两个double类型变量是否相等：精度判断
     */
    public static boolean equals(Double d1, Double d2) {
        return Math.abs(d1 - d2) < PRECISION ? true : false;
    }
}
