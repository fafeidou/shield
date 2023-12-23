package com.example.springstudy.algorithm;

import java.util.Arrays;

import static com.example.springstudy.algorithm.Utils.swap;

public class InsertSort {
    public static void main(String[] args) {
        int[] a = {7, 5, 19, 8, 4, 1};
//        insert(a);
        insertSort(a);
    }

    public static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        // 0 - 0 是有序的
        //0 -i 上有序
        for (int i = 1; i <= arr.length - 1; i++) {
            for (int j = i - 1; j >= 0 && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }

        System.out.println(Arrays.toString(arr));
    }

    // 修改了代码与希尔排序一致
    public static void insert(int[] a) {
        // i 代表待插入元素的索引
        for (int i = 1; i < a.length; i++) {
            int t = a[i]; // 代表待插入的元素值
            int j = i;
            System.out.println(j);
            while (j >= 1) {
                if (t < a[j - 1]) { // j-1 是上一个元素索引，如果 > t，后移
                    a[j] = a[j - 1];
                    j--;
                } else { // 如果 j-1 已经 <= t, 则 j 就是插入位置
                    break;
                }
            }
            a[j] = t;
            System.out.println(Arrays.toString(a) + " " + j);
        }
    }
}
