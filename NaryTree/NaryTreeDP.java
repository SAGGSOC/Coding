/**
 * N-ary Tree DP patterns.
 * Each method demonstrates a different "combine child states at the parent" template.
 *
 * 1) maxPathSum        — best root-to-leaf path sum (positive or negative weights).
 * 2) maxAnyPathSum     — best path between any two nodes through an optional LCA
 *                        (generalization of LC 124 for n-ary).
 * 3) includeExclude    — House Robber on an n-ary tree: pick non-adjacent nodes
 *                        to maximize sum. Returns (include, exclude) pair per node.
 */
public class NaryTreeDP {

    // 1) Max root-to-leaf path sum.
    public int maxPathSum(NaryNode root) {
        if (root == null) return 0;
        if (root.children.isEmpty()) return root.val;
        int best = Integer.MIN_VALUE;
        for (NaryNode c : root.children) best = Math.max(best, maxPathSum(c));
        return root.val + best;
    }

    // 2) Max path sum between any two nodes. Tracks best top-2 downward paths at each node.
    private int ans;
    public int maxAnyPathSum(NaryNode root) {
        ans = Integer.MIN_VALUE;
        downMax(root);
        return ans;
    }

    private int downMax(NaryNode node) {
        if (node == null) return 0;
        int top1 = 0, top2 = 0; // best two non-negative child contributions
        for (NaryNode c : node.children) {
            int v = Math.max(0, downMax(c));
            if (v > top1) { top2 = top1; top1 = v; }
            else if (v > top2) { top2 = v; }
        }
        ans = Math.max(ans, node.val + top1 + top2);
        return node.val + top1;
    }

    // 3) House Robber on n-ary tree. Returns {include, exclude} sums rooted at node.
    public int robTree(NaryNode root) {
        int[] r = robDp(root);
        return Math.max(r[0], r[1]);
    }

    private int[] robDp(NaryNode node) {
        if (node == null) return new int[]{0, 0};
        int inc = node.val;
        int exc = 0;
        for (NaryNode c : node.children) {
            int[] cr = robDp(c);
            inc += cr[1];                 // include node -> exclude children
            exc += Math.max(cr[0], cr[1]);// exclude node -> best of each child
        }
        return new int[]{inc, exc};
    }
}
