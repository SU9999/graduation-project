package com.su.enums;

import lombok.Getter;

/**
 * 订单支付状态枚举：
 */
@Getter
public enum PayStatusEnum implements IStatusEnum{
    WAIT(0, "等待支付"),
    SUCCESS(1, "已支付"),
    ;

    private Integer code;
    private String msg;

    PayStatusEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static PayStatusEnum getEnumByCode(int code){
        PayStatusEnum result = null;

        for (PayStatusEnum orderStatusEnum : PayStatusEnum.values()){
            if (orderStatusEnum.getCode() == code){
                result = orderStatusEnum;
                break;
            }
        }

        return result;
    }
}
