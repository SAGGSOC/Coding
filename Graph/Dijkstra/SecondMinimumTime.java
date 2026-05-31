import java.util.*;

/**
 * LC 2045 - Second Minimum Time to Reach Destination.
 *
 * Pattern: Modified BFS/Dijkstra tracking 2 best distances per node.
 *
 * Key: All edges have equal weight (time), but traffic lights add periodic delays.
 * We need the SECOND shortest path (strictly greater than the shortest).
 *
 * Approach:
 *   - BFS (since all edges have same weight 'time').
 *   - Track dist1[v] (shortest) and dist2[v] (second shortest, strictly > dist1).
 *   - When we reach node n with dist2[n], that's the answer.
 *   - Apply traffic light: if arrival time falls in a red phase, wait until green.
 *
 * Traffic light logic:
 *   At time t, if (t / change) is odd → red → wait until next green = (t/change + 1) * change.
 *
 * Time  : O(V + E)
 * Space : O(V)
 */
public class SecondMinimumTime {

    public int secondMinimum(int n, int[][] edges, int time, int change) {
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(e[1]);
            adj.get(e[1]).add(e[0]);
        }

        // dist1[i] = shortest time, dist2[i] = second shortest time (strictly >)
        int[] dist1 = new int[n + 1];
        int[] dist2 = new int[n + 1];
        Arrays.fill(dist1, Integer.MAX_VALUE);
        Arrays.fill(dist2, Integer.MAX_VALUE);
        dist1[1] = 0;

        // BFS: queue stores {time, node}
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 1});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int t = curr[0], u = curr[1];

            for (int v : adj.get(u)) {
                // Apply traffic light at node u before departing.
                int depart = t;
                if ((depart / change) % 2 == 1) {
                    // Red light — wait until next green.
                    depart = (depart / change + 1) * change;
                }
                int arrive = depart + time;

                if (arrive < dist1[v]) {
                    dist2[v] = dist1[v];
                    dist1[v] = arrive;
                    queue.offer(new int[]{arrive, v});
                } else if (arrive > dist1[v] && arrive < dist2[v]) {
                    dist2[v] = arrive;
                    queue.offer(new int[]{arrive, v});
                }
            }
        }
        return dist2[n];
    }

    public static void main(String[] args) {
        SecondMinimumTime sol = new SecondMinimumTime();
        System.out.println(sol.secondMinimum(5,
            new int[][]{{1,2},{1,3},{1,4},{3,4},{4,5}}, 3, 5)); // 13
        System.out.println(sol.secondMinimum(2,
            new int[][]{{1,2}}, 3, 2)); // 11
    }
}
