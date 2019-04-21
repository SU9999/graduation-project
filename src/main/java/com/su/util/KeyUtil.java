package com.su.util;

import java.util.Random;

/**
 * 生成键的工具类
 */
public class KeyUtil {

    /** 生成唯一主键: 当前时间戳+6位随机数 */
    // 为避免多线程下主键重复，使用synchronized同步机制
    public static synchronized String genUniqueKey(){
        long time = System.currentTimeMillis();
        Random random = new Random(time);
        // 生成6位随机数
        Integer rand = random.nextInt(900000) + 100000;

        return time + String.valueOf(rand);
    }
}
