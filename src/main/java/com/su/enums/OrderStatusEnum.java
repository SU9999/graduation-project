package com.su.enums;

import lombok.Getter;
import org.aspectj.weaver.ast.Or;

/**
 * 订单状态枚举：
 */
@Getter
public enum OrderStatusEnum {
    NEW(0, "新订单"),
    FINISHED(1, "完结订单"),
    CANCEL(2, "取消订单")
    ;

    private int code;
    private String msg;

    OrderStatusEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static OrderStatusEnum getEnumByCode(int code){
        OrderStatusEnum result = null;

        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()){
            if (orderStatusEnum.getCode() == code){
                result = orderStatusEnum;
                break;
            }
        }

        return result;
    }
}
