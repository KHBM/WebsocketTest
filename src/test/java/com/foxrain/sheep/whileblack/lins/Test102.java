package com.foxrain.sheep.whileblack.lins;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
public class Test102
{
    public static void main (String[] args) throws java.lang.Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        final List<TempData> intList =
            Arrays.stream(input.split(" ", -1))
                .map(s -> new TempData(Integer.parseInt(s), true)).collect(Collectors.toList());

        if (intList.size() == 1)
            System.out.println(intList.get(0));

        List<TempData> validList = new ArrayList<>();
        for (int i = 0; i < intList.size(); i++)
        {
            final boolean isValidTemperature = isValidTemperature(i, intList);
            final boolean isRangeValid = isValidRange(i, intList);
            if (isRangeValid && isValidTemperature)
                validList.add(intList.get(i));
        }

        int sum = 0;
        for (TempData tempData : validList)
        {
            sum += tempData.getTemp();
        }

        if (validList.isEmpty())
        {
            System.out.println("ERROR");
            return;
        }

        System.out.println((sum/validList.size()));
    }

    private static boolean isValidTemperature(int at, List<TempData> temperatures)
    {
        final Integer targetTemperature = temperatures.get(at).getTemp();
        if (targetTemperature > 200)
            return false;
        if (targetTemperature < -200)
            return false;

        return true && temperatures.get(at).isValid();
    }

    private static boolean isValidRange(int at, List<TempData> temperatures)
    {
        final Integer targetTemperature = temperatures.get(at).getTemp();

        boolean prevCondition = (at < temperatures.size()-1) ?
            Math.abs(targetTemperature - temperatures.get(at + 1).getTemp()) > 2 && temperatures.get(at + 1).isValid(): true;
        boolean nextCondition = (at > 0) ?
            Math.abs(targetTemperature - temperatures.get(at - 1).getTemp()) > 2 && temperatures.get(at - 1).isValid() :
            true;

        if (prevCondition && nextCondition)
        {
            if (at < temperatures.size() -1)
                temperatures.get(at + 1).setValid(false);
            if (at > 0)
                temperatures.get(at - 1).setValid(false);
            return false;
        }

        return true;
    }

    private static class TempData
    {
        int temp;
        boolean valid;

        public TempData(int temp, boolean valid)
        {
            this.temp = temp;
            this.valid = valid;
        }

        void setValid(boolean valid)
        {
            this.valid = valid;
        }

        int getTemp()
        {
            return temp;
        }

        boolean isValid()
        {
            return valid;
        }
    }
}
