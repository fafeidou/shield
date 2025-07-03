package com.example.proguard.algorithm.linkedlist;

import java.util.HashSet;

public class CycleNodeList {
    static class ListNode {
        int val;
        ListNode next;
        boolean visited; // 新增标记字段

        ListNode(int val) {
            this.val = val;
            this.next = null;
            this.visited = false;
        }
    }


    /**
     * 判断一个链表是否为循环链表（也称为环形链表），
     * 可以使用经典的 Floyd 判圈算法，又称 快慢指针法。
     * 这种方法通过两个以不同速度移动的指针来检测链表中是否存在环。
     *
     * 实现思路：
     * 使用两个指针 slow 和 fast，初始时都指向链表的头节点。
     * 每次 slow 移动一步 (slow = slow.next)，而 fast 移动两步 (fast = fast.next.next)。
     * 如果链表中存在环，这两个指针最终会相遇（即 slow == fast）。
     * 如果 fast 或 fast.next 为 null，说明链表无环，遍历结束。
     *
     * 时间和空间复杂度：
     * 时间复杂度: O(n)，在最坏的情况下，快慢指针会在链表中绕环两次。
     * 空间复杂度: O(1)，仅使用了两个指针的空间。
     *
     * 以下是实现代码：
     */
    public boolean hasCycle(ListNode head) {
        // 快慢指针初始化
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            // 慢指针每次走一步
            slow = slow.next;
            // 快指针每次走两步
            fast = fast.next.next;

            // 如果相遇，说明有环
            if (slow == fast) {
                return true;
            }
        }

        // 遍历结束且未相遇，说明无环
        return false;
    }

    /**
     * 哈希表法
     *
     * 实现思路：
     * 遍历链表时，将每个节点存储在哈希表中。
     * 如果当前节点已经存在于哈希表中，则说明链表存在环。
     * 否则继续遍历直到 null。
     *
     * 时间和空间复杂度：
     * 时间复杂度: O(n)，需要遍历整个链表。
     * 空间复杂度: O(n)，用于存储已访问的节点。
     */
    public boolean hasCycle2(ListNode head) {
        HashSet<ListNode> seen = new HashSet<>();

        while (head != null) {
            if (seen.contains(head)) {
                return true; // 已经访问过该节点，存在环
            }
            seen.add(head);
            head = head.next;
        }

        return false; // 遍历完成且未发现环
    }


    /**
     * 标记法（修改原始数据结构）
     *
     * 实现思路：
     * 在节点类中增加一个布尔字段 visited 来标记是否访问过该节点。
     * 遍历时，如果当前节点已经被标记为已访问，则说明存在环。
     * 否则标记为已访问并继续遍历。
     *
     * 时间和空间复杂度：
     * 时间复杂度: O(n)
     * 空间复杂度: O(n)，因为新增了一个布尔字段用于标记。
     */
    public boolean hasCycle3(ListNode head) {
        while (head != null) {
            if (head.visited) {
                return true; // 节点已被访问过，存在环
            }
            head.visited = true; // 标记为已访问
            head = head.next;
        }

        return false; // 遍历完成且无环
    }


    public static void main(String[] args) {
        // 创建链表 1 -> 2 -> 3 -> 4 -> 2 (形成环)
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);

        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node2; // 形成环

        CycleNodeList solution = new CycleNodeList();
        System.out.println(solution.hasCycle3(node1)); // 输出: true
    }
}
