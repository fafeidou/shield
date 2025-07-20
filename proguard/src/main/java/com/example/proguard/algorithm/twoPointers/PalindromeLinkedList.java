package com.example.proguard.algorithm.twoPointers;

import java.util.ArrayList;
import java.util.List;

/**
 * 判断一个链表是否是回文链表，是一个经典的链表问题。
 * 目标是判断链表中的节点值是否构成回文序列（即正着读和反着读都一样）。
 *
 * ✅ 问题描述
 * 给定一个单链表的头节点 head，请判断该链表是否为回文链表。
 * 示例：
 *
 * 输入: 1 -> 2 -> 3 -> 2 -> 1
 * 输出: true
 *
 * 输入: 1 -> 2 -> 3 -> 4 -> 5
 * 输出: false
 *
 * ✅ 解法一：快慢指针 + 反转链表（原地修改，空间 O(1)）
 * ✅ 思路
 * 使用快慢指针 找到链表的中点。
 * 从中间节点开始反转后半部分链表。
 * 比较前半部分和反转后的后半部分是否相同。
 * （可选）恢复链表结构。
 *
 * 快慢指针 + 反转链表
 * 时间复杂度 O(n)
 *
 */
public class PalindromeLinkedList {

    static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        // 1. 使用快慢指针找到中点
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // 2. 反转后半部分链表
        ListNode prev = null;
        ListNode curr = slow;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        ListNode left = head;
        ListNode right = prev;

        while (right != null) {
            if (right.val != left.val) {
                return false;
            }
            right = right.next;
            left = left.next;
        }

        return true;

    }

    /**
     * ✅ 解法二：使用数组 + 双指针（空间 O(n)，简单易懂）
     * ✅ 思路
     * 遍历链表，将节点值存入一个数组。
     * 使用双指针从数组两端向中间比较，判断是否为回文。
     * 时间复杂度O(n)
     * 空间复杂度O(n)
     */
    public boolean isPalindrome2(ListNode head) {
        List<Integer> list = new ArrayList<>();
        ListNode curr = head;

        while (curr != null) {
            list.add(curr.val);
            curr = curr.next;
        }

        int left = 0;
        int right = list.size() - 1;

        while (left < right) {
            if (!list.get(left).equals(list.get(right))) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }


    // 辅助方法：打印结果
    public void printResult(ListNode head) {
        boolean result = isPalindrome(head);
        System.out.println(result ? "是回文链表" : "不是回文链表");
    }

    public static void main(String[] args) {
        // 示例：1 -> 2 -> 3 -> 2 -> 1
        ListNode head = new ListNode(1,
                new ListNode(2,
                        new ListNode(3,
                                new ListNode(2,
                                        new ListNode(1)))));

        PalindromeLinkedList solution = new PalindromeLinkedList();
        solution.printResult(head);
    }
}
