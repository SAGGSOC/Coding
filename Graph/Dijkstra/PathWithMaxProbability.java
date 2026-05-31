import java.util.*;

/**
 * LC 1514 - Path with Maximum Probability.
 *
 * Pattern: Modified Dijkstra (max-heap, multiply instead of add).
 *
 * Instead of minimizing distance, maximize probability.
 * prob[src] = 1.0, relax: if prob[u] * weight > prob[v] → update.
 * Use a MAX-heap (negate or reverse comparator).
 *
 * Time  : O((V + E) * log V)
 * Space : O(V + E)
 */
public class PathWithMaxProbability {

    public double maxProbability(int n, int[][] edges, double[] succProb,
                                 int start, int end) {
        List<List<double[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < edges.length; i++) {
            adj.get(edges[i][0]).add(new double[]{edges[i][1], succProb[i]});
            adj.get(edges[i][1]).add(new double[]{edges[i][0], succProb[i]});
        }

        double[] prob = new double[n];
        prob[start] = 1.0;

        // Max-heap by probability.
        PriorityQueue<double[]> pq = new PriorityQueue<>((a, b) -> Double.compare(b[0], a[0]));
        pq.offer(new double[]{1.0, start});

        while (!pq.isEmpty()) {
            double[] curr = pq.poll();
            double p = curr[0];
            int u = (int) curr[1];

            if (u == end) return p;
            if (p < prob[u]) continue;

            for (double[] edge : adj.get(u)) {
                int v = (int) edge[0];
                double w = edge[1];
                double newProb = prob[u] * w;
                if (newProb > prob[v]) {
                    prob[v] = newProb;
                    pq.offer(new double[]{newProb, v});
                }
            }
        }
        return 0.0;
    }

    public static void main(String[] args) {
        PathWithMaxProbability sol = new PathWithMaxProbability();
        System.out.println(sol.maxProbability(3,
            new int[][]{{0,1},{1,2},{0,2}},
            new double[]{0.5, 0.5, 0.2}, 0, 2)); // 0.25
        System.out.println(sol.maxProbability(3,
            new int[][]{{0,1},{1,2},{0,2}},
            new double[]{0.5, 0.5, 0.3}, 0, 2)); // 0.3
    }
}
