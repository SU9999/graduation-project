package com.su.service;

import com.su.dto.OrderDTO;

/**
 * 处理买家通用逻辑的接口：
 */
public interface BuyerService {

    /** 查询一个订单详情 */
    OrderDTO findOrderOne(String openid, String orderId);

    /** 取消订单 */
    OrderDTO cancelOrder(String openid, String orderId);
}
