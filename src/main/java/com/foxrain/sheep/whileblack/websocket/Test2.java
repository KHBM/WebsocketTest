package com.foxrain.sheep.whileblack.websocket;

import java.util.Stack;

/**
 * Created with intellij IDEA.
 * by 2021 06 2021/06/28 4:40 오후 28
 * User we at 16 40
 * To change this template use File | Settings | File Templates.
 *
 * @author foxrain
 */
public class Test2
{

    public static void main(String[] args)
    {
        //  Q. Simple Balanced Parentheses(괄호의 사용이 잘 되었는지 잘못 되었는지 판별 해 주는 프로그램)
        // ((aab)(sd) false
        // ()()aa((sa))) true
        // )))dd(e)

        System.out.println(isValidBalancedParentheses("a)))dd(e)")); //false
        System.out.println(isValidBalancedParentheses("((aab)(sd))")); //true
        System.out.println(isValidBalancedParentheses("()()aa((sa)))"));
    }

    public static boolean isValidBalancedParentheses(String s)
    {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray())
        {
            if ( c != '(' && c != ')' )
                continue;

            if ( c == '(' )
                stack.push(c);

            if ( c == ')')
            {
                if (stack.isEmpty())
                    return false;
                stack.pop();
            }
        }

        return stack.isEmpty();
    }
}