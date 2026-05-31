import java.util.*;

/**
 * LC 211 - Design Add and Search Words Data Structure.
 *
 * Pattern: Trie + DFS for wildcard '.' matching.
 *
 * addWord: standard Trie insert.
 * search: if char is '.', try ALL 26 children (DFS branching).
 *         if char is a letter, follow that single child.
 *
 * Time  : addWord O(L), search O(26^dots * L) worst case.
 * Space : O(total chars inserted)
 */
public class AddSearchWord {

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;
    }

    private TrieNode root = new TrieNode();

    public void addWord(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) node.children[idx] = new TrieNode();
            node = node.children[idx];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        return dfs(word, 0, root);
    }

    private boolean dfs(String word, int i, TrieNode node) {
        if (node == null) return false;
        if (i == word.length()) return node.isEnd;

        char c = word.charAt(i);
        if (c == '.') {
            // Try all 26 children.
            for (TrieNode child : node.children) {
                if (dfs(word, i + 1, child)) return true;
            }
            return false;
        } else {
            return dfs(word, i + 1, node.children[c - 'a']);
        }
    }

    public static void main(String[] args) {
        AddSearchWord dict = new AddSearchWord();
        dict.addWord("bad");
        dict.addWord("dad");
        dict.addWord("mad");
        System.out.println(dict.search("pad"));  // false
        System.out.println(dict.search("bad"));  // true
        System.out.println(dict.search(".ad"));  // true
        System.out.println(dict.search("b.."));  // true
    }
}
