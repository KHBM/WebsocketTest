package com.foxrain.sheep.whileblack.websocket.controller;

import com.foxrain.sheep.whileblack.websocket.message.from.HelloWorld;
import com.foxrain.sheep.whileblack.websocket.message.to.Greeting;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/20 2:38 오후 20
 * User we at 14 38
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/ftopic/greetings")
    public Greeting greeting(HelloWorld message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }
}
