package com.example.proguard.algorithm.twoPointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {
    /**
     * 使用双指针方法找出数组中所有三个数之和为0的组合。
     *
     * @param nums 输入数组
     * @return 所有满足条件的三元组
     */
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (result == null || nums.length == 0) {
            return result;
        }
        Arrays.sort(nums);

        //i < nums.length - 2 是为了确保每次循环中，当前数 nums[i] 后面至少有两个元素，从而可以继续使用双指针寻找和为0的三元组，同时避免不必要的无效遍历。
        for (int i = 0; i < nums.length - 1; i++) {
            // 如果当前数与前一个数相同，则跳过以避免重复组合
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            // 定义双指针，left指向当前数的下一个数，right指向数组末尾
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    // 如果和为0，将当前三元组加入结果列表
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // 移动左指针并跳过重复值
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // 移动右指针并跳过重复值
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    // 继续寻找下一个可能的组合
                    left++;
                    right--;
                } else if (sum > 0) {
                    // 如果和大于0，移动右指针以减少和
                    right--;
                } else {
                    // 如果和小于0，移动左指针以增加和
                    left++;
                }
            }
        }

        return result;
    }

    //时间复杂度分析：
    //排序操作：排序的时间复杂度为 $O(n \log n)$，其中 $n$ 是数组的长度。
    //遍历与双指针：外层循环遍历数组的时间复杂度是 $O(n)$，内层双指针在最坏情况下会遍历剩余数组，因此时间复杂度为 $O(n^2)$。
    //总体时间复杂度为 $O(n^2)$，这是由于排序操作的时间复杂度低于遍历与双指针操作的时间复杂度，因此主要考虑后者。空间复杂度为 $O(1)$（不考虑结果存储空间），因为没有使用额外的空间。
    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> result = threeSum(nums);
        System.out.println(result);
    }
}
