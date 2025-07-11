package com.pdfmanager.pdfbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket endpoint clients connect to
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // Allow all origins for dev; restrict in prod
                .withSockJS(); // Fallback support
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Topic for broadcasting updates
        registry.enableSimpleBroker("/topic");
        // Prefix for message-mapped methods in @MessageMapping
        registry.setApplicationDestinationPrefixes("/app");
    }
}
