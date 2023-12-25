package com.example.springstudy.algorithm;

import java.util.Arrays;

import static com.example.springstudy.algorithm.Utils.swap;

public class HeapSort {
    public static void main(String[] args) {
        int[] a = {5, 2, 7, 4, 1, 3, 8, 9};
        heapSort(a);

    }

    //时间复杂度 O(NlogN) 额外空间复杂度O(1)
    public static void heapSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length; i++) { //O(n)
            heapInsert(arr, i); //o(logn)
        }

        int heapSize = arr.length;
        //第一个位置上和最后一个位置上交换，让最后一个断开，heapSize--
        //做堆化
        swap(arr, 0, --heapSize);
        while (heapSize > 0) {
            heapify(arr, 0, heapSize); //O(logn)
            swap(arr, 0, --heapSize); //O(1)
            System.out.println(Arrays.toString(arr));
        }
    }

    // 某个数在index位置，是否能够往下移动，堆化
    private static void heapify(int[] arr, int index, int heapSize) {
        int left = index * 2 + 1; //左孩子的下标
        while (left < heapSize) {
            int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left; //左孩子和右孩子比较
            //父节点和两个子节点比较去最大
            largest = arr[index] > arr[largest] ? index : largest;
            if (largest == index) {
                break;
            }
            swap(arr, largest, index);
            index = largest;
            left = index * 2 + 1;
        }
    }

    private static void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }
}
