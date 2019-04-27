package com.su.service;

import com.su.model.SellerInfo;

/**
 * 卖家端Service层接口
 */
public interface SellerService {

    SellerInfo findSellerInfoByEmail(String email);

    SellerInfo save(SellerInfo sellerInfo);
}
