import java.util.List;
import java.util.function.BiFunction;

/**
 * Replace Node Values Based on Rules.
 * Pattern: DFS traversal + functional mapping.
 *
 * Two modes are useful in interviews:
 *   - Postorder transform: rule sees current value + already-transformed children.
 *     Example: node.val = sum of children values.
 *   - Preorder transform: rule sees current value + accumulator from the parent.
 *     Example: rank-encoding by depth.
 *
 * The BiFunction is the "rule" you inject — this is the composability point.
 */
public class ReplaceNodeValues {

    // Postorder: rule = f(currentVal, childValuesAfterTransform) -> newVal.
    public void transformPostorder(NaryNode root,
                                   BiFunction<Integer, List<Integer>, Integer> rule) {
        if (root == null) return;
        for (NaryNode c : root.children) transformPostorder(c, rule);
        java.util.List<Integer> childVals = new java.util.ArrayList<>();
        for (NaryNode c : root.children) childVals.add(c.val);
        root.val = rule.apply(root.val, childVals);
    }

    // Preorder: rule = f(currentVal, parentAccumulator) -> newVal.
    // The new value is also the accumulator passed to children.
    public void transformPreorder(NaryNode root,
                                  BiFunction<Integer, Integer, Integer> rule,
                                  int parentAccumulator) {
        if (root == null) return;
        root.val = rule.apply(root.val, parentAccumulator);
        for (NaryNode c : root.children) {
            transformPreorder(c, rule, root.val);
        }
    }

    // Convenience: replace each node with the sum of its subtree.
    public int subtreeSumReplace(NaryNode root) {
        if (root == null) return 0;
        int sum = root.val;
        for (NaryNode c : root.children) sum += subtreeSumReplace(c);
        root.val = sum;
        return sum;
    }
}
