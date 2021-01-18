package com.foxrain.sheep.whileblack.websocket.config;

import static org.springframework.messaging.simp.stomp.StompHeaders.SESSION;

import java.security.Principal;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/22 12:31 오전 22
 * User we at 00 31
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Slf4j
public class HttpHandshakeInterceptor implements HandshakeInterceptor
{

//    @Override
//    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
//                                      Map<String, Object> attributes)
//    {
//        return super.determineUser(request, wsHandler, attributes);
//    }
    //    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
//                                   Map attributes) {
//        if (request instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//            HttpSession session = servletRequest.getServletRequest().getSession();
//            attributes.put(SESSION, session);
//        }
//        return true;
//    }
    @Override
    public boolean beforeHandshake(
        ServerHttpRequest request,
        ServerHttpResponse response,
        WebSocketHandler wsHandler,
        Map attributes) {

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest
                = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest
                .getServletRequest().getSession();
            attributes.put("sessionId", session.getId());
            log.info("What session : {}", session.getId());
        }
        return true;
    }
//
    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
    }
}
