package com.foxrain.sheep.whileblack.logic.main;

import java.util.List;

/**
 * Created with intellij IDEA.
 *
 * @author foxrain
 * @created 2020/12/21 1:43 오후
 */
public interface Rule
{
    List<Block> getSiblings(Board board, Block target);
}
