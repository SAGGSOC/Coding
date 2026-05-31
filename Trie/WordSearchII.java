import java.util.*;

/**
 * LC 212 - Word Search II.
 *
 * Pattern: Trie + DFS on grid (backtracking).
 *
 * Approach:
 *   1. Insert all words into a Trie.
 *   2. DFS from every cell in the grid. At each cell, follow the Trie.
 *   3. If we reach a Trie node that marks end-of-word, add it to results.
 *   4. Prune: if current Trie node has no children path, stop DFS early.
 *
 * Time  : O(m * n * 4^L) worst case, but Trie pruning makes it much faster.
 * Space : O(total chars in words) for Trie.
 */
public class WordSearchII {

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        String word = null; // store full word at leaf for easy collection
    }

    public List<String> findWords(char[][] board, String[] words) {
        // Build Trie.
        TrieNode root = new TrieNode();
        for (String w : words) {
            TrieNode node = root;
            for (char c : w.toCharArray()) {
                int idx = c - 'a';
                if (node.children[idx] == null) node.children[idx] = new TrieNode();
                node = node.children[idx];
            }
            node.word = w;
        }

        List<String> result = new ArrayList<>();
        int m = board.length, n = board[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dfs(board, i, j, root, result);
            }
        }
        return result;
    }

    private void dfs(char[][] board, int i, int j, TrieNode node, List<String> result) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length) return;
        char c = board[i][j];
        if (c == '#' || node.children[c - 'a'] == null) return;

        node = node.children[c - 'a'];
        if (node.word != null) {
            result.add(node.word);
            node.word = null; // avoid duplicates
        }

        board[i][j] = '#'; // mark visited
        dfs(board, i + 1, j, node, result);
        dfs(board, i - 1, j, node, result);
        dfs(board, i, j + 1, node, result);
        dfs(board, i, j - 1, node, result);
        board[i][j] = c; // restore
    }

    public static void main(String[] args) {
        WordSearchII sol = new WordSearchII();
        char[][] board = {
            {'o','a','a','n'},
            {'e','t','a','e'},
            {'i','h','k','r'},
            {'i','f','l','v'}
        };
        System.out.println(sol.findWords(board, new String[]{"oath","pea","eat","rain"}));
        // [oath, eat]
    }
}
