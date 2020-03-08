/**
 * 题目：5339. 二叉搜素子树的最大键值和
 * 描述：
 * 给你一棵以 root 为根的 二叉树 ，请你返回 任意 二叉搜索子树的最大键值和。
 * <p>
 * 二叉搜索树的定义如下：
 * <p>
 * <p>
 * 任意节点的左子树中的键值都 小于 此节点的键值。
 * 任意节点的右子树中的键值都 大于 此节点的键值。
 * 任意节点的左子树和右子树都是二叉搜索树。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-sum-bst-in-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * 解题思路：
 * PS: 题意所述的搜索子树是指包含所有子节点的树，
 * 比如节点A是子树的根节点，则有效的子树必须包含A的左节点和右节点，且其子节点也必须包含所有的子节点
 * 使用dfs，用数组保存当前节点的状态，{ 以当前节点为根节点的子树中的最大值，以当前节点为根节点的子树中的最小值
 * ，以当前节点为根节点的子树中所有节点的和 }。其中‘以当前节点为根节点的子树’成立的前提是该子树满足搜索子树的定义
 * <p>
 * create by chris https://github.com/noyo/Leetcode-Hard-Java
 */
public class Problem5339 {

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    int max = 0;

    public int[] dfs(TreeNode root) {
        //保存当前节点的状态，具体含义见注释
        int res[] = new int[3];
        if (null == root) {
            res[0] = Integer.MAX_VALUE;
            res[1] = Integer.MIN_VALUE;
            res[2] = 0;
            return res;
        }
        int lft[] = dfs(root.left);
        int rht[] = dfs(root.right);
        int cur = root.val;
        if ((null == root.left || lft[1] < root.val) && (null == root.right || rht[0] > root.val)) {
            res[0] = Math.min(lft[0], root.val);
            res[1] = Math.max(rht[1], root.val);
            cur += lft[2] + rht[2];
            res[2] = cur;
            max = Math.max(max, cur);
            return res;
        }
        res[0] = Integer.MIN_VALUE;
        res[1] = Integer.MAX_VALUE;
        return res;
    }

    public int maxSumBST(TreeNode root) {
        if (null == root) {
            return 0;
        }
        dfs(root);
        return max;
    }
}
