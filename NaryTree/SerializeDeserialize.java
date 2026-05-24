import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Serialize & Deserialize N-ary Tree.
 * Format: preorder with child-count prefix.
 *   "val,childCount,<child1>,<child2>,..."
 *   empty tree -> ""
 *
 * Example:
 *          1
 *        / | \
 *       3  2  4
 *      / \
 *     5   6
 * -> "1,3,3,2,5,0,6,0,2,0,4,0"
 *
 * This is self-delimiting, avoids sentinels, and scales to arbitrary branching.
 */
public class SerializeDeserialize {

    public String serialize(NaryNode root) {
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        ser(root, sb);
        return sb.toString();
    }

    private void ser(NaryNode node, StringBuilder sb) {
        if (sb.length() > 0) sb.append(',');
        sb.append(node.val).append(',').append(node.children.size());
        for (NaryNode c : node.children) ser(c, sb);
    }

    public NaryNode deserialize(String data) {
        if (data == null || data.isEmpty()) return null;
        Deque<String> tokens = new ArrayDeque<>();
        for (String t : data.split(",")) tokens.offer(t);
        return des(tokens);
    }

    private NaryNode des(Deque<String> tokens) {
        int val = Integer.parseInt(tokens.poll());
        int k = Integer.parseInt(tokens.poll());
        NaryNode node = new NaryNode(val);
        for (int i = 0; i < k; i++) {
            node.children.add(des(tokens));
        }
        return node;
    }
}
