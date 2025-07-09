package com.example.proguard.algorithm.dynamicprogramming;

/**
 * 最大子数组和
 * 算法说明：
 * Kadane 算法：通过一次遍历数组，动态维护两个变量：
 *      currentMax 表示以当前元素结尾的子数组的最大和。
 *      globalMax 表示整个数组中连续子数组的最大和。
 *
 * 每次迭代中，判断是否以当前元素重新开始一个新的子数组，还是继续扩展之前的子数组。
 *
 * 时间复杂度：
 * O(n)：仅需一次遍历数组即可找到最大值。
 * 空间复杂度：
 * O(1)：只使用了常数级别的额外空间。
 */
public class MaxSubArraySum {
    /**
     * 查找连续子数组的最大和。
     *
     * @param nums 输入整数数组（可以包含负数）。
     * @return 连续子数组的最大和。
     */
    public int maxSubArray(int[] nums) {
        // 初始化当前最大值和全局最大值为数组第一个元素
        int currentMax = nums[0];
        int globalMax = nums[0];

        // 从第二个元素开始遍历数组
        for (int i = 1; i < nums.length; i++) {
            // 更新当前最大值：要么以当前元素重新开始一个新的子数组，要么扩展之前的子数组
            currentMax = Math.max(nums[i], currentMax + nums[i]);
            // 如果当前最大值大于全局最大值，则更新全局最大值
            if (currentMax > globalMax) {
                globalMax = currentMax;
            }
        }

        return globalMax;
    }

    /**
     * 主方法，用于测试 maxSubArray 函数。
     *
     * @param args 命令行参数（此处未使用）。
     */
    public static void main(String[] args) {
        MaxSubArraySum solution = new MaxSubArraySum();

        // 示例输入数组
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};

        // 调用 maxSubArray 方法并输出结果
        int result = solution.maxSubArray(nums);
        System.out.println("连续子数组的最大和为: " + result); // 预期输出: 6
    }
}
