package com.foxrain.sheep.whileblack.logic.service;

import static com.foxrain.sheep.whileblack.logic.main.Initializer.init;

import com.foxrain.sheep.whileblack.logic.entity.BoardLevel;
import com.foxrain.sheep.whileblack.logic.main.Board;
import com.foxrain.sheep.whileblack.logic.main.NineByNineRule;
import com.foxrain.sheep.whileblack.logic.mapper.BoardLevelMapper;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/23 4:36 오후 23
 * User we at 16 36
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Service
@Slf4j
public class BoardLevelService {

    private final BoardLevelMapper mapper;

    @Autowired
    public BoardLevelService(BoardLevelMapper mapper)
    {
        this.mapper = mapper;
    }

    @PostConstruct
    public void titi()
    {
        final BoardLevel boardLevel = mapper.selectLevel(0);
        log.info("INITIAL LEVEL L {}", boardLevel);
    }

    public Board getNextLevel(int userLevel)
    {
        final BoardLevel boardLevel = mapper.selectLevel(userLevel);
        if (boardLevel == null)
            return null;
        final Board init = init(boardLevel.getMapInfoArr(), new NineByNineRule());
        return init;
    }
}
