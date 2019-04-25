package com.su.service.impl;

import com.su.dto.OrderDTO;
import com.su.model.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerAddress("重庆邮电大学");
        orderDTO.setBuyerOpenid("su9999");
        orderDTO.setBuyerPhone("18890807009");
        orderDTO.setBuyerName("李四");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail detail01 = new OrderDetail();
        detail01.setProductId("1");
        detail01.setProductQuantity(20);
        OrderDetail detail02 = new OrderDetail();
        detail02.setProductId("2");
        detail02.setProductQuantity(3);
        orderDetailList.add(detail01);
        orderDetailList.add(detail02);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        System.out.println(result);
    }

    @Test
    public void findList() {
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> page = orderService.findList("su9999", request);
        System.out.println("total pages -> " + page.getTotalPages());
        System.out.println("total elements -> " + page.getTotalElements());
        System.out.println("Content:");
        page.getContent().forEach(System.out::println);
    }

    @Test
    public void findOne() {
        OrderDTO result = orderService.findOne("1555825295528825136");
        System.out.println(result);
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne("1555825295528825136");
        OrderDTO result = orderService.cancel(orderDTO);
        System.out.println(result);
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne("1555825295528825136");
        OrderDTO result = orderService.finish(orderDTO);
        System.out.println(result);
    }

    @Test
    public void pay() {
        OrderDTO orderDTO = orderService.findOne("1555825295528825136");
        OrderDTO result = orderService.pay(orderDTO);
        System.out.println(result);
    }

    @Test
    public void findAllTest(){
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> page = orderService.findAll(request);
        System.out.println("total pages -> " + page.getTotalPages());
        System.out.println("total elements -> " + page.getTotalElements());
        System.out.println("Content:");
        page.getContent().forEach(System.out::println);
    }
}