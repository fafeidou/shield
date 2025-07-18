package com.example.proguard.algorithm.twoPointers;

public class SearchInRotatedSortedArray {
    //🔍 问题描述
    //给定一个 旋转排序数组（例如 [4,5,6,7,0,1,2]），以及一个目标值 target，请判断该数组中是否包含目标值。数组中 没有重复元素。
    //
    //✅ 解题思路
    //使用二分查找：
    //通过 mid = left + (right - left) / 2 找到中间位置。
    //判断哪一部分是有序的（左半部分或右半部分）。
    //如果目标值在有序的那一部分范围内，则缩小搜索范围到那一侧；否则继续在另一侧查找。
    //终止条件：
    //当 nums[mid] == target 时，找到目标值。
    //当 left > right 时，说明未找到目标值。
    public static boolean search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return true; // 找到目标值
            }

            // 左半部分有序
            if (nums[left] <= nums[mid]) {
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1; // 目标值在左半部分
                } else {
                    left = mid + 1; // 目标值在右半部分
                }
            }
            // 右半部分有序
            else {
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1; // 目标值在右半部分
                } else {
                    right = mid - 1; // 目标值在左半部分
                }
            }
        }

        return false; // 未找到目标值
    }

    public static void main(String[] args) {
        int[] rotatedArray = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;

        boolean found = search(rotatedArray, target);
        if (found) {
            System.out.println("目标值 " + target + " 在旋转数组中。");
        } else {
            System.out.println("目标值 " + target + " 不在旋转数组中。");
        }
    }
}
