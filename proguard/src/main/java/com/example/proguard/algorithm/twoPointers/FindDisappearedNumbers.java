package com.example.proguard.algorithm.twoPointers;

import java.util.ArrayList;
import java.util.List;

/**
 * 思路
 * 数组中元素的范围是 1 到 n，可以利用数组本身作为哈希表进行标记。
 * 遍历数组，将当前元素对应的索引位置的值标记为负数（表示该数字出现过）。
 * 最后遍历数组，索引 + 1 对应的值仍为正数的，说明该数字缺失。
 *
 * 时间复杂度
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)（结果列表不计入空间）
 */
public class FindDisappearedNumbers {

    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int index = Math.abs(nums[i]) - 1; // 转换为 0-based 索引
            //这里标记为负数，后面还是会用求绝对值的方式计算
            if (nums[index] > 0) {
                nums[index] = -nums[index]; // 标记为已出现
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                result.add(i + 1); // 没有被标记的数字
            }
        }

        return result;
    }
    public static void main(String[] args) {
        FindDisappearedNumbers solution = new FindDisappearedNumbers();
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
        List<Integer> missingNumbers = solution.findDisappearedNumbers(nums);
        System.out.println("Missing numbers: " + missingNumbers);
    }
}
