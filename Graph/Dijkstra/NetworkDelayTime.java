import java.util.*;

/**
 * LC 743 - Network Delay Time.
 *
 * Pattern: Dijkstra's Algorithm.
 *
 * Run Dijkstra from node k. The answer is max(dist[]) — the time it takes
 * for the signal to reach the LAST node. If any node is unreachable, return -1.
 *
 * Time  : O((V + E) * log V)
 * Space : O(V + E)
 */
public class NetworkDelayTime {

    public int networkDelayTime(int[][] times, int n, int k) {
        // Build adjacency list. Nodes are 1-indexed.
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) adj.add(new ArrayList<>());

        for (int[] t : times) {
            adj.get(t[0]).add(new int[]{t[1], t[2]});
        }

        // Dijkstra from node k.
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;

        // PQ: {distance, node}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, k});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0], u = curr[1];

            if (d > dist[u]) continue;

            for (int[] edge : adj.get(u)) {
                int v = edge[0], w = edge[1];
                if (dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    pq.offer(new int[]{dist[v], v});
                }
            }
        }

        // Answer = max distance among all nodes.
        int ans = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) return -1; // unreachable
            ans = Math.max(ans, dist[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        NetworkDelayTime sol = new NetworkDelayTime();

        System.out.println(sol.networkDelayTime(
            new int[][]{{2,1,1},{2,3,1},{3,4,1}}, 4, 2));
        // 2

        System.out.println(sol.networkDelayTime(
            new int[][]{{1,2,1}}, 2, 1));
        // 1

        System.out.println(sol.networkDelayTime(
            new int[][]{{1,2,1}}, 2, 2));
        // -1 (node 1 unreachable from node 2)
    }
}
