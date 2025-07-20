package com.example.proguard.algorithm.twoPointers;

/**
 * ✅ 题目描述
 * 将两个升序链表 list1 和 list2 合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 *
 * ✅ 解法：双指针 + 虚拟头节点
 * 思路
 * 使用两个指针 p1 和 p2 分别遍历 list1 和 list2。
 * 创建一个虚拟头节点 dummy，并用指针 current 来构建新链表。
 * 每次比较 p1.val 和 p2.val，将较小的节点连接到 current.next，并移动相应的指针。
 * 当其中一个链表遍历完后，将另一个链表剩余部分直接接在 current.next。
 * 返回 dummy.next 作为合并后的链表头节点。
 *
 ✅ 时间复杂度分析
 时间复杂度：O(m + n)，其中 m 和 n 分别是两个链表的长度。我们最多遍历每个链表一次。
 空间复杂度：O(1)，我们只使用了常数级别的额外空间（虚拟头节点和几个指针）。
 */
public class MergeTwoSortedLists {

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

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        //虚拟头节点
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        if (list1 != null) {
            current.next = list1;
        }
        if (list2 != null) {
            current.next = list2;
        }
        return dummy.next;
    }

    public void printList(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val + " -> ");
            curr = curr.next;
        }
        System.out.println("null");
    }

    public static void main(String[] args) {
        // 创建链表 1: 1 -> 2 -> 4
        ListNode list1 = new ListNode(1, new ListNode(2, new ListNode(4)));
        // 创建链表 2: 1 -> 3 -> 4
        ListNode list2 = new ListNode(1, new ListNode(3, new ListNode(4)));

        MergeTwoSortedLists solution = new MergeTwoSortedLists();
        ListNode mergedList = solution.mergeTwoLists(list1, list2);

        System.out.println("Merged List:");
        solution.printList(mergedList);
    }
}
