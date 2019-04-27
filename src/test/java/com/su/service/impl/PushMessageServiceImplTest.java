package com.su.service.impl;

import com.su.dto.OrderDTO;
import com.su.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageServiceImpl pushMessageService;
    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatusMessage() {
        OrderDTO orderDTO = orderService.findOne("1556093851410104622");
        pushMessageService.orderStatusMessage(orderDTO);
    }
}