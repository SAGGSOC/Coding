import java.util.*;

public class PrintTheShortestPath {

    static class Pair {
        int first;   // node
        int second;  // weight / distance
        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public static List<Integer> shortestPath(int edges[][], int n, int m) {
        // Weighted adjacency list: adj[u] = list of (neighbor, weight)
        ArrayList<ArrayList<Pair>> adj = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int u = edges[i][0], v = edges[i][1], w = edges[i][2];
            adj.get(u).add(new Pair(v, w)); // (neighbor, weight)
            adj.get(v).add(new Pair(u, w));
        }

        // Min-heap ordered by distance (second)
        PriorityQueue<Pair> pq = new PriorityQueue<>((x, y) -> x.second - y.second);
        int[] dist = new int[n + 1];
        int[] parent = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            dist[i] = (int) 1e9;
        }
        dist[1] = 0;
        pq.add(new Pair(1, 0)); // (node, distance)

        while (!pq.isEmpty()) {
            Pair it = pq.poll();
            int node = it.first;
            int dis = it.second;

            for (Pair iter : adj.get(node)) {
                int adjNode = iter.first;   // neighbor
                int edW = iter.second;      // edge weight
                if (edW + dis < dist[adjNode]) {
                    dist[adjNode] = edW + dis;
                    pq.add(new Pair(adjNode, dist[adjNode]));
                    parent[adjNode] = node;
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        if (dist[n] == (int) 1e9) {
            path.add(-1);
            return path;
        }
        int node = n;
        while (parent[node] != node) {
            path.add(node);
            node = parent[node];
        }
        path.add(1);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        int[][] edges = {
            {1, 2, 2}, {1, 4, 1}, {2, 3, 4},
            {2, 5, 5}, {4, 5, 2}, {3, 5, 1}
        };
        System.out.println(shortestPath(edges, 5, 6)); // expected: [1, 4, 5]
    }
}
