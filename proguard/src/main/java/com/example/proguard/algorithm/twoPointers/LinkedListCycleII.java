package com.example.proguard.algorithm.twoPointers;

/**
 * ✅ 题目描述
 * 给定一个链表，判断链表中是否存在环，如果存在环，则返回 环的入口节点；否则返回 null。
 *
 * ✅ 解题思路（快慢指针）
 * 1. 判断是否有环
 * 使用两个指针 slow 和 fast，初始都指向头节点。
 * slow 每次走一步，fast 每次走两步。
 * 如果链表中存在环，两个指针最终会相遇。
 * 如果 fast 或 fast.next 为 null，说明链表无环。
 * 2. 找到环的入口节点
 * 当快慢指针相遇后，将 slow 重置为头节点。
 * slow 和 fast 每次都走一步，再次相遇的位置就是 环的入口节点。
 *
 * ✅ 时间复杂度分析
 * 时间复杂度：O(n)，每个节点最多被访问两次。
 * 空间复杂度：O(1)，只使用了常数级别的额外空间
 */
public class LinkedListCycleII {
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

    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;

        // 第一步：判断是否有环
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                break; // 相遇，存在环
            }
        }

        // 如果 fast 或 fast.next 为 null，说明无环
        if (fast == null || fast.next == null) {
            return null;
        }

        // 第二步：找到环的入口
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow; // 返回环的入口节点
    }

    // 辅助方法：打印链表
    public void printCycleInfo(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val + " -> ");
            curr = curr.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // 构建一个带环的链表：3 -> 2 -> 0 -> -4 -> 2（环从2开始）
        ListNode head = new ListNode(3);
        head.next = new ListNode(2);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(-4);
        head.next.next.next.next = head.next; // 环从 2 开始

        LinkedListCycleII solution = new LinkedListCycleII();
        solution.printCycleInfo(solution.detectCycle(head));
    }
}
