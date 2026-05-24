import java.util.ArrayList;
import java.util.List;

/**
 * N-ary Tree Inorder Traversal.
 *
 * Note: "inorder" is not canonically defined for n-ary trees (it only has
 * a clean meaning on binary trees). The common convention used here:
 *
 *     inorder(node) = inorder(firstChild), visit(node), inorder(remainingChildren)
 *
 * For a leaf: visit the leaf.
 * For a binary tree (2 children), this matches the classic left-node-right order.
 *
 * An alternative convention — visit(node) between every pair of children —
 * is provided below as inorderInterleaved for completeness.
 */
public class InorderTraversal {

    // Convention 1: first child, then node, then remaining children.
    public List<Integer> inorder(NaryNode root) {
        List<Integer> out = new ArrayList<>();
        dfs(root, out);
        return out;
    }

    private void dfs(NaryNode node, List<Integer> out) {
        if (node == null) return;
        if (node.children.isEmpty()) {
            out.add(node.val);
            return;
        }
        // Visit first child's subtree.
        dfs(node.children.get(0), out);
        // Then the node itself.
        out.add(node.val);
        // Then the remaining children in order.
        for (int i = 1; i < node.children.size(); i++) {
            dfs(node.children.get(i), out);
        }
    }

    // Convention 2: interleave the node between every pair of child subtrees.
    // For k children, the node is emitted k-1 times. Useful for certain
    // expression-tree flattenings.
    public List<Integer> inorderInterleaved(NaryNode root) {
        List<Integer> out = new ArrayList<>();
        dfsInterleaved(root, out);
        return out;
    }

    private void dfsInterleaved(NaryNode node, List<Integer> out) {
        if (node == null) return;
        if (node.children.isEmpty()) {
            out.add(node.val);
            return;
        }
        for (int i = 0; i < node.children.size(); i++) {
            dfsInterleaved(node.children.get(i), out);
            if (i < node.children.size() - 1) out.add(node.val);
        }
    }
}
