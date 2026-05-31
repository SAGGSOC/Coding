import java.util.*;

/**
 * LC 1129 - Shortest Path with Alternating Colors.
 *
 * Pattern: BFS with state = (node, lastColor).
 *
 * Key Insight:
 *   Arriving at node X via a RED edge is a different state than arriving via BLUE.
 *   Because the next edge must be the opposite color.
 *
 * Approach:
 *   - Build two adjacency lists: one for red edges, one for blue edges.
 *   - BFS state: (node, lastColor). Start with both (0, RED) and (0, BLUE) at distance 0.
 *   - visited[node][color] to avoid revisiting the same state.
 *   - For each state, follow edges of the OPPOSITE color.
 *   - answer[node] = min distance across both colors.
 *
 * Time  : O(n + E) where E = total edges
 * Space : O(n + E)
 */
public class ShortestAlternatingPaths {

    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        // 0 = red, 1 = blue
        List<List<Integer>>[] adj = new List[2];
        adj[0] = new ArrayList<>(); // red adjacency
        adj[1] = new ArrayList<>(); // blue adjacency
        for (int i = 0; i < n; i++) {
            adj[0].add(new ArrayList<>());
            adj[1].add(new ArrayList<>());
        }

        for (int[] e : redEdges) adj[0].get(e[0]).add(e[1]);
        for (int[] e : blueEdges) adj[1].get(e[0]).add(e[1]);

        // dist[node][color] = shortest distance to reach node with last edge = color.
        int[][] dist = new int[n][2];
        for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);

        // BFS: queue stores {node, lastColor}
        Queue<int[]> queue = new LinkedList<>();

        // Start: node 0, can begin with either color.
        dist[0][0] = 0;
        dist[0][1] = 0;
        queue.offer(new int[]{0, 0}); // arrived at 0, "last" was red (so next must be blue)
        queue.offer(new int[]{0, 1}); // arrived at 0, "last" was blue (so next must be red)

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int node = curr[0], lastColor = curr[1];
            int nextColor = 1 - lastColor; // alternate!

            for (int neighbor : adj[nextColor].get(node)) {
                if (dist[neighbor][nextColor] == Integer.MAX_VALUE) {
                    dist[neighbor][nextColor] = dist[node][lastColor] + 1;
                    queue.offer(new int[]{neighbor, nextColor});
                }
            }
        }

        // Answer = min(dist[node][red], dist[node][blue]) for each node.
        int[] answer = new int[n];
        for (int i = 0; i < n; i++) {
            int minDist = Math.min(dist[i][0], dist[i][1]);
            answer[i] = (minDist == Integer.MAX_VALUE) ? -1 : minDist;
        }
        return answer;
    }

    public static void main(String[] args) {
        ShortestAlternatingPaths sol = new ShortestAlternatingPaths();

        System.out.println(Arrays.toString(sol.shortestAlternatingPaths(3,
            new int[][]{{0,1},{1,2}}, new int[][]{})));
        // [0, 1, -1]

        System.out.println(Arrays.toString(sol.shortestAlternatingPaths(3,
            new int[][]{{0,1}}, new int[][]{{2,1}})));
        // [0, 1, -1]

        System.out.println(Arrays.toString(sol.shortestAlternatingPaths(3,
            new int[][]{{0,1}}, new int[][]{{1,2}})));
        // [0, 1, 2]

        System.out.println(Arrays.toString(sol.shortestAlternatingPaths(3,
            new int[][]{{0,1},{0,2}}, new int[][]{{1,0}})));
        // [0, 1, 1]
    }
}
