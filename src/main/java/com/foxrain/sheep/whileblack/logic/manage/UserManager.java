package com.foxrain.sheep.whileblack.logic.manage;

import com.foxrain.sheep.whileblack.logic.main.Board;
import com.foxrain.sheep.whileblack.logic.main.EmptyBoard;
import com.foxrain.sheep.whileblack.logic.service.BoardLevelService;
import com.google.common.collect.Maps;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/23 7:05 오후 23
 * User we at 19 05
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Slf4j
@Component
public class UserManager
{
    private final Map<String, Board> userBoard;
    private final Map<String, Integer> userLevel;
    private final BoardLevelService service;

    @Autowired
    public UserManager(BoardLevelService service)
    {
        this.service = service;
        userBoard = Maps.newConcurrentMap();
        userLevel = Maps.newConcurrentMap();
    }

    public Board getCurrentBoardOfUser(String user)
    {
        return getUserMap(user);
    }

    public Board getNextBoardToUser(String user)
    {
        final int nextLevel = getUserLevel(user) + 1;
        final Board nextBoard = service.getNextLevel(nextLevel);
        if (nextBoard != null)
        {
            replaceUserMap(user, nextBoard);
            setUserLevel(user, nextLevel);
        }
        else
            replaceUserMap(user, new EmptyBoard());

        return getUserMap(user);
    }

    private void resetUserMap(String user)
    {
        final int userLevel = getUserLevel(user);
        setUserLevel(user, userLevel);
        final Board board = service.getNextLevel(userLevel);
        replaceUserMap(user, board);
    }

    private Board getUserMap(String user)
    {
        if (!userBoard.containsKey(user))
        {
            resetUserMap(user);
        }
        return userBoard.get(user);
    }

    private void setUserLevel(String user, int level)
    {
        userLevel.put(user, level);
    }

    private int getUserLevel(String user)
    {
        return userLevel.getOrDefault(user, 9);
    }

    private void replaceUserMap(String user, Board board)
    {
        userBoard.put(user, board);
    }

    public void initialzeUserMap(String user)
    {
        resetUserMap(user);
    }
}
