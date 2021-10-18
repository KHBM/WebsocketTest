package com.foxrain.sheep.whileblack.util;

import fj.data.List;
import fj.data.Option;

/**
 * Created with intellij IDEA.
 * by 2021 05 2021/05/11 10:04 오전 11
 * User we at 10 04
 * To change this template use File | Settings | File Templates.
 *
 * @author foxrain
 */
public class FFunctionalTesters
{
    public static void main(String[] args)
    {
        try
        {

            List<Integer> fList = List.list(1, 2, 3, 4);
            List<Integer> fList1 = fList.bind(l -> List.list(l, l)); /* 배열 확장 */
            List<Integer> fList2 = fList1.map(i -> i * 2); /* 배열 요소 2배 */

            System.out.println(fList2);

            Option<Integer> option = Option.some(31);
            final Option<Integer> bind = option.bind(s -> Option.some(3232))
                .bind(e -> Option.<Integer>none())
                .map(e -> e * 100);

            System.out.println(bind.isNone() ? "none" : bind.some());

            final Option<String> num = Option.some("42");
            final Option<Integer> bind1 = num.bind(s -> tryParse(s));

            System.out.println(bind1.isNone()); /* false */

            final Option<String> num2 = Option.some("42s");
            final Option<Integer> bind2 = num2.bind(s -> tryParse(s));

            System.out.println(bind2.isNone()); /* true */
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    static Option<Integer> tryParse(String s)
    {
        try
        {
            final int i = Integer.parseInt(s);
            return Option.some(i);
        }
        catch (NumberFormatException e)
        {
            return Option.none();
        }
    }
}
