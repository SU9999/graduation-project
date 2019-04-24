package com.su.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 原生的微信API的使用
 * 微信相关的API接口的Controller：如微信认证，微信授权等
 */
@RestController
@RequestMapping("/weixin")
@Slf4j
public class WeiXinController {

    /**
     * 1 第一步：用户同意授权，获取code:
     *  此操作通过访问微信API的认证，通过回调的方式访问该接口，同时传入code参数
     * */
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
        log.info("进入auth方法。。。。");
        log.info("code={}", code);

        /**
         * 1 第一步：用户同意授权，获取code
         *
         * 2 第二步：通过code换取网页授权access_token
         *
         * 3 第三步：刷新access_token（如果需要）
         *
         * 4 第四步：拉取用户信息(需scope为 snsapi_userinfo)
         *
         * 5 附：检验授权凭证（access_token）是否有效
         */

       /** 2 第二步：通过code换取网页授权access_token */
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxe227becfe60132e2&secret=534253ebd7cdae3711f401a3c4b13ecb&code=" +
                code + "&grant_type=authorization_code";
        // 通过RestTemplate访问该url，获得json格式数据
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("response={}", response);
    }
}
