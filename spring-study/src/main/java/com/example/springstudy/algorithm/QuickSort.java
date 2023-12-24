package com.example.springstudy.algorithm;

import java.util.Arrays;

/**
 * https://god-jiang.github.io/2019/12/16/%E4%B8%89%E5%A4%A7%E9%AB%98%E6%95%88%E6%8E%92%E5%BA%8F%E4%B9%8B%E5%BF%AB%E9%80%9F%E6%8E%92%E5%BA%8F-Java%E5%AE%9E%E7%8E%B0/
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] a = {2, 4, 6, 1, 3, 7, 9, 8, 5};
        quickSort2(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }

    //时间复杂度O(n*logn)，空间复杂度O(n*logn)
    public static void quickSort(int[] arr, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            //找出基准
            int partition = partition(arr, startIndex, endIndex);
            //分成两边递归进行
            quickSort(arr, startIndex, partition - 1);
            quickSort(arr, partition + 1, endIndex);
        }
    }

    //找基准
    private static int partition(int[] arr, int startIndex, int endIndex) {
        int pivot = arr[startIndex];
        int left = startIndex;
        int right = endIndex;
        while (left != right) {
            while (left < right && arr[right] > pivot) {
                right--;
            }
            while (left < right && arr[left] <= pivot) {
                left++;
            }
            //找到left比基准大，right比基准小，进行交换
            if (left < right) {
                swap(arr, left, right);
            }
        }
        //第一轮完成，让left和right重合的位置和基准交换，返回基准的位置
        swap(arr, startIndex, left);
        return left;
    }

    //两数交换
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void quickSort2(int[] arr, int L, int R) {
        if (L < R) {
            swap(arr, L + (int) Math.random() * (R - L + 1), R);
            int[] p = partition2(arr, L, R);
            quickSort2(arr, L, p[0] - 1);
            quickSort2(arr, p[0] + 1, R);
        }
    }

    //    这是一个处理arr[1..r]的函数
//    默认以arr[r]做划分,arr[r]->p <p ==p >p
//    返回等于区域(左边界,右边界),所以返回一个长度为2的数组res,res[0]res[1]
    private static int[] partition2(int[] arr, int L, int R) {
        int less = L;
        int more = R;
        while (L < more) {
            if (arr[L] < arr[R]) {
                swap(arr, less++, L++);
            } else if (arr[L] > arr[R]) {
                swap(arr, --more, L);
            } else {
                L++;
            }
        }
        swap(arr, more, R);
        return new int[]{less, more};
    }
}
