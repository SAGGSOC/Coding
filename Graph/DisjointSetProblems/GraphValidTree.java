import java.util.*;

/**
 * LC 261 - Graph Valid Tree.
 *
 * A graph is a valid tree if and only if:
 *   1. It has exactly n-1 edges (necessary for a tree with n nodes).
 *   2. It is fully connected (all nodes reachable from any node).
 *
 * If both conditions hold → no cycles and connected → valid tree.
 * (n-1 edges + connected → automatically no cycles)
 *
 * Approach 1: Union-Find (Disjoint Set).
 *   - Start with n components.
 *   - For each edge, union the two nodes.
 *   - If they already share the same parent → cycle → not a tree.
 *   - At the end, check if there's exactly 1 component.
 *
 * Approach 2: BFS/DFS.
 *   - Quick check: edges.length must be n-1.
 *   - BFS from node 0, count visited nodes.
 *   - If visited == n → connected → valid tree.
 *
 * Time  : O(n + E) for BFS, O(E * α(n)) for Union-Find
 * Space : O(n + E)
 */
public class GraphValidTree {

    // ---- Approach 1: Union-Find ----
    public boolean validTreeUF(int n, int[][] edges) {
        // A tree with n nodes must have exactly n-1 edges.
        if (edges.length != n - 1) return false;

        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) parent[i] = i;

        for (int[] edge : edges) {
            int pu = find(parent, edge[0]);
            int pv = find(parent, edge[1]);
            if (pu == pv) return false; // cycle detected
            // Union by rank
            if (rank[pu] < rank[pv]) parent[pu] = pv;
            else if (rank[pv] < rank[pu]) parent[pv] = pu;
            else { parent[pv] = pu; rank[pu]++; }
        }
        return true; // n-1 edges + no cycle → connected tree
    }

    private int find(int[] parent, int x) {
        if (parent[x] != x) parent[x] = find(parent, parent[x]);
        return parent[x];
    }

    // ---- Approach 2: BFS ----
    public boolean validTreeBFS(int n, int[][] edges) {
        if (edges.length != n - 1) return false;

        // Build adjacency list.
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        // BFS from node 0.
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        visited[0] = true;
        int count = 0;

        while (!queue.isEmpty()) {
            int node = queue.poll();
            count++;
            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }
        return count == n; // all nodes reachable → connected
    }

    // ---- Approach 3: DFS ----
    public boolean validTreeDFS(int n, int[][] edges) {
        if (edges.length != n - 1) return false;

        // Build adjacency list.
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        // DFS from node 0, count reachable nodes.
        boolean[] visited = new boolean[n];
        int count = dfs(0, visited, adj);
        return count == n;
    }

    private int dfs(int node, boolean[] visited, List<List<Integer>> adj) {
        visited[node] = true;
        int count = 1;
        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) {
                count += dfs(neighbor, visited, adj);
            }
        }
        return count;
    }

    public static void main(String[] args) {
        GraphValidTree sol = new GraphValidTree();

        // Example 1: valid tree
        //   0 - 1 - 2 - 3 - 4
        System.out.println(sol.validTreeUF(5, new int[][]{{0,1},{0,2},{0,3},{1,4}}));  // true
        System.out.println(sol.validTreeBFS(5, new int[][]{{0,1},{0,2},{0,3},{1,4}})); // true

        // Example 2: has cycle
        //   0 - 1 - 2
        //   |       |
        //   3 - 4 --+
        System.out.println(sol.validTreeUF(5, new int[][]{{0,1},{1,2},{2,3},{1,3},{1,4}}));  // false
        System.out.println(sol.validTreeBFS(5, new int[][]{{0,1},{1,2},{2,3},{1,3},{1,4}})); // false

        // Example 3: disconnected
        System.out.println(sol.validTreeUF(4, new int[][]{{0,1},{2,3}}));  // false
        System.out.println(sol.validTreeBFS(4, new int[][]{{0,1},{2,3}})); // false
        System.out.println(sol.validTreeDFS(4, new int[][]{{0,1},{2,3}})); // false

        // DFS tests
        System.out.println("\n--- DFS ---");
        System.out.println(sol.validTreeDFS(5, new int[][]{{0,1},{0,2},{0,3},{1,4}}));          // true
        System.out.println(sol.validTreeDFS(5, new int[][]{{0,1},{1,2},{2,3},{1,3},{1,4}}));    // false
    }
}
