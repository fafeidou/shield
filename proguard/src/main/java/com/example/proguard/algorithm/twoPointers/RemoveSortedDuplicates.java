package com.example.proguard.algorithm.twoPointers;

/**
 * 以下是使用 双指针法 删除有序数组中重复项的 Java 实现
 */
public class RemoveSortedDuplicates {

    /**
     * 使用双指针法删除有序数组中的重复项。
     * 该方法修改原数组，将不重复的元素移动到前面，并返回去重后的长度。
     * 时间复杂度O(n)，只遍历一次数组
     * 空间复杂度O(1)，没有使用额外空
     * @param nums 已排序的整型数组
     * @return 去重后的有效长度
     */
    public static int removeDuplicates(int[] nums) {
        // 如果数组为空或长度为0，直接返回0
        if (nums.length == 0) return 0;

        // 定义快慢指针
        int i = 0; // 慢指针，指向不重复部分的最后一个位置
        // 快指针 j 从索引1开始遍历数组
        for (int j = 1; j < nums.length; j++) {
            // 当前元素与慢指针指向的元素不同，说明不是重复项
            if (nums[j] != nums[i]) {
                i++; // 慢指针后移一位
                nums[i] = nums[j]; // 将当前非重复元素移到前面
            }
            // 如果相同，快指针继续向后移动，慢指针保持不动，相当于跳过重复项
        }

        // 返回去重后的有效长度（i+1是因为数组下标从0开始）
        return i + 1;
    }

    public static void main(String[] args) {
        int[] nums = {1, 1, 2, 2, 3};
        int length = removeDuplicates(nums);

        // 打印去重后的数组内容
        System.out.println("去重后的数组长度为：" + length);
        System.out.print("去重后的数组内容为：");
        for (int k = 0; k < length; k++) {
            System.out.print(nums[k] + " ");
        }
        // 输出：
        // 去重后的数组长度为：3
        // 去重后的数组内容为：1 2 3
    }
}

