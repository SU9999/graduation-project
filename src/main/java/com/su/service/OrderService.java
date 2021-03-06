package com.su.service;

import com.su.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 处理订单业务逻辑的接口：Service层
 */
public interface OrderService {

    /** 创建订单 */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询买家订单列表：分页查询*/
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /** 查询单个订单的详情 */
    OrderDTO findOne(String orderId);

    /** 查询所有订单，用于卖家对订单进行管理：分页查询*/
    Page<OrderDTO> findAll(Pageable pageable);

    /** 取消订单*/
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结订单 */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单 */
    OrderDTO pay(OrderDTO orderDTO);
}
