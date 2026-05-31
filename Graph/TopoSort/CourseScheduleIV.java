import java.util.*;

/**
 * LC 1462 - Course Schedule IV (Prerequisite Queries).
 *
 * Pattern: Topological Sort + Transitive Closure.
 *
 * Approach:
 *   1. Build the graph and compute indegrees.
 *   2. Maintain a boolean matrix isPrereq[i][j] = "is i a prerequisite of j?"
 *   3. BFS in topological order. When processing node u → neighbor v:
 *      - v's prerequisites = v's own prerequisites ∪ u ∪ u's prerequisites.
 *      - i.e., isPrereq[u][v] = true, and for all x where isPrereq[x][u], set isPrereq[x][v] = true.
 *   4. Answer each query in O(1) from the matrix.
 *
 * Alternative: Floyd-Warshall (simpler code, same O(n³) complexity).
 *
 * Time  : O(n² + n*E) for BFS propagation, O(Q) for queries.
 * Space : O(n²) for the reachability matrix.
 */
public class CourseScheduleIV {

    // ---- Approach 1: Topological Sort + BFS propagation ----
    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        int n = numCourses;
        boolean[][] isPrereq = new boolean[n][n];

        // Build adjacency list and indegree.
        List<List<Integer>> adj = new ArrayList<>();
        int[] indegree = new int[n];
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int[] pre : prerequisites) {
            adj.get(pre[0]).add(pre[1]); // pre[0] must be taken before pre[1]
            indegree[pre[1]]++;
        }

        // BFS topological sort with reachability propagation.
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : adj.get(u)) {
                // u is a direct prerequisite of v.
                isPrereq[u][v] = true;
                // All prerequisites of u are also prerequisites of v.
                for (int i = 0; i < n; i++) {
                    if (isPrereq[i][u]) {
                        isPrereq[i][v] = true;
                    }
                }
                indegree[v]--;
                if (indegree[v] == 0) queue.offer(v);
            }
        }

        // Answer queries.
        List<Boolean> result = new ArrayList<>();
        for (int[] q : queries) {
            result.add(isPrereq[q[0]][q[1]]);
        }
        return result;
    }

    // ---- Approach 2: Floyd-Warshall (simpler, same complexity) ----
    public List<Boolean> checkIfPrerequisiteFloyd(int numCourses, int[][] prerequisites, int[][] queries) {
        int n = numCourses;
        boolean[][] reach = new boolean[n][n];

        // Direct edges.
        for (int[] pre : prerequisites) {
            reach[pre[0]][pre[1]] = true;
        }

        // Floyd-Warshall: if i can reach k, and k can reach j, then i can reach j.
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (reach[i][k] && reach[k][j]) {
                        reach[i][j] = true;
                    }
                }
            }
        }

        List<Boolean> result = new ArrayList<>();
        for (int[] q : queries) {
            result.add(reach[q[0]][q[1]]);
        }
        return result;
    }

    public static void main(String[] args) {
        CourseScheduleIV sol = new CourseScheduleIV();

        // Example 1
        System.out.println(sol.checkIfPrerequisite(2,
            new int[][]{{1, 0}},
            new int[][]{{0, 1}, {1, 0}}));
        // [false, true]

        // Example 2
        System.out.println(sol.checkIfPrerequisite(3,
            new int[][]{{1, 2}, {1, 0}, {2, 0}},
            new int[][]{{1, 0}, {1, 2}}));
        // [true, true]

        // Floyd approach
        System.out.println(sol.checkIfPrerequisiteFloyd(3,
            new int[][]{{1, 2}, {1, 0}, {2, 0}},
            new int[][]{{1, 0}, {1, 2}}));
        // [true, true]
    }
}
