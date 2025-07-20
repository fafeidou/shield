package com.example.proguard.algorithm.stack;

import java.util.Stack;

/**
 * 判断一个字符串是否是有效的括号是一个经典的栈应用问题。目标是判断括号是否以正确的顺序闭合。
 * ✅ 问题描述
 * 给定一个只包含字符 '(', ')', '{', '}', '[', ']' 的字符串 s，判断该字符串是否 有效：
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 每个右括号都有一个对应的左括号。
 * 示例：
 * 输入: "()"
 * 输出: true
 *
 * 输入: "()[]{}"
 * 输出: true
 *
 * 输入: "(]"
 * 输出: false
 *
 * 输入: "([)]"
 * 输出: false
 *
 * 输入: "{[]}"
 * 输出: true
 *
 * ✅ 解法：使用栈（推荐）
 * ✅ 思路
 * 遇到左括号（(, {, [）时，压入栈。
 * 遇到右括号（), }, ]）时：
 * 如果栈为空，说明没有对应的左括号，返回 false。
 * 否则，弹出栈顶元素，判断是否匹配：
 * ) 匹配 (
 * } 匹配 {
 * ] 匹配 [
 * 如果不匹配，返回 false。
 * 遍历完成后，如果栈为空，说明所有括号都匹配成功，返回 true；否则返回 false。
 *
 * ✅ 时间复杂度分析
 * 时间复杂度：O(n)，其中 n 是字符串长度。每个字符最多入栈和出栈一次。
 * 空间复杂度：O(n)，栈的大小取决于输入字符串中括号的数量。
 */
public class ValidParentheses {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                char top = stack.pop();
                if ((c == ')' && top != '(') ||
                        (c == '}' && top != '{') ||
                        (c == ']' && top != '[')) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        ValidParentheses solution = new ValidParentheses();

        System.out.println(solution.isValid("()"));        // true
        System.out.println(solution.isValid("()[]{}"));    // true
        System.out.println(solution.isValid("(]"));        // false
        System.out.println(solution.isValid("([)]"));      // false
        System.out.println(solution.isValid("{[]}"));      // true
    }

}
