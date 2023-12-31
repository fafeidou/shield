package com.example.springstudy.algorithm;

import java.util.LinkedList;
import java.util.List;

public class SpiralMatrix {
    public static void main(String[] args) {
        int a[][] = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(spiralOrder(a));
    }

    public static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans = new LinkedList<>();
        if (matrix.length == 0) {
            return ans;
        }
        int up = 0;
        int down = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;
        while (true) {
            for (int col = left; col <= right; ++col) {
                ans.add(matrix[up][col]);
            }
            if (++up > down) {
                break;
            }
            for (int row = up; row <= down; ++row) {
                ans.add(matrix[row][right]);
            }
            if (--right < left) {
                break;
            }
            for (int col = right; col >= left; --col) {
                ans.add(matrix[down][col]);
            }
            if (--down < up) {
                break;
            }
            for (int row = down; row >= up; --row) {
                ans.add(matrix[row][left]);
            }
            if (++left > right) {
                break;
            }
        }
        return ans;
    }
}
