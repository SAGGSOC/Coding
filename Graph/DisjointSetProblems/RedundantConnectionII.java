import java.util.*;

/**
 * LC 685 - Redundant Connection II (Directed Graph).
 *
 * Pattern: DisjointSet (Union-Find) + Case Analysis.
 *
 * Cases:
 *   1. A node has 2 parents, no cycle → remove the later edge to that node.
 *   2. A cycle exists, no 2-parent node → remove the cycle-causing edge.
 *   3. A node has 2 parents AND a cycle → remove the edge that's both a
 *      duplicate parent and part of the cycle (cand1).
 *
 * Uses the shared DisjointSet class (unionByRank + findUlpParent).
 *
 * Time  : O(n * α(n)) ≈ O(n)
 * Space : O(n)
 */
public class RedundantConnectionII {

    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;
        int[] parentArr = new int[n + 1]; // parentArr[v] = u means edge u→v seen

        int[] cand1 = null, cand2 = null;

        // Step 1: Find if any node has 2 parents.
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            if (parentArr[v] != 0) {
                // v already has a parent → two candidates.
                cand1 = new int[]{parentArr[v], v}; // first edge to v
                cand2 = new int[]{u, v};             // second (later) edge to v
                // "Remove" cand2 by zeroing it out.
                edge[0] = 0;
                edge[1] = 0;
            } else {
                parentArr[v] = u;
            }
        }

        // Step 2: Use DisjointSet to detect cycle (skipping removed cand2).
        DisjointSet ds = new DisjointSet(n);

        for (int[] edge : edges) {
            if (edge[0] == 0 && edge[1] == 0) continue; // skipped cand2

            int u = edge[0], v = edge[1];
            int pu = ds.findUlpParent(u);
            int pv = ds.findUlpParent(v);

            if (pu == pv) {
                // Cycle detected!
                // If there was a 2-parent node → cand1 caused the cycle.
                // If no 2-parent node → this edge itself is the answer.
                return cand1 != null ? cand1 : edge;
            }
            ds.unionByRank(u, v);
        }

        // Step 3: No cycle found after removing cand2 → cand2 was the problem.
        return cand2;
    }

    public static void main(String[] args) {
        RedundantConnectionII sol = new RedundantConnectionII();

        // Example 1: node 3 has 2 parents (1→3 and 2→3). No cycle.
        System.out.println(Arrays.toString(sol.findRedundantDirectedConnection(
            new int[][]{{1,2},{1,3},{2,3}})));
        // [2, 3]

        // Example 2: cycle (1→2→3→4→1), no 2-parent node.
        System.out.println(Arrays.toString(sol.findRedundantDirectedConnection(
            new int[][]{{1,2},{2,3},{3,4},{4,1},{1,5}})));
        // [4, 1]

        // Example 3: 2 parents + cycle.
        System.out.println(Arrays.toString(sol.findRedundantDirectedConnection(
            new int[][]{{2,1},{3,1},{4,2},{1,4}})));
        // [2, 1]
    }
}
