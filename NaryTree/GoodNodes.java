/**
 * Good Nodes in an N-ary Tree.
 * A node X is "good" if no node on the path from root to X has a value greater than X.
 * Pattern: DFS carrying the running max.
 *
 * Time  : O(N)
 * Space : O(H)
 */
public class GoodNodes {

    public int goodNodes(NaryNode root) {
        if (root == null) return 0;
        return dfs(root, Integer.MIN_VALUE);
    }

    private int dfs(NaryNode node, int maxSoFar) {
        if (node == null) return 0;
        int count = 0;
        if (node.val >= maxSoFar) count = 1;
        int newMax = Math.max(maxSoFar, node.val);
        for (NaryNode c : node.children) count += dfs(c, newMax);
        return count;
    }

    // Google twist: conditional "good" using a threshold.
    // Counts nodes whose val >= max(running max, threshold).
    public int goodNodesWithThreshold(NaryNode root, int threshold) {
        if (root == null) return 0;
        return dfsThreshold(root, Integer.MIN_VALUE, threshold);
    }

    private int dfsThreshold(NaryNode node, int maxSoFar, int threshold) {
        if (node == null) return 0;
        int bar = Math.max(maxSoFar, threshold);
        int count = node.val >= bar ? 1 : 0;
        int newMax = Math.max(maxSoFar, node.val);
        for (NaryNode c : node.children) count += dfsThreshold(c, newMax, threshold);
        return count;
    }
}
