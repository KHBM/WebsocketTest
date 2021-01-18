package com.foxrain.sheep.whileblack.logic.main;

import java.util.List;

/**
 * Created with intellij IDEA.
 *
 * @author foxrain
 * @created 2020/12/21 1:42 오후
 */
public interface Block
{
    boolean reverse();
    boolean isClear();
    void click();
    void addSibling(List<Block> siblings);
    long getId();
}
