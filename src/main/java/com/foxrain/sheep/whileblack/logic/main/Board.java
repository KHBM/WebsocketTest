package com.foxrain.sheep.whileblack.logic.main;

import java.util.List;

/**
 * Created with intellij IDEA.
 *
 * @author foxrain
 * @created 2020/12/21 1:42 오후
 */
public interface Board
{
    void select(int id);

    boolean isClear();

    List<Block> getMapInfo();

    int getWidth();
    int getHeight();
    int[][] toArray();
}
