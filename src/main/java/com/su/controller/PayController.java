package com.su.controller;

import com.su.dto.OrderDTO;
import com.su.service.OrderService;
import com.su.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 微信支付相关的Controller。主要用于支付和退款操作
 * 由于涉及到页面重定向，因此使用@Controller而不是@RestController
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public String create(@RequestParam("orderId") String orderId,
                         @RequestParam("returnUrl") String returnUrl){
        // 从数据库中查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);

        return null;
    }
}
