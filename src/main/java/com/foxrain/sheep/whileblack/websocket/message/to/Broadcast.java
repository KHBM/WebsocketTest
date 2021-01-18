package com.foxrain.sheep.whileblack.websocket.message.to;

import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/23 10:06 오후 23
 * User we at 22 06
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Getter
@Setter
public class Broadcast {
    @JsonProperty("mapinfo")
    private int[][] mapInfo;
    @JsonProperty("idd")
    private String id;
    private String name;
}
