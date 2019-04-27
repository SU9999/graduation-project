package com.su.repository;

import com.su.model.SellerInfo;
import com.su.util.KeyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    public void saveTest(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setEmail("su999@qq.com");

        SellerInfo result = sellerInfoRepository.save(sellerInfo);
        System.out.println(result);
    }

    @Test
    public void findByOpenidTest(){
        SellerInfo sellerInfo = sellerInfoRepository.findByEmail("su999@qq.com");
        System.out.println(sellerInfo);
    }
}