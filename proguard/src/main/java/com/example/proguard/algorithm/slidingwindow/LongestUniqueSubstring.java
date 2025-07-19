package com.example.proguard.algorithm.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * 查找不含重复字符的最长子串长度
 * ✅ 时间复杂度分析
 * 时间复杂度：O(n)
 * 其中 n 是字符串的长度。
 * 每个字符最多只会被访问两次（一次加入窗口，一次移出窗口）。
 * 空间复杂度：O(min(m, n))
 * m 是字符集大小（如 ASCII 为 128），使用哈希表存储字符位置。
 */
public class LongestUniqueSubstring {
    /**
     * 返回最长无重复字符子串的长度
     *
     * @param s 输入字符串
     * @return 最长子串长度
     * 输入: "abcabcbb" → 输出: 3
     */
    public static int lengthOfLongestSubstring(String s) {
        //使用map 来记录字符和字符出现的位置
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int start = 0;
        int maxLength = 0;

        for (int end = 0; end < s.length(); end++) {
            char currentChar = s.charAt(end);

            //如果当前字符在map中存在，并且当前字符的位置大于等于start，则更新start为当前字符的位置加1
            if (charIndexMap.containsKey(currentChar) && charIndexMap.get(currentChar) >= start) {
                //移动滑动窗口的起始位置到重复字符串的下一个位置
                start = charIndexMap.get(currentChar) + 1;
            }
            //更新字符的最后出现位置
            charIndexMap.put(currentChar, end);
            //计算当前窗口长度并更新最大值
            int currentLength = end - start + 1;
            maxLength = Math.max(maxLength, currentLength);
        }

        return maxLength;
    }

    /**
     * 主方法用于测试
     */
    public static void main(String[] args) {
        String testStr = "pwwkew";
        System.out.println("最长无重复字符子串的长度: " + lengthOfLongestSubstring(testStr));
    }
}
