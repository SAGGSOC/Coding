import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Subtree Check in N-ary Tree.
 * Does tree `root` contain a subtree that is structurally identical to `sub`?
 *
 * Approach: canonical serialization (Merkle-style).
 *   - Serialize every subtree of `root` into a canonical string and collect them.
 *   - Canonical form of `sub` is then a membership test.
 *
 * Canonical form handles unordered children by sorting child serializations.
 * Time  : O(N log N) average due to sort per node.
 * Space : O(N)
 */
public class SubtreeCheck {

    public boolean isSubtree(NaryNode root, NaryNode sub) {
        if (sub == null) return true;
        if (root == null) return false;
        Set<String> seen = new HashSet<>();
        collectSerializations(root, seen);
        return seen.contains(canonical(sub));
    }

    private String collectSerializations(NaryNode node, Set<String> seen) {
        if (node == null) return "#";
        List<String> parts = new ArrayList<>(node.children.size());
        for (NaryNode c : node.children) parts.add(collectSerializations(c, seen));
        Collections.sort(parts); // unordered-children safe
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(node.val);
        for (String p : parts) sb.append(',').append(p);
        sb.append(')');
        String s = sb.toString();
        seen.add(s);
        return s;
    }

    private String canonical(NaryNode node) {
        if (node == null) return "#";
        List<String> parts = new ArrayList<>(node.children.size());
        for (NaryNode c : node.children) parts.add(canonical(c));
        Collections.sort(parts);
        StringBuilder sb = new StringBuilder();
        sb.append('(').append(node.val);
        for (String p : parts) sb.append(',').append(p);
        sb.append(')');
        return sb.toString();
    }
}
