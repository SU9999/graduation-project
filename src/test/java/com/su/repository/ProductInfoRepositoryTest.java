package com.su.repository;

import com.su.model.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("2");
        productInfo.setProductName("Java进阶课程");
        productInfo.setProductPrice(new BigDecimal(0.02));
        productInfo.setProductStock(20);
        productInfo.setProductDescription("详细介绍Java Web相关基础知识");
        productInfo.setCategoryType(1);
        productInfo.setProductStatus(0);
        productInfo.setProductIcon("http://localhost/javaweb.jpg");

        ProductInfo result = productInfoRepository.save(productInfo);
        System.out.println(result);
    }

    @Test
    public void findByIdTest(){
        Optional<ProductInfo> optional = productInfoRepository.findById("1");
        optional.ifPresent(System.out::println);
    }

    @Test
    public void findByProductStatusTest(){
        List<ProductInfo> productInfoList = productInfoRepository.findByProductStatus(0);
        productInfoList.forEach(System.out::println);
    }
}