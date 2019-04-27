package com.su.controller;

import com.su.dto.OrderDTO;
import com.su.enums.ResultEnum;
import com.su.exception.SellException;
import com.su.service.OrderService;
import com.su.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家订单相关的Controller：通过FreeMarker组件，返回一个视图渲染
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {


    @Autowired
    private OrderService orderService;

    /**
     * 卖家操作订单，更改订单状态后，向买家推送模板消息
     * */
    @Autowired
    private PushMessageService pushMessageService;

    /**
     * 订单列表
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findAll(request);
        map.put("orderPage", orderDTOPage);
        // 将page传回给前端：用于处理分页
        map.put("currentPage", page);
        map.put("size", size);

        return new ModelAndView("order/list", map);
    }

    /**
     * 取消订单
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        // 查询该订单：查询不到是，返回一个error页面
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findOne(orderId);
            // 执行取消订单操作
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖家取消订单】取消订单失败，orderId={}，msg={}", orderId, e.getMessage());
            // 跳转到错误页面
            map.put("msg", e.getMessage());  // 错误信息
            map.put("url", "/sell/seller/order/list");     // 跳转页面
            return new ModelAndView("common/error", map);
        }

        // 取消订单成功
        // 推送模板消息
        pushMessageService.orderStatusMessage(orderDTO);

        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }

    /**
     * 查询订单详情
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map){

        //查询该订单
        OrderDTO orderDTO = null;

        try {
            orderDTO = orderService.findOne(orderId);
        } catch (SellException e){
            log.error("【卖家查看订单详情】查看详情失败，orderId={}，msg={}", orderId, e.getMessage());
            // 跳转到错误页面
            map.put("msg", e.getMessage());  // 错误信息
            map.put("url", "/sell/seller/order/list");     // 跳转页面
            return new ModelAndView("common/error", map);
        }
        // 查找成功跳转到订单详情页
        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", map);
    }

    /**
     * 完结订单
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> map){
        // 查询该订单：查询不到是，返回一个error页面
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findOne(orderId);
            // 执行取消订单操作
            orderService.finish(orderDTO);
        } catch (SellException e) {
            log.error("【卖家完结订单】完结订单失败，orderId={}，msg={}", orderId, e.getMessage());
            // 跳转到错误页面
            map.put("msg", e.getMessage());  // 错误信息
            map.put("url", "/sell/seller/order/list");     // 跳转页面
            return new ModelAndView("common/error", map);
        }

        // 取消订单成功
        // 推送模板消息
        pushMessageService.orderStatusMessage(orderDTO);

        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }
}
