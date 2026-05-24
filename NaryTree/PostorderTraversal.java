import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;

/**
 * N-ary Tree Postorder Traversal.
 * Pattern: DFS — recursive and iterative variants.
 *
 * Recursive:   obvious, O(N) time, O(H) stack.
 * Iterative:   single-stack "reversed preorder" trick —
 *              push root, pop and prepend, push children in natural order.
 *              The final list is postorder.
 */
public class PostorderTraversal {

    public List<Integer> postorderRecursive(NaryNode root) {
        List<Integer> out = new ArrayList<>();
        dfs(root, out);
        return out;
    }

    private void dfs(NaryNode node, List<Integer> out) {
        if (node == null) return;
        for (NaryNode c : node.children) dfs(c, out);
        out.add(node.val);
    }

    // Iterative: push root, on each pop prepend value and push children left-to-right.
    public List<Integer> postorderIterative(NaryNode root) {
        LinkedListResult out = new LinkedListResult();
        if (root == null) return out.list;

        Deque<NaryNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            NaryNode node = stack.pop();
            out.list.add(0, node.val); // prepend
            for (NaryNode c : node.children) stack.push(c);
        }
        return out.list;
    }

    private static class LinkedListResult {
        List<Integer> list = new ArrayList<>();
    }

    // Google twist: bottom-up aggregation (sum of subtree values per node).
    // Returns the subtree sum at each node in postorder.
    public List<int[]> postorderWithSubtreeSum(NaryNode root) {
        List<int[]> out = new ArrayList<>();
        aggregate(root, out);
        return out;
    }

    private int aggregate(NaryNode node, List<int[]> out) {
        if (node == null) return 0;
        int sum = node.val;
        for (NaryNode c : node.children) sum += aggregate(c, out);
        out.add(new int[]{node.val, sum});
        return sum;
    }
}
