package com.example.proguard.algorithm.twoPointers;

/**
 * 计算字符串中所有回文子串的数量
 * 要计算一个字符串中所有回文子串的个数，我们可以使用以下几种方法。
 * 这里我们使用最直观且高效的 中心扩展法（Expand Around Center） 来实现。
 * ✅ 时间复杂度分析
 * 时间复杂度：O(n^2)
 * 外层循环遍历每个字符作为中心，共 n 次。
 * 内层 expandAroundCenter 最多扩展 n 次。
 * 总体为 O(n^2)，适用于字符串长度在 10^3 范围内。
 * 空间复杂度：O(1)
 * 没有使用额外空间。
 */
public class PalindromeSubstrings {

    /**
     * 统计回文子串数量
     *
     * @param s 输入字符串
     * @return 回文子串的数量
     */
    public static int countSubstrings(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            // 遍历每个字符，以当前字符为中心向两边扩展
            count += expandAroundCenter(s, i, i);
            // 遍历每个字符，以当前字符和下一个字符为中心向两边扩展
            count += expandAroundCenter(s, i, i + 1);
        }
        return count;
    }

    /**
     * 从给定中心向两边扩展，统计回文子串数量
     *
     * @param s   输入字符串
     * @param left  左指针
     * @param right 右指针
     * @return 当前中心下有多少个回文子串
     */
    private static int expandAroundCenter(String s, int left, int right) {
        int count = 0;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            count++; // 找到一个回文子串
            left--;// 向左扩展
            right++;// 向右扩展
        }
        return count;
    }


    /**
     * 主方法用于测试
     */
    public static void main(String[] args) {
        String testStr = "aaa";
        System.out.println("Number of palindromic substrings of \"" + testStr + "\": " + countSubstrings(testStr));
    }
}
