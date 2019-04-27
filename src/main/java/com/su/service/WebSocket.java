package com.su.service;

import javassist.tools.web.Webserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 处理服务端WebSocket的一个组件
 */
@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    /**
     * 处理新的websocket连接
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
        log.info("【websocket消息】有新的连接，总数：{}", webSockets.size());
    }

    /**
     * websocket连接关闭
     */
    @OnClose
    public void onClose() {
        webSockets.remove(this);
        log.info("【websocket消息】有连接关闭，总数：{}", webSockets.size());
    }

    /**
     * 处理连接发送的消息
     */
    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端发来的消息：{}", message);
    }

    /**
     * 广播消息
     */
    public void sendMessage(String message) {
        for (WebSocket webSocket : webSockets) {
            log.info("【websocket消息】广播消息，message = {}", message);
            try {
                // 广播消息到每一个连接
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("【websocket消息】广播消息失败，message = {}", message);
            }
        }
    }
}
