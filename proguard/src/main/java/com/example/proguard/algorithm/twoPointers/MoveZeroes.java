package com.example.proguard.algorithm.twoPointers;

/**
 * 移动零问题是一个经典的双指针问题，目标是将数组中的所有零移动到数组的末尾，同时保持非零元素的相对顺序。我们可以使用双指针的方法来解决这个问题，确保在不使用额外空间的情况下高效完成操作。
 * 问题描述
 * 给定一个数组 nums，请将数组中的所有零移动到数组的末尾，同时保持非零元素的相对顺序。
 * 双指针解法
 * 思路
 * 初始化两个指针：
 * left：用于追踪当前需要放置非零元素的位置。
 * right：用于遍历数组。
 * 遍历数组：
 * 当 right 指针指向的元素非零时，将其与 left 指针指向的元素交换，并将 left 指针向右移动。
 * 无论是否交换，right 指针始终向右移动。
 * 最终结果：
 * 所有非零元素按顺序排列在数组的前面，所有零被移动到数组的末尾。
 *
 * 总结
 * 双指针法：通过两个指针 left 和 right，可以高效地将零移动到数组的末尾，同时保持非零元素的相对顺序。
 * 时间复杂度：O(n)，其中 n 是数组的长度。每个元素最多被访问两次（一次由 right 指针，一次由 left 指针）。
 * 空间复杂度：O(1)，不需要额外的空间。
 */
public class MoveZeroes {
    public void moveZeroes(int[] nums) {
        int left = 0; // 左指针用于放置非零元素
        int right = 0; // 右指针用于遍历数组

        while (right < nums.length) {
            if (nums[right] != 0) {
                // 如果右指针指向的元素非零，交换左右指针的值
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++; // 左指针向右移动
            }
            //等于零的时候左指针是不动的，等下下次right指针指向不等于零元素的时候直接交换
            right++; // 右指针始终向右移动
        }
    }

    public static void main(String[] args) {
        MoveZeroes solution = new MoveZeroes();
        int[] nums = {0, 1, 0, 3, 12};
        solution.moveZeroes(nums);

        // 打印结果
        for (int num : nums) {
            System.out.print(num + " ");
        }
    }
}
