package com.su.service;

import com.su.dto.OrderDTO;

/**
 * 微信模板详细相关的Service
 */
public interface PushMessageService {

    /** 订单状态变化push的模板消息 */
    void orderStatusMessage(OrderDTO orderDTO);
}
