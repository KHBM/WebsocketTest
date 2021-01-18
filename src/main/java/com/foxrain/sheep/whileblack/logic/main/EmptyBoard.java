package com.foxrain.sheep.whileblack.logic.main;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/23 9:18 오후 23
 * User we at 21 18
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
public class EmptyBoard implements Board
{
    @Override
    public void select(int id)
    {

    }

    @Override
    public boolean isClear()
    {
        return false;
    }

    @Override
    public List<Block> getMapInfo()
    {
        return Lists.newArrayList();
    }

    @Override
    public int getWidth()
    {
        return 0;
    }

    @Override
    public int getHeight()
    {
        return 0;
    }

    @Override
    public int[][] toArray()
    {
        return new int[0][];
    }
}
