package com.foxrain.sheep.whileblack.logic.mapper;

import com.foxrain.sheep.whileblack.logic.entity.BoardLevel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Created with intellij IDEA.
 *
 * @author foxrain
 * @created 2020/12/23 4:25 오후
 */
@Repository
@Mapper
public interface BoardLevelMapper
{
    BoardLevel selectLevel(int seq);
}
