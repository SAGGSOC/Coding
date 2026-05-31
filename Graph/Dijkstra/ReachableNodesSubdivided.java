import java.util.*;

/**
 * LC 882 - Reachable Nodes In Subdivided Graph.
 *
 * Pattern: Dijkstra + post-processing to count reachable sub-nodes.
 *
 * Each edge (u, v, cnt) is subdivided into cnt+1 segments (cnt new nodes between u and v).
 * From node 0, with at most maxMoves moves, count how many nodes (original + new) are reachable.
 *
 * Approach:
 *   1. Run Dijkstra from node 0 on the ORIGINAL graph (edge weight = cnt + 1).
 *   2. For each original node: reachable if dist[node] <= maxMoves.
 *   3. For each edge (u, v, cnt): count how many sub-nodes on this edge are reachable.
 *      From u's side: min(cnt, maxMoves - dist[u]) if dist[u] <= maxMoves.
 *      From v's side: min(cnt, maxMoves - dist[v]) if dist[v] <= maxMoves.
 *      Total for this edge: min(cnt, fromU + fromV).
 *
 * Time  : O((V + E) * log V)
 * Space : O(V + E)
 */
public class ReachableNodesSubdivided {

    public int reachableNodes(int[][] edges, int maxMoves, int n) {
        // Build adjacency list: {neighbor, subdivisions (cnt)}
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(new int[]{e[1], e[2]});
            adj.get(e[1]).add(new int[]{e[0], e[2]});
        }

        // Dijkstra from node 0. Edge weight = cnt + 1 (pass through all sub-nodes + reach other end).
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[0] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0], u = curr[1];
            if (d > dist[u]) continue;
            for (int[] edge : adj.get(u)) {
                int v = edge[0], cnt = edge[1];
                int newDist = dist[u] + cnt + 1;
                if (newDist < dist[v]) {
                    dist[v] = newDist;
                    pq.offer(new int[]{newDist, v});
                }
            }
        }

        // Count reachable original nodes.
        int result = 0;
        for (int i = 0; i < n; i++) {
            if (dist[i] <= maxMoves) result++;
        }

        // Count reachable sub-nodes on each edge.
        for (int[] e : edges) {
            int u = e[0], v = e[1], cnt = e[2];
            int fromU = dist[u] <= maxMoves ? Math.max(0, maxMoves - dist[u]) : 0;
            int fromV = dist[v] <= maxMoves ? Math.max(0, maxMoves - dist[v]) : 0;
            result += Math.min(cnt, fromU + fromV);
        }

        return result;
    }

    public static void main(String[] args) {
        ReachableNodesSubdivided sol = new ReachableNodesSubdivided();
        System.out.println(sol.reachableNodes(
            new int[][]{{0,1,10},{0,2,1},{1,2,2}}, 6, 3)); // 13
        System.out.println(sol.reachableNodes(
            new int[][]{{0,1,4},{1,2,6},{0,2,8},{1,3,1}}, 10, 4)); // 23
    }
}
