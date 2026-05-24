import java.util.HashMap;
import java.util.Map;

/**
 * Clone an N-ary Tree.
 * Tree-only: pure recursion is enough (no shared nodes).
 * Graph/DAG (Google twist): use an identity map to prevent re-cloning
 * of shared subgraphs and to handle cycles safely.
 */
public class CloneNaryTree {

    // Straight tree clone.
    public NaryNode clone(NaryNode root) {
        if (root == null) return null;
        NaryNode copy = new NaryNode(root.val);
        for (NaryNode c : root.children) copy.children.add(clone(c));
        return copy;
    }

    // Safe for DAGs / cycles. Uses identity-based map.
    public NaryNode cloneGraph(NaryNode root) {
        if (root == null) return null;
        Map<NaryNode, NaryNode> seen = new HashMap<>();
        return cloneGraphDfs(root, seen);
    }

    private NaryNode cloneGraphDfs(NaryNode node, Map<NaryNode, NaryNode> seen) {
        NaryNode cached = seen.get(node);
        if (cached != null) return cached;
        NaryNode copy = new NaryNode(node.val);
        seen.put(node, copy);
        for (NaryNode c : node.children) copy.children.add(cloneGraphDfs(c, seen));
        return copy;
    }
}
