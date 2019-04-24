package com.su.service.impl;

import com.su.dto.OrderDTO;
import com.su.service.OrderService;
import com.su.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void createPayment() {
//        OrderDTO orderDTO = new OrderDTO();
        // 从数据库中查询一个订单
        OrderDTO orderDTO = orderService.findOne("1555852731675192816");
        payService.createPayment(orderDTO);
    }
}