package com.vienna.jaray.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * WebSocketConfig配置类
 */
@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();

        container.setMaxTextMessageBufferSize(512000);
        container.setMaxBinaryMessageBufferSize(512000);
        container.setMaxSessionIdleTimeout(15 * 60000L);
        return container;
    }
}
