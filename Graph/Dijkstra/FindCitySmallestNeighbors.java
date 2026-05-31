import java.util.*;

/**
 * LC 1334 - Find the City With the Smallest Number of Neighbors at a Threshold Distance.
 *
 * Pattern: Dijkstra from each node (or Floyd-Warshall).
 *
 * For each city, run Dijkstra to find all cities reachable within distanceThreshold.
 * Return the city with the fewest reachable neighbors (tie-break: largest index).
 *
 * Time  : O(V * (V + E) * log V) with Dijkstra from each node.
 * Space : O(V + E)
 */
public class FindCitySmallestNeighbors {

    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        // Build adjacency list.
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] e : edges) {
            adj.get(e[0]).add(new int[]{e[1], e[2]});
            adj.get(e[1]).add(new int[]{e[0], e[2]});
        }

        int minCount = Integer.MAX_VALUE;
        int result = -1;

        for (int src = 0; src < n; src++) {
            int count = dijkstraCount(src, n, adj, distanceThreshold);
            if (count <= minCount) { // <= for tie-break (largest index wins)
                minCount = count;
                result = src;
            }
        }
        return result;
    }

    private int dijkstraCount(int src, int n, List<List<int[]>> adj, int threshold) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        pq.offer(new int[]{0, src});

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

        int count = 0;
        for (int i = 0; i < n; i++) {
            if (i != src && dist[i] <= threshold) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        FindCitySmallestNeighbors sol = new FindCitySmallestNeighbors();
        System.out.println(sol.findTheCity(4,
            new int[][]{{0,1,3},{1,2,1},{1,3,4},{2,3,1}}, 4)); // 3
        System.out.println(sol.findTheCity(5,
            new int[][]{{0,1,2},{0,4,8},{1,2,3},{1,4,2},{2,3,1},{3,4,1}}, 2)); // 0
    }
}
