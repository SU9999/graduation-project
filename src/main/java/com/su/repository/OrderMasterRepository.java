package com.su.repository;

import com.su.model.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 订单主表OrderMaster 的DAO层
 */
@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    /** 根据买家的微信openid进行订单的查询，传入Pageable对象进行分页查询 */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
