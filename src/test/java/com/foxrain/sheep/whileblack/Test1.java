package com.foxrain.sheep.whileblack;

import io.vavr.Function1;
import io.vavr.control.Option;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Created with intellij IDEA.
 * by 2021 02 2021/02/09 5:32 오후 09
 * User we at 17 32
 * To change this template use File | Settings | File Templates.
 *
 * @author foxrain
 */
public class Test1
{
    @Test
    public void composeTest()
    {
        Function1<String, String> toUpper = String::toUpperCase;
        Function1<String, String> trim = String::trim;
        Function1<String, String> cheers = (s) -> String.format("Hello %s", s);
        assertThat(trim
            .andThen(toUpper)
            .andThen(cheers)
            .apply("   john")).isEqualTo("Hello JOHN");
        Function1<String, String> composedCheer =
            cheers.compose(trim).compose(toUpper);
        assertThat(composedCheer.apply(" steve ")).isEqualTo("Hello STEVE");
    }

    @Test
    public void liftTest()
    {
        Function1<String, String> toUpper = (s) -> {
            if (s.isEmpty()) throw new IllegalArgumentException("input can not be null");
            return s.toUpperCase();
        };
        Function1<String, String> trim = String::trim;
        Function1<String, String> cheers = (s) -> String.format("Hello %s", s);
        Function1<String, String> composedCheer = cheers.compose(trim).compose(toUpper);
        Function1<String, Option<String>> lifted = Function1.lift(composedCheer);
                assertThat(lifted.apply("")).isEqualTo(Option.none());
        assertThat(lifted.apply(" steve ")).isEqualTo(Option.some("Hello STEVE"));
    }

    @Test
    public void meoizationTest()
    {
        memoization();
    }

    void memoization() {
        Function1<Integer, Integer> calculate =
            Function1.of(this::aVeryExpensiveMethod).memoized();
        StopWatch watch = new StopWatch();
        watch.start();
        calculate.apply(40);
        System.out.println(watch.getTime());

        calculate.apply(40);
        System.out.println(watch.getTime());

        calculate.apply(50);
        System.out.println(watch.getTime());
    }
    private Integer aVeryExpensiveMethod(Integer number) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return number * number;
    }

    public static void main(String[] args)
    {
        Test1 test1 = new Test1();
        boolean solution = test1.solution(new int[]{0, 0});
        System.out.println("result " + solution);
    }

    public boolean solution(int[] A) { // max cases : 50000
        // write your code in Java SE 8

        int evenCount = getNumbers(A, (e) -> e % 2 == 0);
        int oddCount = getNumbers(A, (e) -> e % 2 == 1);

        System.out.println(evenCount);
        System.out.println(oddCount);

        return evenCount > 0 && oddCount > 0 && evenCount - evenCount == 0;
    }

    private int getNumbers(int[] array, Predicate<Integer> predicate)
    {
        int num = 0;
        for (int i = 0; i < array.length; i ++)
        {
            if ( predicate.test(Math.abs(array[i])) )
                num += 1;
        }
        return num;
    }

    public void main1(String[] args)
    {
        Test1 test1 = new Test1();
        System.out.println(test1.solution1("apple", "pear"));
    }
    public int solution1(String A, String B) {
        // write your code in Java SE 8
        int[] aArray = getAlphabetArray1(A);
        int[] bArray = getAlphabetArray1(B);
//        for ( int i = 0; i < abcdefggs.length; i++)
//        {
//            System.out.println(String.format("At %d was %d", i, abcdefggs[i]));
//        }
        int sum = 0;
        for (int i = 0; i < aArray.length; i++)
        {
            sum += Math.abs(aArray[i] - bArray[i]);
        }
        System.out.println("sum was : "+ sum);

        return 0;
    }
    private int[] getAlphabetArray1(String givenString)
    {
        int[] aArr = new int[26];
        for (char c : givenString.toCharArray())
        {
            int i = c - 'a';
            aArr[i] += 1;
        }
        return aArr;
    }
}
