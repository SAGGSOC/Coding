/**
 * Diameter of an N-ary Tree (longest path between any two nodes, measured in edges).
 * Pattern: postorder DFS. At each node, combine the top-2 child heights.
 *
 * For each node:
 *   - compute height = 1 + max(child height)
 *   - candidate diameter = sum of top-2 child heights
 *
 * Time  : O(N)
 * Space : O(H) recursion
 */
public class Diameter {

    private int best = 0;

    public int diameter(NaryNode root) {
        best = 0;
        height(root);
        return best;
    }

    // Returns height in edges (null -> 0, leaf -> 0, single edge -> 1).
    private int height(NaryNode node) {
        if (node == null) return 0;
        int h1 = 0, h2 = 0; // top two child heights measured in edges
        for (NaryNode c : node.children) {
            int h = height(c) + 1; // +1 for the edge to child
            if (h > h1) { h2 = h1; h1 = h; }
            else if (h > h2) { h2 = h; }
        }
        best = Math.max(best, h1 + h2);
        return h1;
    }
}
