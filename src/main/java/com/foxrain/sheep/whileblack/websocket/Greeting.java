package com.foxrain.sheep.whileblack.websocket;

import lombok.Getter;
import lombok.Setter;

/**
 * Created with intellij IDEA.
 * by 2020 12 2020/12/20 2:36 오후 20
 * User we at 14 36
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Getter
@Setter
public class Greeting {
    private String content;
    public Greeting(String content)
    {
        this.content = content;
    }
}
