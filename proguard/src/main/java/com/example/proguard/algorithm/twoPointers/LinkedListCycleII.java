package com.example.proguard.algorithm.twoPointers;

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
