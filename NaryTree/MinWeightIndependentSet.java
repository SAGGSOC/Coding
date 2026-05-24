import java.util.*;

/**
 * Minimum Weight Independent Set on an N-ary Tree.
 *
 * Given: A tree where each node has a weight.
 * Constraint: If you select a node, you cannot select any directly connected node
 *             (parent or children).
 * Goal: Find the minimum total weight of a valid selection (independent set).
 *
 * Pattern: Tree DP (Include / Exclude).
 *   include[node] = node.weight + sum(exclude[child])
 *   exclude[node] = sum(min(include[child], exclude[child]))
 *   answer = min(include[root], exclude[root])
 *
 * Time  : O(n)
 * Space : O(h) recursion stack
 */
public class MinWeightIndependentSet {

    /**
     * Returns the minimum weight of an independent set in the tree.
     * Each node's weight is stored in node.val.
     */
    public int minWeight(NaryNode root) {
        int[] result = dp(root);
        return Math.min(result[0], result[1]);
    }

    /**
     * Returns int[]{include, exclude}
     *   include = min sum if we pick this node
     *   exclude = min sum if we skip this node
     */
    private int[] dp(NaryNode node) {
        if (node == null) return new int[]{0, 0};

        int include = node.val; // pick this node
        int exclude = 0;        // skip this node

        for (NaryNode child : node.children) {
            int[] childResult = dp(child);
            include += childResult[1];                          // must skip children
            exclude += Math.min(childResult[0], childResult[1]); // children: pick or skip
        }

        return new int[]{include, exclude};
    }

    /**
     * Bonus: also returns WHICH nodes are selected (for interview follow-up).
     */
    public List<Integer> minWeightNodes(NaryNode root) {
        List<Integer> selected = new ArrayList<>();
        dpWithTracking(root, true, selected);
        dpWithTracking(root, false, selected); // try both, pick better
        // Actually need to compare — let's do it properly:
        selected.clear();
        boolean pickRoot = pickRootDecision(root);
        collect(root, pickRoot, selected);
        return selected;
    }

    private boolean pickRootDecision(NaryNode root) {
        int[] r = dp(root);
        return r[0] <= r[1]; // pick root if include <= exclude
    }

    private void collect(NaryNode node, boolean picked, List<Integer> selected) {
        if (node == null) return;
        if (picked) {
            selected.add(node.val);
            // Must skip all children
            for (NaryNode child : node.children) {
                collect(child, false, selected);
            }
        } else {
            // For each child, decide independently
            for (NaryNode child : node.children) {
                int[] cr = dp(child);
                boolean pickChild = cr[0] <= cr[1];
                collect(child, pickChild, selected);
            }
        }
    }

    private void dpWithTracking(NaryNode node, boolean pick, List<Integer> sel) {
        // helper stub — actual logic in collect()
    }

    // ========================================================
    // Main
    // ========================================================
    public static void main(String[] args) {
        MinWeightIndependentSet sol = new MinWeightIndependentSet();

        //          10
        //        /  |  \
        //       1    2    3
        //      / \       |
        //     4   5      6
        //
        // Option A: pick {1, 2, 6}     = 1+2+6 = 9
        // Option B: pick {4, 5, 3}     = 4+5+3 = 12
        // Option C: pick {10, 4, 5, 6} = 10+4+5+6 = 25 (invalid: 10 and its children)
        // Wait — if we pick 10, we can't pick 1,2,3. But we CAN pick 4,5,6 (grandchildren).
        // Option D: pick {10, 4, 5, 6} = 10+4+5+6 = 25
        // Option E: pick {1, 2, 6}     = 1+2+6 = 9  ← minimum!

        NaryNode root = new NaryNode(10);
        NaryNode n1 = new NaryNode(1);
        NaryNode n2 = new NaryNode(2);
        NaryNode n3 = new NaryNode(3);
        NaryNode n4 = new NaryNode(4);
        NaryNode n5 = new NaryNode(5);
        NaryNode n6 = new NaryNode(6);

        root.children.addAll(Arrays.asList(n1, n2, n3));
        n1.children.addAll(Arrays.asList(n4, n5));
        n3.children.add(n6);

        System.out.println("Min weight: " + sol.minWeight(root));
        // Let's trace:
        // dp(n4) = [4, 0]  (leaf)
        // dp(n5) = [5, 0]  (leaf)
        // dp(n6) = [6, 0]  (leaf)
        // dp(n1) = include: 1 + exc(n4) + exc(n5) = 1+0+0 = 1
        //          exclude: min(4,0) + min(5,0) = 0+0 = 0
        //        = [1, 0]
        // dp(n2) = [2, 0]  (leaf)
        // dp(n3) = include: 3 + exc(n6) = 3+0 = 3
        //          exclude: min(6,0) = 0
        //        = [3, 0]
        // dp(root) = include: 10 + exc(n1) + exc(n2) + exc(n3) = 10+0+0+0 = 10
        //            exclude: min(1,0) + min(2,0) + min(3,0) = 0+0+0 = 0
        //          = [10, 0]
        // answer = min(10, 0) = 0
        //
        // Wait — 0 means we can pick NOTHING and have sum 0!
        // That's technically valid (empty set is independent).
        // If the problem requires picking at least one node, we need to adjust.

        System.out.println("\n--- With mandatory selection ---");
        // If we must select at least one node:
        System.out.println("Min weight (must pick): " + sol.minWeightMustPick(root));

        // Simpler example where empty set isn't the answer:
        //     5
        //    / \
        //   3   7
        NaryNode r2 = new NaryNode(5);
        r2.children.add(new NaryNode(3));
        r2.children.add(new NaryNode(7));
        System.out.println("\nTree [5, 3, 7]:");
        System.out.println("Min weight (must pick): " + sol.minWeightMustPick(r2)); // 3
    }

    /**
     * Variant: must pick at least one node.
     * dp returns {include, exclude} but exclude can't be 0 at root level
     * unless some descendant was picked.
     *
     * Better approach: dp returns {include, excludeButSomeDescPicked}
     * For leaves: include = val, exclude = infinity (can't skip leaf AND have someone picked)
     * Actually simplest: answer = min over all nodes of (include[node])
     * since include[node] guarantees at least node itself is picked.
     *
     * Cleaner: run normal dp, if answer is 0 (empty set), find the single minimum-weight node.
     */
    public int minWeightMustPick(NaryNode root) {
        int[] r = dp(root);
        int ans = Math.min(r[0], r[1]);
        if (ans == 0) {
            // Empty set was optimal — find the lightest single node instead.
            return findMinNode(root);
        }
        return ans;
    }

    private int findMinNode(NaryNode node) {
        if (node == null) return Integer.MAX_VALUE;
        int min = node.val;
        for (NaryNode child : node.children) {
            min = Math.min(min, findMinNode(child));
        }
        return min;
    }
}
