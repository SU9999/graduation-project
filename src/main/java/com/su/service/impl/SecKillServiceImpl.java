package com.su.service.impl;

import com.su.exception.SellException;
import com.su.service.RedisLock;
import com.su.service.SecKillService;
import com.su.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟高并发下的多线程访问Service：秒杀系统
 */
@Service
@Slf4j
public class SecKillServiceImpl implements SecKillService {
    /*设置超时时间: 1000ms*/
    private static final Integer TIMEOUT = 1000*10;

    @Autowired
    private RedisLock redisLock;

    /**
     * 使用三个map分别模拟商品，库存，订单信息
     * 初始化商品id为123456，库存为100000
     */
    static Map<String, Integer> products;
    static Map<String, Integer> stocks;
    static Map<String, String> orders;

    static {
        products = new HashMap<>();
        stocks = new HashMap<>();
        orders = new HashMap<>();

        products.put("123456", 100000);
        stocks.put("123456", 100000);
    }

    /**
     * 查询当前商品信息
     */
    private String query(String productId) {
        return "秒杀商品，限时抢购，总量为【"
                + products.get(productId)
                + "】份，目前还剩余【"
                + stocks.get(productId)
                + "】份，成功下单用户购买了【"
                + orders.size() + "】份。";
    }

    @Override
    public String querySecKillProductInfo(String productId) {
        return this.query(productId);
    }

    @Override
    public void orderProductMockDiffUser(String productId) {
//        doOrderMockNoLock(productId);
//        doOrderMockBySynchronized(productId);
//        doOrderMockByRedisLoopLock(productId);
        doOrderMockByRedisLock(productId);
    }

    /**
     * 真正的处理订单的业务代码：不加锁
     */
    private void doOrderMockNoLock(String productId) {
        doWork(productId);
    }

    /**
     * 真正的处理订单的业务代码：加synchronized同步锁
     */
    private synchronized void doOrderMockBySynchronized(String productId) {
        doWork(productId);
    }

    /**
     * 真正的处理订单的业务代码：通过Redis实现分布式锁
     */
    private void doOrderMockByRedisLoopLock(String productId) {
        /** Step1: Redis加锁 */
        boolean isLock = false;
        /*采用自旋锁的方式的进行加锁*/
        while (!isLock) {
            String value = String.valueOf(System.currentTimeMillis() + TIMEOUT);
            isLock = redisLock.lock(productId, value);
        }

        /** Step2: 业务代码*/
        doWork(productId);

        /** Step3: Redis解锁*/
        redisLock.unlock(productId);
    }

    /**
     * 真正的处理订单的业务代码：通过Redis实现分布式锁：非自旋锁
     */
    private void doOrderMockByRedisLock(String productId) {
        /** Step1: Redis加锁 */
        /*采用非自旋锁的方式的进行加锁*/
        String value = String.valueOf(System.currentTimeMillis() + TIMEOUT);
        if (!redisLock.lock(productId, value)){
            throw new SellException(101, "访问的人数太多，无法进行下单操作，请稍后重试！！");
        }

        /** Step2: 业务代码*/
        doWork(productId);

        /** Step3: Redis解锁*/
        redisLock.unlock(productId, value);
    }

    /**
     * 真正的处理订单的业务代码
     */
    private void doWork(String productId) {
        // 查询秒杀商品的数量，数量为0，秒杀活动结束
        int stockNum = stocks.get(productId);
        if (stockNum <= 0) {
            throw new SellException(100, "秒杀结束");
        }

        // 模拟下单操作
        orders.put(KeyUtil.genUniqueKey(), productId);
        // 模拟减库存
        stockNum -= 1;

        // 模拟数据库操作：休眠100ms
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        stocks.put(productId, stockNum);
    }
}
