/**
 * N-ary Tree <-> Binary Tree (Left-Child / Right-Sibling encoding).
 *
 * encode:  for each n-ary node X with children c1, c2, c3...
 *          binary.left   = encode(c1)
 *          binary.right  = sibling link (c2 -> c3 -> ...)
 *
 * decode:  reverse — walk the left spine collecting children from the right chain.
 *
 * This is a classic lossless conversion used in compilers and filesystem trees.
 */
public class NaryToBinary {

    // Minimal binary node used only for this conversion.
    public static class BNode {
        public int val;
        public BNode left, right;
        public BNode(int v) { this.val = v; }
    }

    public BNode encode(NaryNode root) {
        if (root == null) return null;
        BNode b = new BNode(root.val);
        if (root.children.isEmpty()) return b;

        // First child becomes left.
        b.left = encode(root.children.get(0));
        // Remaining children thread along the right chain of b.left.
        BNode cursor = b.left;
        for (int i = 1; i < root.children.size(); i++) {
            cursor.right = encode(root.children.get(i));
            cursor = cursor.right;
        }
        return b;
    }

    public NaryNode decode(BNode root) {
        if (root == null) return null;
        NaryNode n = new NaryNode(root.val);
        BNode child = root.left;
        while (child != null) {
            n.children.add(decode(child));
            child = child.right; // siblings
        }
        return n;
    }
}
