package com.example.proguard.algorithm.linkedlist;

/**
 * 反转链表
 */
public class ReverseNodeList {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        // 用于构建链表的辅助方法
        public static ListNode buildList(int[] values) {
            if (values == null || values.length == 0) return null;
            ListNode head = new ListNode(values[0]);
            ListNode current = head;
            for (int i = 1; i < values.length; i++) {
                current.next = new ListNode(values[i]);
                current = current.next;
            }
            return head;
        }

        // 用于比较链表是否相等的辅助方法
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            ListNode current = this;
            while (current != null) {
                sb.append(current.val).append(" -> ");
                current = current.next;
            }
            sb.append("null");
            return sb.toString();
        }
    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            ListNode nextTemp = current.next; // 保存下一个节点
            current.next = prev;              // 反转当前节点
            prev = current;                   // 移动 prev 到当前节点
            current = nextTemp;               // 移动 current 到下一个节点
        }

        return prev; // 新的头节点
    }

    public ListNode reverseListRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head; // 最后一个节点作为新的头节点
        }

        ListNode newHead = reverseListRecursive(head.next); // 递归到末尾
        head.next.next = head; // 反转当前层的指向
        head.next = null;      // 防止形成环

        return newHead; // 返回新的头节点
    }


    public static void main(String[] args) {
        ReverseNodeList reverseLinkedList = new ReverseNodeList();
        ListNode head = ListNode.buildList(new int[]{1, 2, 3, 4, 5});
        ListNode reversed = reverseLinkedList.reverseList(head);
        System.out.println(reversed.toString());
    }

}
