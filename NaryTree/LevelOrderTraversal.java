import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * N-ary Tree Level Order Traversal (BFS).
 * Also includes the Google twist: zigzag level order.
 *
 * Time  : O(N)
 * Space : O(W)  where W = max width of the tree
 */
public class LevelOrderTraversal {

    public List<List<Integer>> levelOrder(NaryNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<NaryNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                NaryNode node = queue.poll();
                level.add(node.val);
                for (NaryNode child : node.children) queue.offer(child);
            }
            result.add(level);
        }
        return result;
    }

    // Google twist: zigzag level order. Alternate direction each level.
    // Use a deque and flip the insertion side instead of reversing a list.
    public List<List<Integer>> zigzagLevelOrder(NaryNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<NaryNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int size = queue.size();
            Deque<Integer> level = new ArrayDeque<>();
            for (int i = 0; i < size; i++) {
                NaryNode node = queue.poll();
                if (leftToRight) level.offerLast(node.val);
                else level.offerFirst(node.val);
                for (NaryNode child : node.children) queue.offer(child);
            }
            result.add(new ArrayList<>(level));
            leftToRight = !leftToRight;
        }
        return result;
    }
}
