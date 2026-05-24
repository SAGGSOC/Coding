import java.util.*;

class TreeNode {
    public int data;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int data) {
        this.data = data;
        left = null;
        right = null;
    }

    static TreeNode createTree(Scanner sc) {
        System.out.println("Enter the value (-1 for null):");
        int x = sc.nextInt();
        if (x == -1)
            return null;
        TreeNode root = new TreeNode(x);
        System.out.println("Enter the left child of " + x);
        root.left = createTree(sc);
        System.out.println("Enter the right child of " + x);
        root.right = createTree(sc);
        return root;
    }
}

class Tree {

    public void inorder(TreeNode node) {
        if (node == null)
            return;
        inorder(node.left);
        System.out.print(node.data + " ");
        inorder(node.right);
    }

    public List<List<Integer>> zigzagTraversal(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (leftToRight)
                    level.add(node.data);
                else
                    level.add(0, node.data);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }
            result.add(level);
            leftToRight = !leftToRight;
        }
        return result;
    }

    public void preorder(TreeNode node) {
        if(node == null) {
            return;
        }
        System.out.print(node.data + " ");
        preorder(node.left);
        preorder(node.right);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TreeNode root = TreeNode.createTree(sc);
        Tree tree = new Tree();
        System.out.println("Inorder traversal:");
        tree.inorder(root);
        tree.preorder(root);
        System.out.println("\nZigZag traversal:");
        System.out.println(tree.zigzagTraversal(root));
        sc.close();
    }
}
