package com.foxrain.sheep.whileblack.logic.main;

import java.util.Arrays;
import java.util.List;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/21 6:28 오후 21
 * User we at 18 28
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
public class NineByNineRule implements Rule
{

    @Override
    public List<Block> getSiblings(Board board, Block target)
    {
        final List<Block> blocks = board.getMapInfo();
        final int cur = blocks.indexOf(target);

        int w = board.getWidth();
        int h = board.getHeight();
        int cx = cur % w;
        int cy = cur / w;

        return Arrays.asList(
            get((cx-1), (cy-1) , blocks, w, h),
            get((cx+0), (cy-1) , blocks, w, h),
            get((cx+1), (cy-1) , blocks, w, h),
            get((cx-1), (cy+0) , blocks, w, h),
            get((cx+1), (cy+0) , blocks, w, h),
            get((cx-1), (cy+1) , blocks, w, h),
            get((cx+0), (cy+1) , blocks, w, h),
            get((cx+1), (cy+1) , blocks, w, h)
        );
    }

    private Block get(int x, int y, List<Block> blocks, int width, int height)
    {
        int index = x + y*width;
        if (x < 0 || x >= width || y < 0 || y >= height || index < 0 || index >= blocks.size())
            return new TrueBlock();
        else
            return blocks.get(index);
    }
}
