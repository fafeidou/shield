package com.example.proguard.algorithm.twoPointers;

/**
 * 删除有序链表中的重复元素是一个经典的链表操作问题，
 * 目标是 保留每个元素的第一次出现，删除后续重复的节点。
 * 由于链表是有序的，所以重复的元素一定是连续的。
 * ✅ 题目描述
 * 给定一个 有序链表，删除所有重复的元素，使得每个元素 只保留一次。
 * 输入: 1 -> 1 -> 2 -> 3 -> 3 -> null
 * 输出: 1 -> 2 -> 3 -> null
 *
 * ✅ 解法一：双指针法（推荐）
 * 思路
 * 使用一个指针 current 遍历链表。
 * 如果当前节点的值与下一个节点的值相同，则跳过下一个节点（即 current.next = current.next.next）。
 * 如果不同，则 current 向后移动一步。
 *
 * ✅ 时间复杂度分析
 * 时间复杂度：O(n)，其中 n 是链表的长度。我们只遍历链表一次。
 * 空间复杂度：O(1)，只使用了常数级别的额外空间。
 *
 */
public class RemoveDuplicatesFromSortedList {

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

    public ListNode deleteDuplicates(ListNode head) {
        ListNode current = head;
        while (current != null && current.next != null) {
            if (current.val == current.next.val) {
                //删除重复节点，不移动当前指针
                current.next = current.next.next;
            } else {
                //不重复，移动当前指针
                current = current.next;
            }
        }
        return head;
    }

    // 辅助方法：打印链表
    public void printList(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val + " -> ");
            curr = curr.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // 构建链表 1 -> 1 -> 2 -> 3 -> 3
        ListNode head = new ListNode(1,
                new ListNode(1,
                        new ListNode(2,
                                new ListNode(3,
                                        new ListNode(3)))));

        RemoveDuplicatesFromSortedList solution = new RemoveDuplicatesFromSortedList();
        ListNode newHead = solution.deleteDuplicates(head);

        System.out.println("After removing duplicates:");
        solution.printList(newHead);
    }
}
