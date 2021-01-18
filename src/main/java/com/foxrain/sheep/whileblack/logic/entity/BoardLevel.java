package com.foxrain.sheep.whileblack.logic.entity;

import java.util.Arrays;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/23 4:26 오후 23
 * User we at 16 26
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Getter
@Setter
public class BoardLevel
{
    private int seq;
    private String maps;
    private int width;
    private int height;
    private String[][] mapInfoArr;

    public void setMaps(String maps)
    {
        this.maps = maps;
        System.out.println("CAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        final String[] token = maps.split(",", -1);
        this.mapInfoArr = new String[getHeight()][getWidth()];

        for (int y = 0; y < getHeight(); y++)
        {
            for (int x = 0; x < getWidth(); x++)
            {
                mapInfoArr[y][x] = token[x+y*getWidth()];
            }
        }
    }

    @Override
    public String toString()
    {
        String toS = "BoardLevel{" +
            "seq=" + seq +
            ", maps='" + maps + '\'' +
            ", width=" + width +
            ", height=" + height +
            ", mapInfoArr=";
        for (String[] srr : mapInfoArr)
            toS += Arrays.toString(srr);
            toS += '}';

        return toS;
    }
}
