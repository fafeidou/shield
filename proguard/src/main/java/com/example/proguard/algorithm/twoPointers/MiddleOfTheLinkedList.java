package com.example.proguard.algorithm.twoPointers;

/**
 * 找到链表的中间位置是一个经典的链表问题，通常使用 快慢指针法（Floyd 判圈算法） 来高效解决。
 * 这种方法不需要额外空间，只需要一次遍历即可找到中间节点。
 * ✅ 问题描述
 * 给定一个单链表的头节点 head，请返回链表的中间节点。
 * 如果链表长度为偶数，则返回第二个中间节点（即偏右的中间节点）。
 *
 * ✅ 示例：
 * 输入: 1 -> 2 -> 3 -> 4 -> 5
 * 输出: 3
 *
 * 输入: 1 -> 2 -> 3 -> 4
 * 输出: 3 （即节点 3）
 *
 * ✅ 解法：快慢指针法（推荐）
 * ✅ 思路
 * 使用两个指针 slow 和 fast。
 * slow 每次走一步，fast 每次走两步。
 * 当 fast 到达链表末尾时，slow 刚好在链表的中间位置。
 *
 * ✅ 时间复杂度分析
 * 时间复杂度：O(n)，只需要一次遍历。
 * 空间复杂度：O(1)，只使用了两个指针。
 */
public class MiddleOfTheLinkedList {
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

    public ListNode middleNode(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow; // slow 最终指向中间节点
    }

    //✅ 拓展：如何找到第一个中间节点？
    //如果你希望在偶数长度链表中返回第一个中间节点（偏左），可以在快慢指针中稍作调整：
    //while (fast.next != null && fast.next.next != null) {
    //    slow = slow.next;
    //    fast = fast.next.next;
    //}
    //此时：
    //对于 1 -> 2 -> 3 -> 4，返回 2。

    // 辅助方法：打印中间节点值
    public void printMiddle(ListNode head) {
        ListNode middle = middleNode(head);
        System.out.println("中间节点的值为: " + middle.val);
    }

    public static void main(String[] args) {
        // 构建链表 1 -> 2 -> 3 -> 4 -> 5
        ListNode head = new ListNode(1,
                new ListNode(2,
                        new ListNode(3,
                                new ListNode(4,
                                        new ListNode(5)))));

        MiddleOfTheLinkedList solution = new MiddleOfTheLinkedList();
        solution.printMiddle(head); // 输出 3
    }

}
