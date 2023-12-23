package com.example.springstudy.algorithm;

import java.util.Arrays;

import static com.example.springstudy.algorithm.Utils.swap;

/**
 * 相邻两个数做比较，并坐swap
 *
 * 看： n+n-1+n-2 + ...
 * 比： n+n-1+n-2
 * swap : 比较
 * 可以写成 aN2+bn+c
 * 时间复杂度： 就是常数操作表达，不要低阶项，去掉最高项的系数，即 o(n2)
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] a = {5, 2, 7, 4, 1, 3, 8, 9};
//        int[] a = {1, 2, 3, 4, 5, 7, 8, 9};
        bubble_v3(a);
    }

    public static void bubble_v3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                //arr[j] > arr[j + 1] 表示升序、arr[j + 1] > arr[j] 表示降序
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j + 1, j);
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    public static void bubble_v2(int[] a) {
        int n = a.length - 1;
        while (true) {
            int last = 0; // 表示最后一次交换索引位置
            for (int i = 0; i < n; i++) {
                System.out.println("比较次数" + i);
                if (a[i] > a[i + 1]) {
                    swap(a, i, i + 1);
                    last = i;
                }
            }
            n = last;
            System.out.println("第轮冒泡"
                    + Arrays.toString(a));
            if (n == 0) {
                break;
            }
        }
    }

    public static void bubble(int[] a) {
        for (int j = 0; j < a.length - 1; j++) {
            // 一轮冒泡
            boolean swapped = false; // 是否发生了交换
            for (int i = 0; i < a.length - 1 - j; i++) {
                System.out.println("比较次数" + i);
                if (a[i] > a[i + 1]) {
                    swap(a, i, i + 1);
                    swapped = true;
                }
            }
            System.out.println("第" + j + "轮冒泡"
                    + Arrays.toString(a));
            if (!swapped) {
                break;
            }
        }
    }

}
