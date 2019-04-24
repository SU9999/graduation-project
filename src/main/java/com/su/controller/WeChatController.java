package com.su.controller;

import com.su.enums.ResultEnum;
import com.su.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;

/**
 * 使用第三方SDK代替原生API实现如下功能：
 * 微信相关的API接口的Controller：如微信认证，微信授权等
 * 由于需要使用重定向redirect，因此不能使用@RestController，而只能使用@Controller
 */

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WeChatController {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 用户访问授权认证的接口：主要用于构造访问微信服务器的http请求
     *
     * @param returnUrl
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        /** Step1：配置微信相关属性: 通过IOC的方式，将WxMpService注册到Spring容器中，已完成相关配置 */

        /** Step2: 调用微信API相关方法 */
        // 构造重定向url地址
        String url = "http://su110.natapp1.cc/sell/wechat/userInfo";
        // 构造访问微信的请求url，重定向到该url，获得code。同时对微信的请求获取到code后会回调指定的参数url
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, returnUrl);
        log.info("【微信授权】获取code，并重定向到{}", redirectUrl);

        // 重定向到返回的url: url前加redirect前缀，表示重定向
        return "redirect:" + redirectUrl;
    }

    /**
     * 微信用户允许授权后，重定向的url，访问该url时，会携带code信息和state
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        System.out.println("【获取微信用户信息】用户重定向。。。");
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = null;
        try {
            // 获取access_token
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
            // 根据access_token获取用户信息
//            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
        } catch (WxErrorException e) {
            log.error("【微信授权】微信授权错误,error={}", e);
            throw new SellException(ResultEnum.WECHAT_MP_AUTH_FAILED);
        }
        // 获取微信用户openid
        String openId = wxMpOAuth2AccessToken.getOpenId();

        String redirectUrl = returnUrl + "?openid=" + openId;
        log.info("[重定向url] url={}", redirectUrl);
        return "redirect:" + redirectUrl;
    }
}
