package com.foxrain.sheep.whileblack.websocket.config;

import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/20 2:41 오후 20
 * User we at 14 41
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer
{

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/ftopic");
//        config.setApplicationDestinationPrefixes("/fapp");
//        config.setUserDestinationPrefix("/fqueue");
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-foxrain")
            .addInterceptors(new HttpHandshakeInterceptor())
//            .setHandshakeHandler(new DefaultHandshakeHandler() {
//
//            public boolean beforeHandshake(
//                ServerHttpRequest request,
//                ServerHttpResponse response,
//                WebSocketHandler wsHandler,
//                Map attributes) throws Exception {
//
//                if (request instanceof ServletServerHttpRequest) {
//                    ServletServerHttpRequest servletRequest
//                        = (ServletServerHttpRequest) request;
//                    HttpSession session = servletRequest
//                        .getServletRequest().getSession();
//                    log.info("Session from inter : {}", session.getId());
//                    attributes.put("sessionId", session.getId());
//                }
//                return true;
//            }})
            .withSockJS();
    }

}
