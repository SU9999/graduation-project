package com.su.enums;

import lombok.Getter;

/**
 * 返回的消息枚举：
 * 包含错误提示，异常等
 */
@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不足"),
    ORDER_NOT_EXIST(12, "订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13, "订单详情表不存在"),
    ORDER_STATUS_ERROR(14, "订单状态不正确"),
    ORDER_UPDATE_FAILED(15, "更新订单状态失败"),
    ORDER_DETAIL_EMPTY(16, "订单详情列表为空"),
    ORDER_PAY_FAILED(17, "支付订单失败"),
    ORDER_PAY_STATUS_ERROR(18, "订单支付状态不正确"),
    PARAM_ERROR(19, "参数不正确"),
    ORDER_CART_EMPTY(20, "订单购物车为空"),
    ORDER_OWNER_ERROR(21, "该订单不属于当前用户，没有访问权限"),
    ;

    private int code;
    private String msg;

    ResultEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
