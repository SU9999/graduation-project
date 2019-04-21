package com.su.repository;

import com.su.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单详情表OrderDetail的DAO层接口
 */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /** 根据订单主表的id（orderId）字段进行查询 */
    List<OrderDetail> findByOrderId(String orderId);
}
