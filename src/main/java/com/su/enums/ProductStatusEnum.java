package com.su.enums;

import lombok.Data;
import lombok.Getter;

/**
 * 商品状态信息：使用枚举表示
 * UP：在架
 * DOWN：下架
 */
@Getter
public enum  ProductStatusEnum {
    UP(0, "在架"),
    DOWN(1, "下架")
    ;

    private int code;
    private String message;

    private ProductStatusEnum(int code, String message){
        this.code = code;
        this.message = message;
    }
}
