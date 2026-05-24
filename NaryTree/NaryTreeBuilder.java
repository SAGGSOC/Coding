import java.util.*;

/**
 * Generic N-ary Tree construction from flat parent-child pairs.
 *
 * Reusable for any problem that gives input as [child, parent] pairs
 * and needs a tree built from it (org trees, file systems, category hierarchies, etc.)
 *
 * Uses the shared NaryNode class.
 */
public class NaryTreeBuilder {

    /**
     * Builds an N-ary tree from [child, parent] string pairs.
     * Returns the root node (the node that never appears as a child).
     *
     * Time  : O(n)
     * Space : O(n)
     */
    public static NaryNode buildTree(List<List<String>> pairs) {
        Map<String, NaryNode> nodeMap = new HashMap<>();
        Set<String> hasParent = new HashSet<>();

        for (List<String> pair : pairs) {
            String childName = pair.get(0);
            String parentName = pair.get(1);

            nodeMap.putIfAbsent(childName, new NaryNode(childName));
            nodeMap.putIfAbsent(parentName, new NaryNode(parentName));

            NaryNode parent = nodeMap.get(parentName);
            NaryNode child = nodeMap.get(childName);
            parent.children.add(child);

            hasParent.add(childName);
        }

        // Root = node that never appears as a child.
        for (String name : nodeMap.keySet()) {
            if (!hasParent.contains(name)) return nodeMap.get(name);
        }
        return null;
    }

    /**
     * DFS to find a node by name. Returns null if not found.
     */
    public static NaryNode findNode(NaryNode root, String name) {
        if (root == null) return null;
        if (root.name.equals(name)) return root;
        for (NaryNode child : root.children) {
            NaryNode found = findNode(child, name);
            if (found != null) return found;
        }
        return null;
    }

    /**
     * Returns the subtree size rooted at the given node (including itself).
     */
    public static int subtreeSize(NaryNode node) {
        if (node == null) return 0;
        int count = 1;
        for (NaryNode child : node.children) count += subtreeSize(child);
        return count;
    }
}
