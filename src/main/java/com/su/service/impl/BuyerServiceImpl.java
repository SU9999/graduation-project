package com.su.service.impl;

import com.su.dto.OrderDTO;
import com.su.enums.ResultEnum;
import com.su.exception.SellException;
import com.su.service.BuyerService;
import com.su.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpInc;
import org.springframework.stereotype.Service;

/**
 * 买家通用逻辑接口实现类：Service层
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {

        // 查询出该订单
        OrderDTO orderDTO = orderService.findOne(orderId);

        // 校验openid
        if (!openid.equalsIgnoreCase(orderDTO.getBuyerOpenid())){
            log.error("【查询订单详情】买家openid不一致，不安全的操作, openid={}, orderDTO={}", openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        // 查询出该订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        // 校验openid
        if (!openid.equalsIgnoreCase(orderDTO.getBuyerOpenid())){
            log.error("【取消订单】买家openid不一致，不安全的操作, openid={}, orderDTO={}", openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        OrderDTO result = orderService.cancel(orderDTO);
        return result;
    }
}
