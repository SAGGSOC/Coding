import java.util.*;

/**
 * Org Tree — Find direct reportees of a given manager.
 *
 * Uses NaryTreeBuilder (separate class) to construct the tree from pairs.
 * This class only contains the business logic (queries on the tree).
 */
public class OrgTree {

    // Direct report count = children.size() of the found node.
    public int getDirectReportCount(NaryNode root, String managerName) {
        NaryNode manager = NaryTreeBuilder.findNode(root, managerName);
        if (manager == null) return -1;
        return manager.children.size();
    }

    // Direct report names.
    public List<String> getDirectReportNames(NaryNode root, String managerName) {
        NaryNode manager = NaryTreeBuilder.findNode(root, managerName);
        if (manager == null) return Collections.emptyList();
        List<String> names = new ArrayList<>();
        for (NaryNode child : manager.children) names.add(child.name);
        return names;
    }

    // Total (indirect) reports under a manager.
    public int getTotalReportCount(NaryNode root, String managerName) {
        NaryNode manager = NaryTreeBuilder.findNode(root, managerName);
        if (manager == null) return -1;
        return NaryTreeBuilder.subtreeSize(manager) - 1;
    }

    // ========================================================
    // Lowest Common Ancestor (Common Manager of two employees)
    // ========================================================

    /**
     * Finds the lowest common manager of two employees in the org tree.
     * This is the LCA problem generalized to N-ary trees.
     *
     * Logic:
     *   - If current node is null, return null.
     *   - If current node matches either name, return it.
     *   - Recurse into all children. Count how many subtrees returned non-null.
     *   - If 2+ children returned non-null → current node is the LCA.
     *   - If exactly 1 returned non-null → propagate that result up.
     *   - If 0 → return null.
     *
     * Time  : O(n)
     * Space : O(h) recursion stack
     */
    public String findCommonManager(NaryNode root, String emp1, String emp2) {
        NaryNode lca = lca(root, emp1, emp2);
        return lca != null ? lca.name : null;
    }

    private NaryNode lca(NaryNode node, String p, String q) {
        if (node == null) return null;
        if (node.name.equals(p) || node.name.equals(q)) return node;

        NaryNode found = null;
        int count = 0;

        for (NaryNode child : node.children) {
            NaryNode result = lca(child, p, q);
            if (result != null) {
                count++;
                found = result;
            }
            if (count == 2) return node; // both found in different subtrees → node is LCA
        }

        return found; // 0 or 1 match found below
    }

    public static void main(String[] args) {
        OrgTree sol = new OrgTree();

        //          CEO
        //        /  |  \
        //      VP1  VP2  VP3
        //      / \       |
        //    M1   M2     M3
        //    |
        //    E1
        List<List<String>> pairs = Arrays.asList(
            Arrays.asList("VP1", "CEO"),
            Arrays.asList("VP2", "CEO"),
            Arrays.asList("VP3", "CEO"),
            Arrays.asList("M1", "VP1"),
            Arrays.asList("M2", "VP1"),
            Arrays.asList("M3", "VP3"),
            Arrays.asList("E1", "M1")
        );

        // Step 1: Build tree (separate concern).
        NaryNode root = NaryTreeBuilder.buildTree(pairs);
        System.out.println("Root: " + root.name);

        // Step 2: Query the tree.
        System.out.println("CEO direct reports: " + sol.getDirectReportCount(root, "CEO"));   // 3
        System.out.println("VP1 direct reports: " + sol.getDirectReportCount(root, "VP1"));   // 2
        System.out.println("VP2 direct reports: " + sol.getDirectReportCount(root, "VP2"));   // 0
        System.out.println("M1 direct reports:  " + sol.getDirectReportCount(root, "M1"));    // 1
        System.out.println("E1 direct reports:  " + sol.getDirectReportCount(root, "E1"));    // 0
        System.out.println("VP1 direct names:   " + sol.getDirectReportNames(root, "VP1"));   // [M1, M2]
        System.out.println("CEO total reports:  " + sol.getTotalReportCount(root, "CEO"));    // 7
        System.out.println("VP1 total reports:  " + sol.getTotalReportCount(root, "VP1"));    // 3

        // Step 3: Find common manager (LCA).
        System.out.println("\n--- Common Manager ---");
        System.out.println("LCA(M1, M2):  " + sol.findCommonManager(root, "M1", "M2"));   // VP1
        System.out.println("LCA(E1, M2):  " + sol.findCommonManager(root, "E1", "M2"));   // VP1
        System.out.println("LCA(E1, M3):  " + sol.findCommonManager(root, "E1", "M3"));   // CEO
        System.out.println("LCA(VP1, E1): " + sol.findCommonManager(root, "VP1", "E1"));  // VP1
        System.out.println("LCA(VP2, VP3):" + sol.findCommonManager(root, "VP2", "VP3")); // CEO
    }
}
