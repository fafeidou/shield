package com.example.proguard.algorithm.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * 查找 s 中涵盖 t 所有字符的最短子串
 * 要解决最小覆盖子串（Minimum Window Substring）问题，
 * 我们可以使用 滑动窗口（Sliding Window） 算法。
 * 这是一个经典的双指针问题，用于在字符串 s 中找到包含另一个字符串 t
 * 所有字符的最短子串。
 *
 ✅ 时间复杂度分析
 时间复杂度：O(n + m)，其中：
 n 是字符串 s 的长度
 m 是字符串 t 的长度
 每个字符最多被访问两次（左指针和右指针各一次）
 空间复杂度：O(k)，其中 k 是目标字符串 t 中不同字符的数量，用于存储哈希表。

 ✅ 为什么时间复杂度是 O(n) 而不是 O(n^2)？

 for (int right = 0; right < n; right++) {
 // ... 做一些操作

 while (条件满足) {
 // ... 移动左指针
 left++;
 }
 }

 虽然有两层循环，但关键是：
 每个元素最多被左右指针各访问一次。
 举个例子：
 right 指针从 0 到 n-1，总共移动 n 次。
 left 指针在整个过程中只向前移动（不会回退），最多也移动 n 次。
 所以，整个循环体中，所有操作加起来是 O(n) 的

 */
public class MinimumWindowSubstring {
    /**
     * 返回 s 中涵盖 t 所有字符的最短子串
     *
     * @param s 主字符串
     * @param t 目标字符串
     * @return 最短覆盖子串
     */
    public static String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }

        // 统计 t 中每个字符的频率
        Map<Character, Integer> targetMap = new HashMap<>();
        for (char c : t.toCharArray()) {
            targetMap.put(c, targetMap.getOrDefault(c, 0) + 1);
        }

        // 滑动窗口需要用到的变量
        Map<Character, Integer> windowMap = new HashMap<>();
        int have = 0; // 当前窗口中满足 targetMap 的字符数量
        int need = targetMap.size(); // 需要满足的字符种类数

        int left = 0;
        int minLength = Integer.MAX_VALUE;
        int minLeft = 0;

        for (int right = 0; right < s.length(); right++) {
            char currentChar = s.charAt(right);
            //如果当前字符串再targetMap中，则将当前字符加入到窗口中
            if (targetMap.containsKey(currentChar)) {
                windowMap.put(currentChar, windowMap.getOrDefault(currentChar, 0) + 1);
                // 如果当前字符的数量刚好满足 targetMap 的要求，have 增加
                if (windowMap.get(currentChar).equals(targetMap.get(currentChar))) {
                    have++;
                }
            }

            // 当窗口满足条件时，尝试收缩窗口
            while (have == need) {
                //更新最小窗口
                if (right - left + 1 < minLength) {
                    minLength = right - left + 1;
                    minLeft = left;
                }

                char leftChar = s.charAt(left);
                // 尝试移动左指针
                if (targetMap.containsKey(leftChar)) {
                    if (windowMap.get(leftChar) > targetMap.get(leftChar)) {
                        windowMap.put(leftChar, windowMap.get(leftChar) - 1);
                    } else {
                        have--;
                        windowMap.put(leftChar, windowMap.get(leftChar) - 1);
                    }
                }
                left++;
            }

        }


        return minLength == Integer.MAX_VALUE ? "" :
                s.substring(minLeft, minLeft + minLength);
    }


    /**
     * 主方法用于测试
     * 输入: s = "ADOBECODEBANC", t = "ABC"
     * 输出: "BANC"
     */
    public static void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";
        System.out.println("最小覆盖子串: " + minWindow(s, t));
    }
}
