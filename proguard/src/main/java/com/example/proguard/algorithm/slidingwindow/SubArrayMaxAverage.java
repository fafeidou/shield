package com.example.proguard.algorithm.slidingwindow;

public class SubArrayMaxAverage {
    /**
     * 计算长度为k的连续子数组的最大平均数
     *
     * 该方法使用滑动窗口算法，通过一次遍历完成计算
     * 时间复杂度：O(n)，其中n是数组长度，只进行一次遍历
     * 空间复杂度：O(1)，不使用额外空间
     *
     * @param nums 整数数组
     * @param k    子数组长度
     * @return 最大平均数（保留五位小数）
     */
    public double findMaxAverage(int[] nums, int k) {
        // 先计算前k个元素的和作为初始窗口和
        double windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }

        // 初始化最大和为初始窗口和
        double maxSum = windowSum;

        // 滑动窗口：从第k个元素开始，每次向右移动一位
        for (int i = k; i < nums.length; i++) {
            // 新的窗口和等于旧的窗口和减去左边移出的元素，加上右边新进入的元素
            windowSum = windowSum - nums[i - k] + nums[i];

            // 更新最大和
            if (windowSum > maxSum) {
                maxSum = windowSum;
            }
        }

        // 返回最大平均数，保留五位小数
        return Math.round(maxSum / k * 100000) / 100000.0;
    }

    /**
     * 示例用法
     */
    public static void main(String[] args) {
        int[] nums = {1, 12, -5, -6, 50, 3};
        int k = 4;

        double result = new SubArrayMaxAverage().findMaxAverage(nums, k);

        // 输出结果
        System.out.println(result); // 应输出 12.75
    }

}
