package com.foxrain.sheep.whileblack.logic.main;

import java.util.List;
import java.util.Optional;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/21 5:04 오후 21
 * User we at 17 04
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
public class MainBoard implements Board
{

    private final List<Block> blockList;
    private final int width;
    private final int height;

    public MainBoard(List<Block> blockList, int width, int height)
    {
        this.blockList = blockList;
        this.width = width;
        this.height = height;
    }

    @Override
    public void select(int id)
    {
        if (id < 0 || id >= blockList.size())
            return;
        final Block selectedBlock = blockList.get(id);
        selectedBlock.click();
    }

    @Override
    public boolean isClear()
    {
        final Optional<Block> any = blockList.stream()
            .filter(b -> !b.isClear())
            .findAny();

        return !any.isPresent();
//        return false;
    }

    @Override
    public List<Block> getMapInfo()
    {
        return this.blockList;
    }

    @Override
    public int getWidth()
    {
        return this.width;
    }

    @Override
    public int getHeight()
    {
        return this.height;
    }

    @Override
    public int[][] toArray()
    {
        int[][] arr = new int[height][width];
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                arr[y][x] = this.blockList.get(y*width+x).isClear() ? 0 : 1;
            }
        }
        return arr;
    }


}
