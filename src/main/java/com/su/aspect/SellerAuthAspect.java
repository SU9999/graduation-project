package com.su.aspect;

import com.su.constant.CookieConstant;
import com.su.constant.RedisConstant;
import com.su.exception.SellerAuthException;
import com.su.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 定义一个AOP切面：用于校验用户是否登录
 */
@Aspect
@Component
@Slf4j
public class SellerAuthAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 定义一个切入点: com.su.controller包下的除了SellerController类的其他Controller的所有方法
     */
    @Pointcut("execution(public * com.su.controller.Seller*.*(..))" +
    " && !execution(public * com.su.controller.SellerController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){

        //获取HttpServletRequest
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //从request获取到token的Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null){
            log.warn("【用户校验】Cookie中没有token值");
            throw new SellerAuthException();
        }

        // 从redis中查询token
        String redisToken = redisTemplate.opsForValue().get(RedisConstant.TOKEN_PREFIX + cookie.getValue());
        if (StringUtils.isEmpty(redisToken)){
            log.warn("【用户校验】Redis中没有token值");
            throw new SellerAuthException();
        }
    }
}
