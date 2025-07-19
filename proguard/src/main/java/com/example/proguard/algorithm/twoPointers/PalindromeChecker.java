package com.example.proguard.algorithm.twoPointers;

/**
 * 回文字符串判断类
 * 该程序用于判断一个字符串是否为回文字符串（Palindrome）
 * 回文字符串是指正序和倒序完全相同的字符串，如 "level"、"madam" 等
 */
public class PalindromeChecker {


    /**
     * 判断字符串是否为回文
     *
     * @param str 输入字符串
     * @return 如果字符串是回文则返回 true，否则返回 false
     */
    public static boolean isPalindrome(String str) {
        int left = 0;
        int right = str.length() - 1;

        // 只要左指针在右指针左侧，就继续比较
        while (left < right) {
            // 如果两个字符不相等，立即返回 false
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        // 所有字符都匹配，返回 true
        return true;
    }
    //时间复杂度分析
    //时间复杂度：O(n)
    //其中 n 是字符串的长度
    //我们使用双指针法，每个字符最多被访问一次，因此是线性复杂度
    //空间复杂度：O(1)
    //没有使用额外的空间，只使用了常数级的变量

    /**
     * 主方法，用于测试
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        String testStr = "level"; // 测试字符串

        // 调用判断方法并输出结果
        System.out.println("Is \"" + testStr + "\" a palindrome? " + isPalindrome(testStr));
    }
}
