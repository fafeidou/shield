package com.example.proguard.algorithm.twoPointers;

/**
 * 找到链表中 倒数第 k 个节点 是一个经典的链表问题。由于链表只能单向遍历，我们不能像数组那样直接通过索引访问元素。
 * 因此，我们可以使用 双指针法 高效地解决这个问题。
 *
 * ✅ 问题描述
 * 给定一个单链表的头节点 head 和一个整数 k，请返回链表中 倒数第 k 个节点 的值。
 * 如果 k 超出链表长度，返回 null 或抛出异常（根据需求）。
 * 输入: 1 -> 2 -> 3 -> 4 -> 5, k = 2
 * 输出: 4 （倒数第 2 个节点的值）
 *
 * 输入: 1 -> 2 -> 3, k = 5
 * 输出: null 或抛出异常
 *
 * ✅ 解法一：双指针法（推荐）
 * ✅ 思路
 * 使用两个指针 fast 和 slow，初始都指向头节点。
 * 先让 fast 指针向前移动 k 步。
 * 然后 fast 和 slow 同时每次移动一步。
 * 当 fast 到达链表末尾（即 fast == null）时，slow 所在的位置就是倒数第 k 个节点。
 *
 *
 */
public class FindKthFromEnd {

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode findKthFromEnd(ListNode head, int k) {

        ListNode fast = head;
        ListNode slow = head;

        // 先让 fast 走 k 步
        for (int i = 0; i < k; i++) {
            if (fast == null) {
                // k 超出链表长度
                return null;
            }
            fast = fast.next;
        }

        // 同时移动 fast 和 slow
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        // slow 指向倒数第 k 个节点
        return slow;
    }

    // 辅助方法：打印结果
    public void printResult(ListNode head, int k) {
        ListNode result = findKthFromEnd(head, k);
        if (result != null) {
            System.out.println("倒数第 " + k + " 个节点的值为: " + result.val);
        } else {
            System.out.println("k 超出链表长度");
        }
    }

    public static void main(String[] args) {
        // 构建链表 1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        FindKthFromEnd solution = new FindKthFromEnd();
        solution.printResult(head, 2); // 输出 4
    }

}
