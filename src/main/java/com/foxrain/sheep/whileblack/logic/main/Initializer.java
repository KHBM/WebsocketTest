package com.foxrain.sheep.whileblack.logic.main;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Created with intellij IDEA. 
 * by 2020 12 2020/12/21 5:52 오후 21
 * User we at 17 52
 * To change this template use File | Settings | File Templates.
 * @author foxrain
 */
@Slf4j(topic = "shrink")
public class Initializer
{
    public static Board init(String[][] mapInfoStr, Rule rule)
    {

        List<Block> blocks = Lists.newArrayList();
        for (String[] y : mapInfoStr)
        {
            System.out.print("[");
            for (String x : y)
            {
                System.out.printf("%s,", x);
                blocks.add(new MainBlock(blocks.size(), x.equalsIgnoreCase("1")));
            }
            System.out.println("],");
        }
        Board board = new MainBoard(blocks, mapInfoStr[0].length, mapInfoStr.length);
        for (Block block : blocks)
        {
            block.addSibling(rule.getSiblings(board, block));
            log.info("{} is siblings ", block);
        }
        return board;
    }

    public static void main(String[] args)
    {
        final Board init = init(new String[][]{{"1", "1", "0", "1", "1"},
            {"1", "1", "0", "1", "1"}}, new NineByNineRule());

        init.select(0);
        debugMap(init);
        init.select(7);
        debugMap(init);
        init.select(4);
        debugMap(init);
        init.select(2);
        debugMap(init);
    }

    public static void debugMap(Board init)
    {
        final List<Block> mapInfo = init.getMapInfo();
        for (int y = 0; y < init.getHeight(); y++)
        {
            System.out.print("[");
            for (int x = 0; x < init.getWidth(); x++)
            {
                System.out.print((mapInfo.get(x + y* init.getWidth()).isClear() ? 0 : 1) + ", ");
            }
            System.out.println("]");
        }
        System.out.printf("isClear : %b\n", init.isClear());
        System.out.println();
    }

    public static void createQuery(Board init)
    {
        final List<Block> mapInfo = init.getMapInfo();
        String query = "INSERT INTO board_level(seq, maps, width, height) VALUES (, '";
        for (int loop = 0; loop < mapInfo.size(); loop++)
        {
            if (loop == mapInfo.size() - 1)
                query += (mapInfo.get(loop).isClear() ? 0 : 1);
            else
                query += (mapInfo.get(loop).isClear() ? 0 : 1) + ",";
        }
//        for (int y = 0; y < init.getHeight(); y++)
//        {
//            for (int x = 0; x < init.getWidth(); x++)
//            {
//                if (x == init.getWidth() - 1)
//                    System.out.print((mapInfo.get(x + y* init.getWidth()).isClear() ? 0 : 1));
//                else
//                    System.out.print((mapInfo.get(x + y* init.getWidth()).isClear() ? 0 : 1) + ",");
//            }
//        }
        query += "', " + init.getHeight() + ", " + init.getWidth();
        query += ");";
        System.out.println(query);
    }
}
