package com.su.controller;

import com.lly835.bestpay.model.PayResponse;
import com.su.dto.OrderDTO;
import com.su.service.OrderService;
import com.su.service.PayService;
import com.su.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 微信支付相关的Controller。主要用于支付和退款操作
 * 由于涉及到页面重定向，因此使用@Controller而不是@RestController
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    /**
     * 返回一个ModeAndView，即跳转到一个页面，而不是返回json
     */
//    @PostMapping("/create")
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> map){
        // 从数据库中查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);

        // 调用统一下单API，在微信方创建预支付订单
        PayResponse payResponse = payService.createPayment(orderDTO);
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
        // TODO 由于无法接入微信支付，因此微信不会发出支付成功异步通知
        /**
         * 此处直接修改订单状态，当接入微信支付时，
         * 应该由微信方通过异步通知的方式调用下面的notify接口,
         * 由此方式对订单的状态进行修改。而不是直接修改订单状态
         * */
        orderService.pay(orderDTO);  // 直接修改订单状态，成功接入微信支付后，删除该代码
        return new ModelAndView("pay/create");
    }

    /**
     * 接收微信异步通知: 该接口有微信方进行访问
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){

        // 处理微信异步通知结果
        payService.notify(notifyData);

        // 告知微信端，处理完成
        return new ModelAndView("pay/success");
    }
}
