package com.example.proguard.algorithm.twoPointers;

//为了找到旋转数组中的最小值，我们可以使用二分查找算法的一个变体。
//旋转数组是指一个原本有序的数组经过了一定次数的旋转操作（例如将数组 [0,1,2,4,5,6,7] 变成 [4,5,6,7,0,1,2]）。
//这种问题通常可以通过比较中间元素与边界元素来缩小搜索范围。
public class FindMinimumInRotatedArray {
    public static int findMin(int[] nums) {
        int left = 0, right = nums.length - 1;

        // 如果数组未被旋转（即数组是排序的）
        if (nums[left] < nums[right]) {
            return nums[left];
        }

        while (left < right) {
            //(left + right) / 2： 等价，防止大数时溢出
            int mid = left + (right - left) / 2;

            // 如果中间元素大于右边界元素，则最小值一定在右半部分
            if (nums[mid] > nums[right]) {
                left = mid + 1;
            } else {
                // 否则最小值在左半部分（包括mid）
                right = mid;
            }
        }

        // 当循环结束时，left == right，此时指向的就是最小值
        return nums[left];
    }
    //说明：
    //初始判断：如果数组未被旋转（即第一个元素小于最后一个元素），那么最小值就是第一个元素。
    //二分查找逻辑：
    //如果中间元素大于右边界元素，则最小值一定在右半部分。
    //否则最小值在左半部分（包括中间元素）。
    //循环结束条件：当 left == right 时，此时指向的就是最小值。
    //这段代码的时间复杂度为 $O(\log n)$，空间复杂度为 $O(1)$。适用于大多数旋转数组的最小值查找场景。
    public static void main(String[] args) {
        int[] rotatedArray = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("The minimum value in the rotated array is: " + findMin(rotatedArray));
    }
}

