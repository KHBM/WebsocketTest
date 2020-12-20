package com.foxrain.sheep.whileblack.websocket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created with intellij IDEA.
 * by 2020 12 2020/12/20 2:34 오후 20
 * User we at 14 34
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Getter
@Setter
@NoArgsConstructor
public class HelloWorld {
    private String name;
    public HelloWorld(String name) {
        this.name = name;
    }
}
