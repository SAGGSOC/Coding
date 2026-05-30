import java.util.*;

/**
 * LC 310 - Minimum Height Trees.
 *
 * Pattern: Topological Sort / Leaf Trimming (BFS from leaves inward).
 *
 * Key Insight:
 *   The root(s) that minimize tree height are the CENTER node(s) of the tree.
 *   A tree has at most 2 center nodes (the middle of the longest path / diameter).
 *
 * Approach: Repeatedly remove all leaf nodes (degree 1) layer by layer,
 *   like peeling an onion from the outside in. The last 1 or 2 nodes remaining
 *   are the answer.
 *
 * Why it works:
 *   - Leaves are the worst roots (max height).
 *   - Removing them exposes new leaves.
 *   - The last remaining nodes are the most "central" → minimum height.
 *
 * This is essentially topological sort on an undirected tree using degree
 * instead of indegree.
 *
 * Time  : O(n)
 * Space : O(n)
 */
public class MinHeightTrees {

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        // Edge case: single node.
        if (n == 1) return Collections.singletonList(0);

        // Build adjacency list and compute degree.
        List<Set<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new HashSet<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        // Collect initial leaves (degree == 1).
        Queue<Integer> leaves = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (adj.get(i).size() == 1) leaves.offer(i);
        }

        // Trim leaves layer by layer until 1 or 2 nodes remain.
        int remaining = n;
        while (remaining > 2) {
            int leafCount = leaves.size();
            remaining -= leafCount;

            Queue<Integer> newLeaves = new LinkedList<>();
            for (int i = 0; i < leafCount; i++) {
                int leaf = leaves.poll();
                // The leaf has exactly one neighbor.
                int neighbor = adj.get(leaf).iterator().next();
                adj.get(neighbor).remove(leaf);
                if (adj.get(neighbor).size() == 1) {
                    newLeaves.offer(neighbor);
                }
            }
            leaves = newLeaves;
        }

        // Remaining nodes are the roots of MHTs.
        return new ArrayList<>(leaves);
    }

    public static void main(String[] args) {
        MinHeightTrees sol = new MinHeightTrees();

        System.out.println(sol.findMinHeightTrees(4, new int[][]{{1,0},{1,2},{1,3}}));
        // [1]

        System.out.println(sol.findMinHeightTrees(6, new int[][]{{3,0},{3,1},{3,2},{3,4},{5,4}}));
        // [3, 4]

        System.out.println(sol.findMinHeightTrees(1, new int[][]{}));
        // [0]
    }
}
