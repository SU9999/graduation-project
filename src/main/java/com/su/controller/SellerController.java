package com.su.controller;

import com.su.constant.CookieConstant;
import com.su.constant.RedisConstant;
import com.su.converter.SellerInfoForm2SellerInfoConverter;
import com.su.enums.ResultEnum;
import com.su.form.SellerInfoForm;
import com.su.model.SellerInfo;
import com.su.service.SellerService;
import com.su.util.CookieUtil;
import com.su.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户相关的Controller：用户注册，用户登录等
 */
@Controller
@RequestMapping("/seller")
@Slf4j
public class SellerController {

    @Autowired
    private SellerService sellerService;

    /**
     * 使用Redis
     */
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 由于登录需要向cookie中设置token，因此需要传入HttpServletResponse对象
     */
    @PostMapping("/login")
    public ModelAndView login(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpServletResponse response,
                              Map<String, Object> map) {
        // 首先根据email冲数据库中查询该用户
        SellerInfo sellerInfo = sellerService.findSellerInfoByEmail(email);
        if (sellerInfo == null) {
            log.error("【用户登录】该用户未注册，email={}", email);
            map.put("msg", ResultEnum.USER_NOT_REGISTER.getMsg());
            map.put("url", "/sell/seller/index");
            return new ModelAndView("common/error", map);
        }

        // 判断该用户的密码是否正确:如果不匹配，则返回错误页面
        if (StringUtils.isEmpty(password) || !password.equals(sellerInfo.getPassword())) {
            log.error("【用户登录】用户名密码不正确，email={}", email);
            map.put("msg", ResultEnum.USER_PASSWORD_ERROR.getMsg());
            map.put("url", "/sell/seller/index");
            return new ModelAndView("common/error", map);
        }

        // 登录成功，进入成功页面，跳转到商品列表页
        setEmailToSession(response, email);

        map.put("msg", ResultEnum.USER_LOGIN_SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid SellerInfoForm sellerInfoForm,
                                 BindingResult bindingResult,
                                 HttpServletResponse response,
                                 Map<String, Object> map) {
        // Step1：进行表单验证
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            log.error("【用户注册】注册信息有误，errorMsg={}", errorMsg);
            map.put("msg", errorMsg);
            map.put("url", "/sell/seller/register-index");
            return new ModelAndView("common/error", map);
        }

        // Step2: 对两次输入的密码进行校验，是否相同
        String password = sellerInfoForm.getPassword();
        String rePassword = sellerInfoForm.getRePassword();
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(rePassword) || !password.equals(rePassword)) {
            log.error("【用户注册】注册信息有误，两次密码不匹配");
            map.put("msg", ResultEnum.USER_REGISTER_FAILED.getMsg());
            map.put("url", "/sell/seller/register-index");
            return new ModelAndView("common/error", map);
        }

        // Step3：校验通过，则将该用户写入到数据库中
        SellerInfo sellerInfo = SellerInfoForm2SellerInfoConverter.convert(sellerInfoForm);
        // 设置主键
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerService.save(sellerInfo);

        // 注册成功，进入成功页面，直接跳转到商品列表页，而无需再次登录
        setEmailToSession(response, sellerInfo.getEmail());

        map.put("msg", ResultEnum.USER_REGISTER_SUCCESS.getMsg());
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    /**
     * 将email设置到Redis中，同时设置token到Cookie中
     */
    private void setEmailToSession(HttpServletResponse response, String email) {
        // Step1: 设置token到redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        // 设置到redis中，同时设置token过期时间
        redisTemplate.opsForValue().set(RedisConstant.TOKEN_PREFIX + token, email, expire, TimeUnit.SECONDS);

        // Step2: 设置token到cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.MAXAGE);
    }

    /**
     * 注销：需要从Cookie和Redis中清除数据
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                               HttpServletResponse response,
                               Map<String, Object> map) {
        // Step1: 获取Cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie != null) {
            String token = cookie.getValue();
            // Step2: 清除Redis中的数据
            redisTemplate.opsForValue().getOperations().delete(RedisConstant.TOKEN_PREFIX + token);

            // Step3: 清除Cookie: 即将相应的Cookie的过期时间设置为0
            CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        }

        // 清除成功，登出，返回成功页面，同时指定跳转到登录页面
        map.put("msg", ResultEnum.USER_LOGOUT_SUCCESS.getMsg());
        map.put("url", "/sell/seller/index");
        return new ModelAndView("common/success", map);
    }
}
