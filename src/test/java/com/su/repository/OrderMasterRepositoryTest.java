package com.su.repository;

import com.su.model.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Test
    public void findByIdTest(){

    }

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();

        orderMaster.setOrderId("1");
        orderMaster.setBuyerName("张三");
        orderMaster.setBuyerPhone("18890807009");
        orderMaster.setOrderAmount(new BigDecimal(0.01));
        orderMaster.setBuyerOpenid("su9999");
        orderMaster.setBuyerAddress("重庆邮电大学");

        OrderMaster result = orderMasterRepository.save(orderMaster);
        System.out.println(result);
    }

    @Test
    public void findByBuyerOpenidTest(){
        PageRequest request = PageRequest.of(0,2);
        Page<OrderMaster> page = orderMasterRepository.findByBuyerOpenid("su9999", request);
        System.out.println("total elements -> " + page.getTotalElements());
        System.out.println("total pages -> " +page.getTotalPages());
        System.out.println("Content：");
        page.getContent().forEach(System.out::println);
    }
}