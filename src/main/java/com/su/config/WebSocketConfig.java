package com.su.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 配置WebSocket
 */
@Component
public class WebSocketConfig {

    /**
     * 注册一个ServerEndpointExporter的Bean，服务端使用WebSocket时需要使用
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
