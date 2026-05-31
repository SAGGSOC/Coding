import java.util.*;

/**
 * LC 1976 - Number of Ways to Arrive at Destination.
 *
 * Pattern: Dijkstra + Path Counting.
 *
 * Approach:
 *   - Run Dijkstra from node 0.
 *   - Maintain ways[i] = number of shortest paths from 0 to i.
 *   - When relaxing edge u→v with weight w:
 *     - If dist[u] + w < dist[v]: found a shorter path → dist[v] = dist[u]+w, ways[v] = ways[u].
 *     - If dist[u] + w == dist[v]: found another shortest path → ways[v] += ways[u].
 *   - Answer = ways[n-1] % MOD.
 *
 * Time  : O((V + E) * log V)
 * Space : O(V + E)
 */
public class NumberOfWaysToArrive {

    public int countPaths(int n, int[][] roads) {
        long MOD = 1_000_000_007;

        // Build adjacency list: {neighbor, weight}
        List<List<long[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] road : roads) {
            int u = road[0], v = road[1], w = road[2];
            adj.get(u).add(new long[]{v, w});
            adj.get(v).add(new long[]{u, w});
        }

        // Dijkstra
        long[] dist = new long[n];
        long[] ways = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        ways[0] = 1;

        // PQ: {distance, node}
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, 0});

        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            long d = curr[0];
            int u = (int) curr[1];

            // Skip if we've already found a shorter path to u.
            if (d > dist[u]) continue;

            for (long[] edge : adj.get(u)) {
                int v = (int) edge[0];
                long w = edge[1];
                long newDist = dist[u] + w;

                if (newDist < dist[v]) {
                    // Found a shorter path to v.
                    dist[v] = newDist;
                    ways[v] = ways[u];
                    pq.offer(new long[]{newDist, v});
                } else if (newDist == dist[v]) {
                    // Found another shortest path to v.
                    ways[v] = (ways[v] + ways[u]) % MOD;
                }
            }
        }

        return (int) (ways[n - 1] % MOD);
    }

    public static void main(String[] args) {
        NumberOfWaysToArrive sol = new NumberOfWaysToArrive();

        System.out.println(sol.countPaths(7, new int[][]{
            {0,6,7},{0,1,2},{1,2,3},{1,3,3},{6,3,3},
            {3,5,1},{6,5,1},{2,5,1},{0,4,5},{4,6,2}
        }));
        // 4

        System.out.println(sol.countPaths(2, new int[][]{{1,0,10}}));
        // 1
    }
}
