package com.example.proguard.algorithm.stack;

import java.util.Stack;

/**
 * 使用两个栈来模拟队列是一种经典的算法问题，
 * 目的是实现队列的 push、pop、peek 和 empty 操作。
 * 通过两个栈的配合，可以实现先进先出（FIFO）的行为。
 *
 * ✅ 问题描述
 * 使用两个栈 stack1 和 stack2，模拟一个队列的功能：
 * push(x)：将元素 x 入队。
 * pop()：移除并返回队列开头的元素。
 * peek()：返回队列开头的元素。
 * empty()：判断队列是否为空。
 *
 * ✅ 解题思路
 * 我们使用两个栈：
 * stack1：用于 入队操作。
 * stack2：用于 出队操作。
 * 🧠 核心逻辑
 * 入队时（push）：始终将元素压入 stack1。
 * 出队时（pop/peek）：
 *  如果 stack2 为空，就将 stack1 中的所有元素依次弹出并压入 stack2，
 *  这样 stack2 的栈顶就是最早入队的元素。然后从 stack2 弹出元素即可。
 * 这样，两个栈配合实现了 先进先出 的行为。
 *
 * ✅ 时间复杂度分析
 * 操作
 * 时间复杂度（均摊）
 * push(x) (1)
 * pop() O(1)（均摊）
 * peek() O(1)（均摊）
 * empty() O(1)
 * 虽然某些 pop() 操作可能需要将所有元素从 stack1 倒入 stack2（O(n)），
 * 但每个元素最多被压入和弹出两次，因此均摊下来是 O(1)。
 */
public class MyQueue {
    private Stack<Integer> stack1; // 用于入队
    private Stack<Integer> stack2; // 用于出队

    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    // 入队：始终压入 stack1
    public void push(int x) {
        stack1.push(x);
    }

    // 出队：从 stack2 弹出，如果 stack2 为空，先将 stack1 元素倒入 stack2
    public int pop() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) {
            throw new IllegalStateException("队列为空");
        }
        return stack2.pop();
    }

    // 查看队列头部元素
    public int peek() {
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        if (stack2.isEmpty()) {
            throw new IllegalStateException("队列为空");
        }
        return stack2.peek();
    }

    // 判断队列是否为空
    public boolean empty() {
        return stack1.isEmpty() && stack2.isEmpty();
    }

    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        queue.push(1);
        queue.push(2);
        queue.push(3);

        System.out.println("队列头部元素: " + queue.peek()); // 输出 1
        System.out.println("出队元素: " + queue.pop());     // 输出 1
        System.out.println("出队元素: " + queue.pop());     // 输出 2
        System.out.println("队列是否为空: " + queue.empty()); // 输出 false
        System.out.println("队列头部元素: " + queue.peek()); // 输出 3
    }

}
