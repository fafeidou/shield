package com.example.springstudy.algorithm;

import java.util.Arrays;

import static com.example.springstudy.algorithm.Utils.swap;

public class SelectionSort {

    public static void main(String[] args) {
        int[] a = {5, 2, 7, 4, 1, 3, 8, 9};
        selectionSort(a);
    }

    public static void selectionSort(int arr[]) {
        if (arr == null || arr.length < 2) {
            return;
        }
        for (int i = 0; i < arr.length; i++) { // i - n-1
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) { //i - n -1 上找到最小下标
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            swap(arr, minIndex, i);
        }
        System.out.println(Arrays.toString(arr));
    }
}
