package com.example.proguard.algorithm.bit;


public class SumTwoIntegersUsingBit {

    /**
     * 使用位运算计算两整数之和
     *
     * @param a 第一个整数
     * @param b 第二个整数
     * @return 两个整数的和
     *
     * 时间复杂度：O(1)
     * 空间复杂度：O(1)
     */
    public static int sum(int a, int b) {
        while (b != 0) {
            int addTemp = a ^ b; // 异或计算和
            int carry = (a & b) << 1; // 计算进位,进位左移，准备下一轮加法
            a = addTemp;
            b = carry;
        }
        return a;
    }

    //方法功能：
    //sum 方法使用位运算来计算两个整数的和。
    //a ^ b 表示不带进位的加法结果。
    //a & b 表示计算进位的部分，然后通过左移一位 (carry << 1) 将进位加入下一轮的加法中。
    //循环直到没有进位为止。
    //时间复杂度：O(1)，因为位运算和循环次数是常数级别（最多 32 次或 64 次，取决于整数的位数）。
    //空间复杂度：O(1)，因为没有使用额外的空间，只使用了常数数量的变量。

    public static void main(String[] args) {
        int a = -5;
        int b = -10;
        System.out.println("两整数之和为: " + sum(a, b));
    }
}
