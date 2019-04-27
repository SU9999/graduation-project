package com.su.service.impl;

import com.su.dto.CartDTO;
import com.su.model.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void findById() {
        ProductInfo result = productInfoService.findById("1");
        System.out.println(result);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = productInfoService.findUpAll();
        upAll.forEach(System.out::println);
    }

    @Test
    public void findAll() {
        PageRequest request = PageRequest.of(1, 1);
        Page<ProductInfo> page = productInfoService.findAll(request);
        System.out.println("total page -> " + page.getTotalPages());
        System.out.println("total elements -> " + page.getTotalElements());
        System.out.println("content:");
        page.getContent().forEach(System.out::println);
    }

    @Test
    public void save() {
    }

    @Test
    public void increaseStockTest(){
        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList.add(new CartDTO("1", 11));
        cartDTOList.add(new CartDTO("2", 22));

        productInfoService.increaseStock(cartDTOList);
    }

    @Test
    public void decreaseStockTest(){
        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList.add(new CartDTO("1", 11));
        cartDTOList.add(new CartDTO("2", 10));

        productInfoService.decreaseStock(cartDTOList);
    }

    @Test
    public void findByCategoryTypeTest(){
        List<ProductInfo> productInfoList = productInfoService.findByCategoryType(1110);
        System.out.println(productInfoList);
    }
}