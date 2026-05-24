import java.util.ArrayList;
import java.util.List;

/**
 * Shared N-ary tree node used by every problem in this folder.
 * Children are stored in insertion order. Null children are not allowed.
 *
 * Supports both integer-keyed nodes (for algo problems) and
 * string-keyed nodes (for org trees, file systems, etc.)
 */
public class NaryNode {
    public int val;
    public String name;
    public List<NaryNode> children;

    public NaryNode(int val) {
        this.val = val;
        this.name = null;
        this.children = new ArrayList<>();
    }

    public NaryNode(String name) {
        this.val = 0;
        this.name = name;
        this.children = new ArrayList<>();
    }

    public NaryNode(int val, List<NaryNode> children) {
        this.val = val;
        this.name = null;
        this.children = children != null ? children : new ArrayList<>();
    }
}
