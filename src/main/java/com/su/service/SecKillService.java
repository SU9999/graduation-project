package com.su.service;

/**
 * 模拟高并发下的多线程访问Service：秒杀系统
 */
public interface SecKillService {
    /** 查询秒杀商品的信息 */
    String querySecKillProductInfo(String productId);

    /** 秒杀商品下单操作 */
    void orderProductMockDiffUser(String productId);
}
