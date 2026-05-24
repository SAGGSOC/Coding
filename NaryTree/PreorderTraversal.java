import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * N-ary Tree Preorder Traversal.
 * Visit order: node -> child1 -> child2 -> ... (DFS from left).
 *
 * Recursive : O(N) time, O(H) stack.
 * Iterative : O(N) time, O(W) stack where W is max branching width.
 *             Trick: push children in reverse order so the leftmost is popped first.
 */
public class PreorderTraversal {

    public List<Integer> preorderRecursive(NaryNode root) {
        List<Integer> out = new ArrayList<>();
        dfs(root, out);
        return out;
    }

    private void dfs(NaryNode node, List<Integer> out) {
        if (node == null) return;
        out.add(node.val);
        for (NaryNode c : node.children) dfs(c, out);
    }

    public List<Integer> preorderIterative(NaryNode root) {
        List<Integer> out = new ArrayList<>();
        if (root == null) return out;

        Deque<NaryNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            NaryNode node = stack.pop();
            out.add(node.val);
            // Push in reverse so the leftmost child is processed next.
            for (int i = node.children.size() - 1; i >= 0; i--) {
                stack.push(node.children.get(i));
            }
        }
        return out;
    }
}
