import java.util.*;

/**
 * LC 399 - Evaluate Division.
 *
 * Model as a weighted directed graph:
 *   a / b = val  →  edge a→b with weight val, edge b→a with weight 1/val.
 *
 * For each query C/D, BFS from C to D multiplying edge weights along the path.
 * If no path exists or either variable is unknown, answer is -1.0.
 *
 * Time  : O(Q * (V + E))  — BFS per query
 * Space : O(V + E)        — adjacency list
 */
public class EvaluateDivision {

    public double[] calcEquation(List<List<String>> equations,
                                 double[] values,
                                 List<List<String>> queries) {

        // Step 1: Build weighted adjacency list.
        // graph[node] = { neighbor -> edgeWeight }
        Map<String, Map<String, Double>> graph = new HashMap<>();

        for (int i = 0; i < equations.size(); i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);
            double w = values[i];

            graph.computeIfAbsent(a, k -> new HashMap<>()).put(b, w);
            graph.computeIfAbsent(b, k -> new HashMap<>()).put(a, 1.0 / w);
        }

        // Step 2: Answer each query via BFS.
        double[] result = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String src = queries.get(i).get(0);
            String dst = queries.get(i).get(1);
            result[i] = bfs(graph, src, dst);
        }
        return result;
    }

    private double bfs(Map<String, Map<String, Double>> graph, String src, String dst) {
        // If either variable is not in the graph, answer is unknown.
        if (!graph.containsKey(src) || !graph.containsKey(dst)) return -1.0;
        // Same variable: a/a = 1.
        if (src.equals(dst)) return 1.0;

        // BFS: queue stores (currentNode, productSoFar).
        Queue<Object[]> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.offer(new Object[]{src, 1.0});
        visited.add(src);

        while (!queue.isEmpty()) {
            Object[] curr = queue.poll();
            String node = (String) curr[0];
            double product = (double) curr[1];

            for (Map.Entry<String, Double> neighbor : graph.get(node).entrySet()) {
                String next = neighbor.getKey();
                double weight = neighbor.getValue();

                if (next.equals(dst)) return product * weight;

                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(new Object[]{next, product * weight});
                }
            }
        }
        return -1.0; // No path found.
    }

    // --- Driver for quick testing ---
    public static void main(String[] args) {
        EvaluateDivision sol = new EvaluateDivision();

        List<List<String>> equations = Arrays.asList(
            Arrays.asList("a", "b"),
            Arrays.asList("b", "c")
        );
        double[] values = {2.0, 3.0};
        List<List<String>> queries = Arrays.asList(
            Arrays.asList("a", "c"),
            Arrays.asList("b", "a"),
            Arrays.asList("a", "e"),
            Arrays.asList("a", "a"),
            Arrays.asList("x", "x")
        );

        double[] ans = sol.calcEquation(equations, values, queries);
        System.out.println(Arrays.toString(ans));
        // Expected: [6.0, 0.5, -1.0, 1.0, -1.0]
    }
}
