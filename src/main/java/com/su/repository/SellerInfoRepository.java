package com.su.repository;

import com.su.model.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String> {

    /** 根据卖家用户邮箱查询该卖家信息 */
    SellerInfo findByEmail(String email);
}
