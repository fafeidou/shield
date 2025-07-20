package com.example.proguard.algorithm.twoPointers;

/**
 * ✅ 问题描述
 * 给定两个单链表 headA 和 headB，判断它们是否在某个节点处相交。
 * 如果相交，返回交点节点；否则返回 null。
 * 示例：
 * 链表 A: a1 → a2    ┐
 *                   ↓
 * 链表 B: b1 → b2 → c1 → c2
 *
 * 交点是节点 c1。
 *
 * ✅ 思路
 * 使用两个指针 pA 和 pB，分别从两个链表头节点出发：
 * 每次各走一步。
 * 当 pA 到达链表 A 的尾部时，转向链表 B 的头节点。
 * 当 pB 到达链表 B 的尾部时，转向链表 A 的头节点。
 * 如果两个链表相交，这两个指针最终会在交点相遇。
 * 如果不相交，两个指针最终会同时为 null。
 *
 * ✅ 为什么这个方法有效？
 * 假设链表 A 长度为 m，链表 B 长度为 n。
 * 两个指针总共走的步数是 m + n。
 * 通过交换链表，两个指针走过的路径长度一致，最终会在交点或 null 处相遇。
 *
 *
 * 🧮 数学推导（为什么这样走就能相遇）
 * 设：
 * 链表 A 的长度为 lenA
 * 链表 B 的长度为 lenB
 * 交点前 A 的长度为 a
 * 交点前 B 的长度为 b
 * 交点后公共部分的长度为 c
 * 则有：
 * lenA = a + c
 * lenB = b + c
 *
 * 当 pA 和 pB 分别走完对方的链表时：
 * pA 走了 lenA + b = a + c + b
 * pB 走了 lenB + a = b + c + a
 * 两者走过的总长度相等
 *
 * a + b + c = a + b + c
 */
public class IntersectionOfTwoLinkedLists {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode pA = headA;
        ListNode pB = headB;

        while (pA != pB) {
            pA = pA == null ? headB : pA.next;
            pB = pB == null ? headA : pB.next;
        }

        return pA; // 相遇即为交点，否则为 null
    }

    // 辅助方法：打印交点信息
    public void printIntersection(ListNode headA, ListNode headB) {
        ListNode intersection = getIntersectionNode(headA, headB);
        if (intersection != null) {
            System.out.println("两个链表在节点 " + intersection.val + " 相交");
        } else {
            System.out.println("两个链表不相交");
        }
    }

    public static void main(String[] args) {
        // 构建链表 A: 1 -> 2 -> 3
        ListNode headA = new ListNode(1);
        headA.next = new ListNode(2);
        headA.next.next = new ListNode(3);

        // 构建链表 B: 4 -> 5
        ListNode headB = new ListNode(4);
        headB.next = new ListNode(5);

        // 让两个链表在节点 3 相交
        headB.next.next = headA.next.next; // 即节点 3

        IntersectionOfTwoLinkedLists solution = new IntersectionOfTwoLinkedLists();
        solution.printIntersection(headA, headB);
    }
}
