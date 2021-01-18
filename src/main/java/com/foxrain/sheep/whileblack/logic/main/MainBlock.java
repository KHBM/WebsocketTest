package com.foxrain.sheep.whileblack.logic.main;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.Getter;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/21 5:10 오후 21
 * User we at 17 10
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
public class MainBlock implements Block
{
    private final long id;
    private boolean isWhite;
    private List<Block> coBlocks = Lists.newArrayList();

    public MainBlock(long id, boolean isWhite)
    {
        this.id = id;
        this.isWhite = isWhite;
    }

    @Override
    public boolean reverse()
    {
        isWhite = !isWhite;
        return isWhite;
    }

    @Override
    public boolean isClear()
    {
        return !isWhite;
    }

    @Override
    public void click()
    {
        reverse();
        for (Block subBlock : coBlocks)
        {
            subBlock.reverse();
        }
    }

    @Override
    public void addSibling(List<Block> siblings)
    {
        this.coBlocks = siblings;
    }

    @Override
    public long getId()
    {
        return this.id;
    }


    @Override
    public String toString()
    {
        String s = "MainBlock{" +
            "id=" + id +
            ", isWhite=" + isWhite +
            ", coBlocks=";

            for (Block block : coBlocks)
                s += (block.isClear() ? 0 : 1) + "["+block.getId()+"], ";

            s += '}';
            return s;
    }
}
