import java.util.ArrayList;
import java.util.List;

/**
 * Find the path from root to a target node.
 * Pattern: DFS with backtracking.
 *
 * Returns the list of node values from root down to target, or empty if not found.
 * For multiple targets (Google twist), see findAllPaths.
 */
public class FindPath {

    public List<Integer> findPath(NaryNode root, int target) {
        List<Integer> path = new ArrayList<>();
        dfs(root, target, path);
        return path;
    }

    private boolean dfs(NaryNode node, int target, List<Integer> path) {
        if (node == null) return false;
        path.add(node.val);
        if (node.val == target) return true;
        for (NaryNode c : node.children) {
            if (dfs(c, target, path)) return true;
        }
        path.remove(path.size() - 1); // backtrack
        return false;
    }

    // Google twist: all root-to-target paths for any target in the set.
    public List<List<Integer>> findAllPaths(NaryNode root, java.util.Set<Integer> targets) {
        List<List<Integer>> out = new ArrayList<>();
        dfsAll(root, targets, new ArrayList<>(), out);
        return out;
    }

    private void dfsAll(NaryNode node, java.util.Set<Integer> targets,
                        List<Integer> path, List<List<Integer>> out) {
        if (node == null) return;
        path.add(node.val);
        if (targets.contains(node.val)) out.add(new ArrayList<>(path));
        for (NaryNode c : node.children) dfsAll(c, targets, path, out);
        path.remove(path.size() - 1);
    }
}
