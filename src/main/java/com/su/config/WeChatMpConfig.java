package com.su.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信公众账号相关的配置项
 */
@Configuration
public class WeChatMpConfig {

    @Autowired
    private WeChatAccountConfig accountConfig;

    /**
     * 将WxMpService注册到Spring容器中，
     * 该bean是调用微信API的第三方SDK的主要接口
     * @return
     */
    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    /**
     * 将WxMpService注册到Spring容器中，
     * 该bean是配置微信基本信息的接口
     * @return
     */
    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(accountConfig.getMpAppId());
        wxMpInMemoryConfigStorage.setSecret(accountConfig.getMpAppSecret());

        return wxMpInMemoryConfigStorage;
    }

}
