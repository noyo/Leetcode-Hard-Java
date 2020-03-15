import java.util.*;

/**
 * 题目：5359. 最大的团队表现值
 * 描述：
 * 公司有编号为 1 到 n 的 n 个工程师，给你两个数组 speed 和 efficiency ，
 * 其中 speed[i] 和 efficiency[i] 分别代表第 i 位工程师的速度和效率。
 * 请你返回由最多 k 个工程师组成的 ​​​​​​最大团队表现值 ，由于答案可能很大，请你返回结果对 10^9 + 7 取余后的结果。
 *
 * 团队表现值 的定义为：一个团队中「所有工程师速度的和」乘以他们「效率值中的最小值」。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/maximum-performance-of-a-team
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * <p>
 * 解题思路：
 * 由题意知表现值公式有两个乘法因子：速度和、最小效率，所有首先我们可以通过效率来对工程师降序排序，
 * 所以排序从左往右遍历可以得到前k个工程师的最大‘最小效率’值。
 * 最多k个工程师，所有可以用一个优先队列保存遍历到当前下标的最多k个效率最大的工程师，
 * 所以遍历到当前下标时的最大‘最小效率’乘以优先队列里的k个工程师的速度和即可得到当前的最大表现值。
 * <p>
 * create by chris https://github.com/noyo/Leetcode-Hard-Java
 */
public class Problem5359 {

    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        Integer order[] = new Integer[n];
        for (int i = 0; i < n; i++) {
            order[i] = i;
        }
        Arrays.sort(order, (o1, o2) -> {
            if (efficiency[o1] == efficiency[o2]) {
                return speed[o2] - speed[o1];
            }
            return efficiency[o2] - efficiency[o1];
        });
        long max = 0;
        long s = 0;
        //最多k个效率
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            int idx = order[i];
            s += speed[idx];
            long mul = s;
            if (queue.size() == k) {
                mul -= queue.peek();
            }
            //当选中当前工程师时才需要重新计算表现值，所以当前最低效率值为efficiency[idx];
            max = Math.max(max, mul * efficiency[idx]);
            if (queue.size() < k) {
                queue.offer(speed[idx]);
            } else {
                if (speed[idx] > queue.peek()) {
                    s -= queue.poll();
                    queue.offer(speed[idx]);
                } else {
                    s -= speed[idx];
                }
            }
        }
        return (int) (max % (1000000007));
    }
}
