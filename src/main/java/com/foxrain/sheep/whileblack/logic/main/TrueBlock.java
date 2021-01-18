package com.foxrain.sheep.whileblack.logic.main;

import java.util.List;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/21 6:46 오후 21
 * User we at 18 46
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
public class TrueBlock implements Block
{
    @Override
    public boolean reverse()
    {
        return true;
    }

    @Override
    public boolean isClear()
    {
        return true;
    }

    @Override
    public void click()
    {
        /* do nothing */
    }

    @Override
    public void addSibling(List<Block> siblings)
    {
        /* do nothing */
    }

    @Override
    public long getId()
    {
        return -1L;
    }
}
