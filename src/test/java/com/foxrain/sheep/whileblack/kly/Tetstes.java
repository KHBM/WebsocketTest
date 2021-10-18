package com.foxrain.sheep.whileblack.kly;

import org.junit.jupiter.api.Test;

/**
 * Created with intellij IDEA.
 * by 2021 06 2021/06/29 4:19 오후 29
 * User we at 16 19
 * To change this template use File | Settings | File Templates.
 *
 * @author foxrain
 */
public class Tetstes
{

    @Test
    public void mains()
    {
        int[][] s = {{1,4}, {3,4}, {3,10}};
        getAnswer(s);

    }

    private int[] getAnswer(int[][] s)
    {
        int mxx = getMax(s, 0);
        int mnx = getMin(s, 0);
        int mxy = getMax(s, 1);
        int mny = getMin(s, 1);

        if (!isExist(new int[]{mnx, mny}, s))
        {
            return new int[]{mnx, mny};
        }
        if (!isExist(new int[]{mxx, mny}, s))
        {
            return new int[]{mxx, mny};
        }
        if (!isExist(new int[]{mxx, mxy}, s))
        {
            return new int[]{mxx, mxy};
        }
        if (!isExist(new int[]{mnx, mxy}, s))
        {
            return new int[]{mnx, mxy};
        }
        return null;
    }

    private boolean isExist(int[] a, int[][] s)
    {
        for (int[] se : s)
        {
            if (a[0] == se[0] && a[1] == se[1])
                return true;
        }
        return false;
    }


    private int getMin(int[][] arr, int x)
    {
        int min = Integer.MAX_VALUE;
        for (int[] n : arr)
        {
            if ( min > n[x] )
            {
                min = n[x];
            }
        }
        return min;
    }

    private int getMax(int[][] arr, int x)
    {
        int max = Integer.MIN_VALUE;
        for (int[] n : arr)
        {
            if ( max < n[x] )
            {
                max = n[x];
            }
        }

        return max;
    }
}
