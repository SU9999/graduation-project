package com.su.controller;

import com.su.converter.OrderForm2OrderDTOConverter;
import com.su.dto.OrderDTO;
import com.su.enums.ResultEnum;
import com.su.exception.SellException;
import com.su.form.OrderForm;
import com.su.model.OrderDetail;
import com.su.model.OrderMaster;
import com.su.repository.OrderDetailRepository;
import com.su.repository.OrderMasterRepository;
import com.su.service.BuyerService;
import com.su.service.OrderService;
import com.su.util.ResultVOUtil;
import com.su.viewobject.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 买家订单Controller：实现买家订单相关的接口API
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMasterRepository masterRepository;

    @Autowired
    private OrderDetailRepository detailRepository;

    @Autowired
    private BuyerService buyerService;

    /** 创建订单 */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult){

        // 校验表单参数是否正确
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        /** 创建表单 */
        log.error("【创建表单，BuyerOrderController】openid={}", orderForm.getOpenid());
        // 通过orderForm对象中的参数，构造orderDTO对象
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车为空，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_CART_EMPTY);
        }
        OrderDTO resultOrderDTO = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", resultOrderDTO.getOrderId());

        return ResultVOUtil.success(map);
    }

    /** 订单列表 */
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "5") Integer size){
        // 校验openid是否正确
        if (StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid不能为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request = PageRequest.of(page, size);
        Page<OrderDTO> resultPage = orderService.findList(openid, request);

        return ResultVOUtil.success(resultPage.getContent());
    }

    /** 订单详情 */
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        //TODO 不安全的做法，后续优化进行改进
//        OrderDTO orderDTO = orderService.findOne(orderId);


        // 已改进，将具体业务逻辑实现封装到BuyerService中
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    /** 取消订单 */
    @PostMapping("/cancel")
    public ResultVO<OrderDTO> cancel(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
        //TODO 不安全的做法，后续优化进行改进
//        OrderDTO orderDTO = orderService.findOne(orderId);
//        OrderDTO result = orderService.cancel(orderDTO);


        /*OrderDTO orderDTO = new OrderDTO();
        // 根据orderId查询出所有的OrderMaster对象
        Optional<OrderMaster> optional = masterRepository.findById(orderId);
        try {
            OrderMaster orderMaster = optional.get();
            BeanUtils.copyProperties(orderMaster, orderDTO);
        } catch (NoSuchElementException e){
            log.error("【取消订单】订单不存在，orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // 根据orderId查询出所有的OrderDetail对象
        List<OrderDetail> orderDetailList = detailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)){
            log.error("【取消订单】订单详情列表不存在或为空，orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        orderDTO.setOrderDetailList(orderDetailList);*/

        // 改进后的做法：将具体优化的业务实现封装到BuyerService中
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
