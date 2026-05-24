/**
 * Maximum Depth of an N-ary Tree.
 * Pattern: Simple recursive depth computation.
 *   depth(null) = 0
 *   depth(node) = 1 + max(depth(child)) over all children
 *
 * Time  : O(N)   where N = total nodes
 * Space : O(H)   recursion stack, H = tree height
 */
public class MaximumDepth {

    public int maxDepth(NaryNode root) {
        if (root == null) return 0;
        int best = 0;
        for (NaryNode child : root.children) {
            best = Math.max(best, maxDepth(child));
        }
        return 1 + best;
    }

    // Google twist: compute min and max depth together (min depth = shortest
    // root-to-leaf path). Returns int[]{min, max}.
    public int[] minMaxDepth(NaryNode root) {
        if (root == null) return new int[]{0, 0};
        if (root.children.isEmpty()) return new int[]{1, 1};
        int mn = Integer.MAX_VALUE, mx = 0;
        for (NaryNode c : root.children) {
            int[] r = minMaxDepth(c);
            mn = Math.min(mn, r[0]);
            mx = Math.max(mx, r[1]);
        }
        return new int[]{1 + mn, 1 + mx};
    }
}
