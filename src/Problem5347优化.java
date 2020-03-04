import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 题目：5347. 使网格图至少有一条有效路径的最小代价
 * 描述：
 * 给你一个 m x n 的网格图 grid 。 grid 中每个格子都有一个数字，对应着从该格子出发下一步走的方向。
 * grid[i][j] 中的数字可能为以下几种情况：
 * 1 ，下一步往右走，也就是你会从 grid[i][j] 走到 grid[i][j + 1]
 * 2 ，下一步往左走，也就是你会从 grid[i][j] 走到 grid[i][j - 1]
 * 3 ，下一步往下走，也就是你会从 grid[i][j] 走到 grid[i + 1][j]
 * 4 ，下一步往上走，也就是你会从 grid[i][j] 走到 grid[i - 1][j]
 * 注意网格图中可能会有 无效数字 ，因为它们可能指向 grid 以外的区域。
 * 一开始，你会从最左上角的格子 (0,0) 出发。我们定义一条 有效路径 为从格子 (0,0) 出发，
 * 每一步都顺着数字对应方向走，最终在最右下角的格子 (m - 1, n - 1) 结束的路径。有效路径不需要是最短路径 。
 * 你可以花费 cost = 1 的代价修改一个格子中的数字，但每个格子中的数字 只能修改一次 。
 * 请你返回让网格图至少有一条有效路径的最小代价。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * 解题思路：
 * PS：BFS+记忆搜索
 * 这题是求最短距离的变种，按最短距离的bfs解法来写。
 * 在这题中求的最小cost可以当作最短距离，只是这个cost的算法不太一样，当我们
 * 使用bfs时向上下左右四个方向扩展，向网络所指方向扩展则cost不变，往其他方向
 * 则cost+1，遍历过程中使用二维数组dst来保存由(0, 0)到其他网格的最小花费。
 * <p>
 * 优化：采用0-1 bfs
 * 使用双端队列来实现
 * <p>
 * create by chris https://github.com/noyo/
 */
public class Problem5347优化 {

    public int minCost(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        //由(0, 0)到其他网格的最小花费，为-1则表示待计算
        int dst[][] = new int[n][m];
        //用来保存待扩展的四个方向在纵轴和横轴上的增量，右-左-下-上
        int d[][] = {{}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int i = 0; i < n; i++) {
            Arrays.fill(dst[i], -1);
        }
        //用来执行bfs的队列
        Deque<int[]> deque = new LinkedList<>();
        //int数组三个参数：纵轴、横轴、当前cost
        deque.offerFirst(new int[]{0, 0});
        dst[0][0] = 0;
        boolean vis[][] = new boolean[n][m];
        while (!deque.isEmpty()) {
            int q[] = deque.pollFirst();
            if (q[0] == n - 1 && q[1] == m - 1) {
                break;
            }
            if (vis[q[0]][q[1]]) {
                continue;
            }
            vis[q[0]][q[1]] = true;
            for (int j = 1; j <= 4; j++) {
                int r = q[0] + d[j][0];
                int c = q[1] + d[j][1];
                int val = grid[q[0]][q[1]] == j ? 0 : 1;
                int newDst = dst[q[0]][q[1]] + val;
                if (r >= 0 && c >= 0 && r < n && c < m
                        && (dst[r][c] == -1 || dst[r][c] > newDst)) {
                    dst[r][c] = newDst;
                    if (val == 0) {
                        deque.offerFirst(new int[]{r, c});
                    } else {
                        deque.offerLast(new int[]{r, c});
                    }
                }
            }
        }
        return dst[n - 1][m - 1];
    }
}
