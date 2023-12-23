package com.example.springstudy.algorithm;

public class GetMax {
    public static void main(String[] args) {
        int[] a = {5, 2, 7, 4, 1, 3, 8, 9};
        System.out.println(getMax(a));
    }

    public static int getMax(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }


    //arr[L..R]范围上求最大值
    public static int process(int arr[], int left, int right) {
        if (left == right) { //arr[L..R]范围上只有一个数,直接返回,bad case
            return arr[left];
        }
        int mid = left + ((right - left) >> 1);
        int leftMax = process(arr, left, mid);
        int rightMax = process(arr, mid + 1, right);
        return Math.max(leftMax, rightMax);
    }

}
