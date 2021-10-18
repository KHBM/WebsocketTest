package com.foxrain.sheep.whileblack;

import java.util.function.Function;

/**
 * Created with intellij IDEA.
 * by 2021 07 2021/07/02 10:39 오전 02
 * User we at 10 39
 * To change this template use File | Settings | File Templates.
 *
 * @author foxrain
 */
public class FoxSaying
{
    static class Tuple<T, U>
    {
        private final T t;
        private final U u;

        public Tuple(T t, U u)
        {
            this.t = t;
            this.u = u;
        }

        public static <T, U> Tuple of(T t, U u)
        {
            return new Tuple<>(t, u);
        }
    }

    private static int F(int x)
    {
        return x + 1;
    }

    private static int G(int x)
    {
        return x - 2;
    }

    public static void main2(String[] args)
    {
        final Tuple<Integer, String> combine = composeWithX(x -> F_(x), x -> G_(x), 43);
        System.out.println(combine.t);
    }

    private static Tuple<Integer, String> F_(int x)
    {
        return Tuple.of(x + 1, "F was called");
    }

    private static Tuple<Integer, String> G_(int x)
    {
        return Tuple.of(x - 2, "G was called");
    }

    private static Tuple<Integer, String> K_(int x)
    {
        return Tuple.of(x * 2, "K was called");
    }

    private static Tuple<Integer, String> M_(int x)
    {
        return Tuple.of(x / 2, "M was called");
    }

    private static Tuple<Integer, String> N_(int x)
    {
        return Tuple.of(x - 0, "N was called");
    }

    public static void main(String[] args)
    {
        int value = 32;
        int result = F(G(value));

        System.out.println(result); //
        System.out.println();
        /* imposible */
        /* F_(G_(value)); */


        /* But this is possible */
        final Tuple<Integer, String> composedResult = composeWithX(x -> F_(x), x -> G_(x), value);
        System.out.println(composedResult.t + ", " + composedResult.u);
        System.out.println();

        //

        final Tuple<Integer, String> compose = compose(x -> K_(x), compose(x -> M_(x), compose(x -> N_(x), compose(x -> F_(x), G_(value)))));
        System.out.println(compose.t + " and " + compose.u);
        System.out.println();
    }

    public static Tuple<Integer, String> composeWithX(Function<Integer, Tuple<Integer, String>> f, Function<Integer,
        Tuple<Integer, String>> g, Integer x)
    {

        Tuple<Integer, String> gResult = g.apply(x);
        Tuple<Integer, String> fResult = f.apply(gResult.t);

        return Tuple.of(fResult.t, gResult.u + ", " + fResult.u);
    }

    //compose :: ( int → (int, String) ) → ( (int, String) → (int, String) )
    public static Tuple<Integer, String> compose(
        Function<Integer, Tuple<Integer, String>> function1
        , Tuple<Integer, String> function2Result
    )
    {
        final String result2String = function2Result.u;
        final Tuple<Integer, String> resultFunction1Applied = function1.apply(function2Result.t);
        return Tuple.of(resultFunction1Applied.t, resultFunction1Applied.u + ", " + result2String);
    }





    static class IO {

    }

    {

    }

    static class Fc {
        static Tuple<Integer, String> compose(Function<Integer
            , Tuple<Integer, String>> function1, Tuple<Integer, String> function2Result)
        {
            final String result2String = function2Result.u;
            final Tuple<Integer, String> resultFunction1Applied = function1.apply(function2Result.t);
            return Tuple.of(resultFunction1Applied.t, resultFunction1Applied.u + ", " + result2String);
        }
    }
}
