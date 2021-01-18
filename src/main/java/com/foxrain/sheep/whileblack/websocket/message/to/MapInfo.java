package com.foxrain.sheep.whileblack.websocket.message.to;

import lombok.Getter;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/21 8:16 오후 21
 * User we at 20 16
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Getter
public class MapInfo
{
    private final int[][] mapInfo;
    private final boolean isClear;
    private final String code;

    public MapInfo(int[][] mapInfo, boolean isClear, String code)
    {
        this.mapInfo = mapInfo;
        this.isClear = isClear;
        this.code = code;
    }
}
