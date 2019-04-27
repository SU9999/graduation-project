package com.su.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 利用Redis实现的一个分布式锁机制
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public Boolean lock(String key, String value) {
        /*当key-value设置成功，返回true，说明获得锁*/
        if (redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }

        /*判断锁过期*/
        String currentValue = redisTemplate.opsForValue().get(key); // 获得当前值
        /*如果锁过期：则进行如下操作*/
        if (!StringUtils.isEmpty(currentValue) &&
                Long.parseLong(currentValue) < System.currentTimeMillis()) {
            /*将新值设置到redis中，同时获取到旧的值*/
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            /*当获取的旧值与之前获得到的redis中的当前值相同，则获得锁：防止两个线程同时进入该代码块，造成两个线程同时获得锁 */
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)) {
                return true;
            }
        }
        /*以上情况不成立，返回false，表示不能获得锁*/
        return false;
    }

    /**
     * 解锁：非自旋锁的方式解锁
     */
    public void unlock(String key, String value) {
        String oldValue = redisTemplate.opsForValue().get(key);
        /** 当解锁的value和redis中存储的value相同时，则删除该key*/
        try {
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(value)) {
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        } catch (Exception e) {
            log.error("【Redis解锁】删除key发生错误！");
        }
    }

    /**
     * 解锁：自旋锁的方式解锁
     */
    public void unlock(String key) {
        try {
            redisTemplate.opsForValue().getOperations().delete(key);
        } catch (Exception e) {
            log.error("【Redis解锁】删除key发生错误！");
        }
    }
}
