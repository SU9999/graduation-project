package com.su.repository;

import com.su.model.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void findByIdTest(){

    }

    @Test
    public void findByOrderIdTest(){

        List<OrderDetail> result = orderDetailRepository.findByOrderId("1");
        result.forEach(System.out::println);
    }

    @Test
    public void saveTest(){

        for (int i=2; i<10; i++) {
            OrderDetail orderDetail = new OrderDetail();

            orderDetail.setDetailId("detail-id-" + i);
            orderDetail.setProductId("product-id-" + i);
            orderDetail.setOrderId("order-id-" + i);
            orderDetail.setProductName("Java基础课程-" + i);
            orderDetail.setProductPrice(new BigDecimal(0.01));
            orderDetail.setProductQuantity(10);
            orderDetail.setProductIcon("http://java-00" + i + ".jpg");

            OrderDetail result = orderDetailRepository.save(orderDetail);
            System.out.println(result);
        }
    }
}