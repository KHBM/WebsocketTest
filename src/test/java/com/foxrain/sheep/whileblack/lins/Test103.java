package com.foxrain.sheep.whileblack.lins;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with intellij IDEA.
 * by 2021 06 2021/06/29 4:18 오후 29
 * User we at 16 18
 * To change this template use File | Settings | File Templates.
 *
 * @author foxrain
 */
public class Test103
{
    @Test
    public void etss() throws InterruptedException
    {
//        int[] bankbook = new int[]{8,4,2,5,3,7};
        int[] bankbook = new int[]{1,2,3,3,3,8};
        int n = 10;

        List<Data> integerList = new ArrayList<>();

        for(int b : bankbook)
        {
            integerList.add(new Data(b, false));
        }

        final List<Data> sorted = integerList.stream().sorted(Comparator.comparingInt(Data::getNum)).collect(Collectors.toList());


        int firstLength = bankbook.length;
        int secondLength = firstLength;

        List<Data> targetList = integerList;

        do {
            firstLength = secondLength;
            final List<Data> newList = group(targetList, n);
            targetList = newList;
            secondLength = newList.size();
        } while ( secondLength < firstLength);

        System.out.println(targetList);
        System.out.println(secondLength + " is the second");
//        return secondLength;
    }

    private List<Data> group(List<Data> banks, int n)
    {
        int maxSize = banks.size();

        List<Data> newList = new ArrayList<>();

        int max = 0;
        int ix = -1, jx = -1;
        for (int i = 0; i < maxSize; i++)
        {
            if (banks.get(i).isAdded()) continue;

            for (int j = i+1; j < banks.size(); j++)
            {
                if (banks.get(j).isAdded()) continue;
                final int sum = banks.get(i).getNum() + banks.get(j).getNum();
                if (sum > n)
                    continue;
                if (sum > max)
                {
                    max = sum;
                    jx = j;
                    ix = i;
                }
            }
        }
        if (ix != -1 && jx != -1)
        {
            banks.get(ix).setAdded(true);
            banks.get(jx).setAdded(true);
            newList.add(new Data(max, false));
        }
        for (Data data : banks)
        {
            if (!data.isAdded())
            {
                newList.add(data);
            }
        }
        System.out.println(newList);
        return newList;
    }

    private class Data
    {
        int num;

        @Override
        public String toString()
        {
            return "Data{" +
                "num=" + num +
                ", added=" + added +
                '}';
        }

        boolean added;

        public Data(int num, boolean added)
        {
            this.num = num;
            this.added = added;
        }

        public int getNum()
        {
            return num;
        }

        public void setNum(int num)
        {
            this.num = num;
        }

        public boolean isAdded()
        {
            return added;
        }

        public void setAdded(boolean added)
        {
            this.added = added;
        }
    }

}