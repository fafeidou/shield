package com.example.proguard.algorithm.stack;

import java.util.Stack;

/**
 * 解码字符串（Decode String）问题通常是指 LeetCode 上的第 394 题：给定一个经过编码的字符串，
 * 返回其解码后的字符串。编码规则通常包含嵌套的结构，例如
 * s = "3[a2[c]]" 解码为 "accaccacc"
 *
 * 解题思路
 * 我们可以使用 栈（Stack） 来处理这种嵌套结构。基本思路是：
 * 遇到数字时，记录当前的倍数。
 * 遇到 [ 时，将当前的字符串和倍数压入栈中。
 * 遇到 ] 时，弹出栈顶的字符串和倍数，将当前字符串进行解码。
 *
 * 时间复杂度分析
 * 时间复杂度： O(n)，其中 n 是输入字符串的长度。每个字符最多会被处理一次，且在解码 ] 时，重复操作的字符数总和不会超过 n。
 * 空间复杂度： O(m)，其中 m 是解码后字符串的长度。最坏情况下，栈可能存储所有的嵌套结构。
 *
 *
 * 时间复杂度分析
 * 时间复杂度： O(n)，其中 n 是输入字符串的长度。每个字符最多会被处理一次，且在解码 ] 时，重复操作的字符数总和不会超过 n。
 * 空间复杂度： O(m)，其中 m 是解码后字符串的长度。最坏情况下，栈可能存储所有的嵌套结构。
 *
 */
public class DecodeString {

    public String decodeString(String s) {
        Stack<Integer> numStack = new Stack<>();  // 存储数字（倍数）
        Stack<StringBuilder> strStack = new Stack<>();  // 存储当前字符串的构建器
        StringBuilder currentStr = new StringBuilder();  // 当前正在构建的字符串
        int num = 0;  // 当前倍数
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                num = 10 * num + Integer.parseInt(String.valueOf(c));
            } else if (c == '[') {
                // 将当前字符串和倍数压栈，并重置
                numStack.push(num);
                num = 0;
                strStack.push(currentStr);
                currentStr = new StringBuilder();
            } else if (c == ']') {
                // 弹出倍数和上一层字符串
                Integer repeatNum = numStack.pop();
                StringBuilder prevStr = strStack.pop(); //a
                StringBuilder temp = new StringBuilder();
                // 将当前解码的字符串重复指定次数后追加到上一层字符串
                for (int i = 0; i < repeatNum; i++) {
                    temp.append(currentStr);
                }
                prevStr.append(temp);
                currentStr = prevStr;
            } else {
                // 普通字符直接追加
                currentStr.append(c);
            }

        }
        return currentStr.toString();
    }


    // 示例测试
    public static void main(String[] args) {
        DecodeString solution = new DecodeString();
        System.out.println(solution.decodeString("3[a2[c]]"));  // 输出 "accaccacc"
        System.out.println(solution.decodeString("2[abc]3[cd]ef"));  // 输出 "abcabccdcdcdef"
    }
}
