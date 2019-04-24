package com.su.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.su.dto.OrderDTO;

/**
 * 处理微信支付和退款相关的Service层接口
 */
public interface PayService {

    /** 完成支付 */
    PayResponse createPayment(OrderDTO orderDTO);

    /** 处理微信支付成功后异步通知的逻辑接口 */
    PayResponse notify(String notifyData);

    /** 申请退款操作 */
    RefundResponse refund(OrderDTO orderDTO);
}
