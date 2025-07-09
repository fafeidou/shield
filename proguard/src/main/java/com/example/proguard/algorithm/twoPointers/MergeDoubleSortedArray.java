package com.example.proguard.algorithm.twoPointers;

import java.util.Arrays;

/**
 * 合并两个有序数组到第一个数组中
 */
public class MergeDoubleSortedArray {
    /**
     * 合并两个有序数组到第一个数组中
     *
     * 该方法使用双指针法从后向前合并，避免覆盖有效数据
     * 时间复杂度：O(m + n)，每个元素只访问一次
     * 空间复杂度：O(1)，原地合并，不使用额外空间
     *
     * @param nums1 第一个有序数组，有足够的空间容纳合并后的所有元素
     * @param m     nums1中有效元素的个数
     * @param nums2 第二个有序数组
     * @param n     nums2中有效元素的个数
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // i 指向 nums1 的有效末尾
        int i = m - 1;
        // j 指向 nums2 的末尾
        int j = n - 1;
        // k 指向 nums1 的最终末尾位置
        int k = m + n - 1;

        // 当两个数组都还有元素时，从后向前比较合并
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }

        // 如果 nums2 还有剩余元素，将其复制到 nums1 中
        // (nums1 剩余元素无需处理，因为它们已经在正确的位置)
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
    }

    /**
     * 示例用法
     */
    public static void main(String[] args) {
        int[] nums1 = {1, 3, 5, 0, 0, 0}; // 第一个有序数组，后面的0是预留空间
        int m = 3; // nums1中有效元素的个数
        int[] nums2 = {2, 4, 6}; // 第二个有序数组
        int n = 3; // nums2中有效元素的个数

        new MergeDoubleSortedArray().merge(nums1, m, nums2, n);

        // 输出合并后的数组
        System.out.println(Arrays.toString(nums1)); // 应输出 [1, 2, 3, 4, 5, 6]
    }

}
