package com.su.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.su.dto.OrderDTO;
import com.su.enums.ResultEnum;
import com.su.exception.SellException;
import com.su.form.OrderForm;
import com.su.model.OrderDetail;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 转化器
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getPhone());

        // 使用gson作为json格式转换组件
        Gson gson = new Gson();
        try {
            List<OrderDetail> orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
            orderDTO.setOrderDetailList(orderDetailList);
        } catch (Exception e){
            log.error("【参数转化】json格式错误，items={}", orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        return orderDTO;
    }
}
