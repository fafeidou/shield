package com.example.proguard.algorithm.slidingwindow;

/**
 * 替换后的最长重复字符子串
 *
 * 📌 问题描述（Longest Repeating Character Replacement）
 * 给定一个仅由大写英文字母组成的字符串 s 和一个整数 k，你可以最多替换 k 次字符（替换为任意字母），目标是使得连续的相同字符子串尽可能长。
 * 要求：返回在最多替换 k 次后，最长的连续相同字符子串的最大长度。
 *
 * ✅ 示例
 * 输入: s = "ABAB", k = 2
 * 输出: 4
 * 解释: 将两个 'B' 替换为 'A'，得到 "AAAA"
 *
 * 输入: s = "AABABBA", k = 1
 * 输出: 4
 * 解释: 把中间的 'A' 替换成 'B'，得到 "AABBBBA"
 *
 * ✅ 解法：滑动窗口 + 贪心
 * 我们使用滑动窗口来维护一个子串，窗口内我们允许最多 k 次替换，使得窗口内字符尽可能统一。
 * 核心思想：
 * 在窗口内保留最多的某个字符（设为 maxCount），其余字符用 k 次替换来替换。
 * 如果 窗口长度 - maxCount <= k，说明可以替换，窗口合法。
 * 否则，移动左指针。
 */
public class LongestRepeatingCharacterReplacement {
    public int characterReplacement(String s, int k) {
        int[] count = new int[26]; // 用于记录窗口中每个字符的频率
        int maxCount = 0; // 记录窗口中最多字符的频率
        int maxLength = 0; // 记录最终的最长子串长度
        int left = 0; // 滑动窗口的左指针

        for (int right = 0; right < s.length(); right++) {
            count[s.charAt(right) - 'A']++;
            maxCount = Math.max(maxCount, count[s.charAt(right) - 'A']);

            // 如果当前窗口的大小减去最多字符的频率大于 k，
            // 说明需要替换的字符数超过 k，需要移动左指针
            if (right - left + 1 - maxCount > k) {
                count[s.charAt(left) - 'A']--;
                left++;
            }
            // 更新最长子串的长度
            maxLength = Math.max(maxCount, right - left + 1);
        }

        return maxLength;
    }

    public static void main(String[] args) {
        LongestRepeatingCharacterReplacement solution = new LongestRepeatingCharacterReplacement();
        String s = "AABABBA";
        int k = 1;
        int result = solution.characterReplacement(s, k);
        System.out.println("The longest repeating character replacement is: " + result);
    }
}
