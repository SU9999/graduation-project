package com.su.constant;

/**
 * Redis相关配置常量
 */
public interface RedisConstant {
    String TOKEN_PREFIX = "token_";

    Integer EXPIRE = 3600 * 2; //2小时过期时间
}
