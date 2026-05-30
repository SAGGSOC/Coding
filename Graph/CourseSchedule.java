import java.util.*;

/**
 * LC 207 - Course Schedule (Can Finish)
 * LC 210 - Course Schedule II (Find Order)
 *
 * Pattern: Topological Sort (Kahn's Algorithm / BFS with Indegree).
 *
 * Key Idea:
 *   - Build a directed graph: prerequisite[1] → prerequisite[0] (must take b before a).
 *   - Compute indegree of each node.
 *   - Start BFS from all nodes with indegree 0 (no prerequisites).
 *   - Process each node: reduce indegree of neighbors. If indegree becomes 0, add to queue.
 *   - If all nodes are visited → no cycle → can finish. The visit order IS the topological order.
 *
 * Time  : O(V + E)
 * Space : O(V + E)
 */
public class CourseSchedule {

    // ---- LC 207: Can you finish all courses? (cycle detection) ----
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());

        for (int[] pre : prerequisites) {
            adj.get(pre[1]).add(pre[0]);
            indegree[pre[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }

        int nodesVisited = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            nodesVisited++;
            for (int neighbour : adj.get(node)) {
                indegree[neighbour]--;
                if (indegree[neighbour] == 0) queue.offer(neighbour);
            }
        }
        return nodesVisited == numCourses;
    }

    // ---- LC 210: Return the ordering of courses (topological order) ----
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) adj.add(new ArrayList<>());

        for (int[] pre : prerequisites) {
            adj.get(pre[1]).add(pre[0]);
            indegree[pre[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) queue.offer(i);
        }

        int[] order = new int[numCourses];
        int idx = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            order[idx++] = node;
            for (int neighbour : adj.get(node)) {
                indegree[neighbour]--;
                if (indegree[neighbour] == 0) queue.offer(neighbour);
            }
        }

        // If not all courses visited → cycle exists → impossible.
        return idx == numCourses ? order : new int[0];
    }

    public static void main(String[] args) {
        CourseSchedule sol = new CourseSchedule();

        // LC 207 tests
        System.out.println(sol.canFinish(2, new int[][]{{1, 0}}));           // true
        System.out.println(sol.canFinish(2, new int[][]{{1, 0}, {0, 1}}));   // false (cycle)

        // LC 210 tests
        System.out.println(Arrays.toString(sol.findOrder(4,
            new int[][]{{1, 0}, {2, 0}, {3, 1}, {3, 2}})));
        // [0, 1, 2, 3] or [0, 2, 1, 3] — any valid topo order

        System.out.println(Arrays.toString(sol.findOrder(2,
            new int[][]{{1, 0}, {0, 1}})));
        // [] — cycle, impossible
    }
}
