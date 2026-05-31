import java.util.*;

/**
 * LC 787 - Cheapest Flights Within K Stops.
 *
 * Pattern: Modified Dijkstra / BFS with state = (node, stops).
 * Can also use Bellman-Ford (k+1 relaxations).
 *
 * Dijkstra approach: state = (cost, node, stopsUsed).
 * Don't use visited array — a node can be visited multiple times with different stop counts.
 * Prune: if stops > k, skip.
 *
 * Time  : O(E * K * log(E*K))
 * Space : O(N * K)
 */
public class CheapestFlightsKStops {

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] f : flights) adj.get(f[0]).add(new int[]{f[1], f[2]});

        // dist[node][stops] = min cost to reach node using exactly 'stops' stops.
        // Simpler: just track min stops to reach each node.
        int[] stops = new int[n];
        Arrays.fill(stops, Integer.MAX_VALUE);

        // PQ: {cost, node, stopsUsed}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, src, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int cost = curr[0], u = curr[1], s = curr[2];

            if (u == dst) return cost;
            if (s > k) continue;
            // Prune: if we've reached u with fewer stops before, skip.
            if (s >= stops[u]) continue;
            stops[u] = s;

            for (int[] edge : adj.get(u)) {
                pq.offer(new int[]{cost + edge[1], edge[0], s + 1});
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        CheapestFlightsKStops sol = new CheapestFlightsKStops();
        System.out.println(sol.findCheapestPrice(4,
            new int[][]{{0,1,100},{1,2,100},{2,0,100},{1,3,600},{2,3,200}},
            0, 3, 1)); // 700
        System.out.println(sol.findCheapestPrice(3,
            new int[][]{{0,1,100},{1,2,100},{0,2,500}},
            0, 2, 1)); // 200
        System.out.println(sol.findCheapestPrice(3,
            new int[][]{{0,1,100},{1,2,100},{0,2,500}},
            0, 2, 0)); // 500
    }
}
