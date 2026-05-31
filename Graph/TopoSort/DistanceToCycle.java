import java.util.*;

/**
 * LC 2204 - Distance to a Cycle in Undirected Graph.
 *
 * Pattern: Topological Sort (leaf trimming) + Multi-source BFS.
 *
 * The graph has exactly n nodes and n edges (one cycle + trees hanging off it).
 *
 * Approach:
 *   Step 1: Find cycle nodes using topological sort (peel leaves).
 *     - Repeatedly remove nodes with degree 1 (leaves).
 *     - Nodes that remain = the cycle.
 *
 *   Step 2: Multi-source BFS from all cycle nodes.
 *     - Cycle nodes have distance 0.
 *     - BFS outward: each layer increases distance by 1.
 *
 * Time  : O(n)
 * Space : O(n)
 */
public class DistanceToCycle {

    public int[] distanceToCycle(int n, int[][] edges) {
        // Build adjacency list and degree.
        List<List<Integer>> adj = new ArrayList<>();
        int[] degree = new int[n];
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
            degree[edge[0]]++;
            degree[edge[1]]++;
        }

        // Step 1: Topological sort — peel leaves to find cycle nodes.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1) queue.offer(i);
        }

        boolean[] inCycle = new boolean[n];
        Arrays.fill(inCycle, true);

        while (!queue.isEmpty()) {
            int leaf = queue.poll();
            inCycle[leaf] = false; // not part of cycle
            for (int neighbor : adj.get(leaf)) {
                degree[neighbor]--;
                if (degree[neighbor] == 1) {
                    queue.offer(neighbor);
                }
            }
        }

        // Step 2: Multi-source BFS from all cycle nodes.
        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        Queue<Integer> bfs = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (inCycle[i]) {
                dist[i] = 0;
                bfs.offer(i);
            }
        }

        while (!bfs.isEmpty()) {
            int node = bfs.poll();
            for (int neighbor : adj.get(node)) {
                if (dist[neighbor] == -1) {
                    dist[neighbor] = dist[node] + 1;
                    bfs.offer(neighbor);
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        DistanceToCycle sol = new DistanceToCycle();

        // Example 1:
        //   0 - 1 - 2 - 3 - 0 (cycle: 0,1,2,3)
        //       |
        //       4
        //       |
        //       5
        System.out.println(Arrays.toString(sol.distanceToCycle(6,
            new int[][]{{0,1},{1,2},{2,3},{3,0},{1,4},{4,5}})));
        // [0, 0, 0, 0, 1, 2]

        // Example 2:
        //   0 - 1 - 2 - 0 (cycle: 0,1,2)
        //   |
        //   3
        System.out.println(Arrays.toString(sol.distanceToCycle(4,
            new int[][]{{0,1},{1,2},{2,0},{0,3}})));
        // [0, 0, 0, 1]
    }
}
