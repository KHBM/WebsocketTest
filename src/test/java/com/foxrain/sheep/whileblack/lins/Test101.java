package com.foxrain.sheep.whileblack.lins;

import org.junit.jupiter.api.Test;

/**
 * Created with intellij IDEA.
 * by 2021 06 2021/06/29 4:17 오후 29
 * User we at 16 17
 * To change this template use File | Settings | File Templates.
 *
 * @author foxrain
 */
public class Test101
{
    @Test
    public void main()
    {
        String s = "bcd";

        final String onlyAZ = removeAZ(s);
        final int finals = check(onlyAZ);

        System.out.println(finals);
    }

    private String removeAZ(String s)
    {
        return s.replaceAll("[b-y]", "");
    }

    private int check(String target)
    {
        final char[] chars = target.toCharArray();
        char first = 0;
        char second = 0;
        int possible = 0;
        for (int i = 0; i < chars.length-1; i++)
        {
            first = chars[i];
            second = chars[i+1];
            if ( first == second )
                continue;
            else
                possible++;
        }

        return possible;
    }

}