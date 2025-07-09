package com.example.proguard.algorithm.binarytree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 最小深度是从根节点到最近叶子节点的最短路径上的节点数量。
 * https://juejin.cn/post/7129485867671879687#heading-4 图解
 *
 *
 */
public class BinaryTreeMinDepth {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }


    /**
     * 计算二叉树的最小深度。
     *📈 时间与空间复杂度：
     * 时间复杂度： O(n)，其中 n 是树中节点的数量。每个节点都会被访问一次。
     * 空间复杂度： O(h)，其中 h 是树的高度。递归调用栈的最大深度由树的高度决定
     * @param root 二叉树根节点
     * @return 最小深度
     */
    public int minDepthDFS(TreeNode root) {
        // 如果当前节点为空，则深度为0
        if (root == null) {
            return 0;
        }

        // 如果左子树为空，递归右子树
        if (root.left == null) {
            return minDepthDFS(root.right) + 1;
        }

        // 如果右子树为空，递归左子树
        if (root.right == null) {
            return minDepthDFS(root.left) + 1;
        }

        // 否则分别递归左右子树，并取较小值
        int leftDepth = minDepthDFS(root.left);
        int rightDepth = minDepthDFS(root.right);

        return Math.min(leftDepth, rightDepth) + 1;
    }

    /**
     * 计算二叉树的最小深度。
     *📈 时间与空间复杂度：
     * 时间复杂度： O(n)，其中 n 是树中节点的数量。每个节点都会被访问一次。
     * 空间复杂度： O(w)，其中 w 是树中最宽的一层所含的节点数（即队列中最多存储的节点数）。
     * @param root 二叉树根节点
     * @return 最小深度
     */
    public int minDepthBFS(TreeNode root) {
        // 空树，高度为 0
        if (root == null) {
            return 0;
        }
        // 初始化队列和层次
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 1;

        // 当队列不为空
        while (!queue.isEmpty()) {
            // 当前层的节点数
            int n = queue.size();
            // 弹出当前层的所有节点，并将所有子节点入队列
            for (int i = 0; i < n; i++) {
                TreeNode node = queue.poll();
                // 最早出现左右节点都为空，证明为叶子节点，即为二叉树的最小高度
                if (node.left == null && node.right == null) {
                    return depth;
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            depth++;
        }

        return depth;
    }

    public static void main(String[] args) {
        // 构造测试用例
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);

        BinaryTreeMinDepth solution = new BinaryTreeMinDepth();
        System.out.println("最小深度: " + solution.minDepthBFS(root)); // 输出 2
    }

}
