import java.util.*;

/**
 * LC 1032 - Stream of Characters.
 *
 * Pattern: Reverse Trie (insert words reversed, check suffixes of stream).
 *
 * Problem: Given a stream of characters, after each character, check if any
 * word from the dictionary is a suffix of the stream so far.
 *
 * Approach:
 *   - Insert all words REVERSED into a Trie.
 *   - Maintain a buffer of the stream.
 *   - On each query, walk the Trie from the END of the buffer backwards.
 *   - If we hit an end-of-word node → some word is a suffix of the stream → return true.
 *
 * Time  : O(L) per query where L = max word length.
 * Space : O(total chars in words) for Trie + O(stream length) for buffer.
 */
public class StreamOfCharacters {

    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;
    }

    private TrieNode root = new TrieNode();
    private StringBuilder stream = new StringBuilder();
    private int maxLen = 0;

    public StreamOfCharacters(String[] words) {
        // Insert each word REVERSED.
        for (String w : words) {
            maxLen = Math.max(maxLen, w.length());
            TrieNode node = root;
            for (int i = w.length() - 1; i >= 0; i--) {
                int idx = w.charAt(i) - 'a';
                if (node.children[idx] == null) node.children[idx] = new TrieNode();
                node = node.children[idx];
            }
            node.isEnd = true;
        }
    }

    public boolean query(char letter) {
        stream.append(letter);
        TrieNode node = root;
        // Walk backwards from the end of the stream.
        for (int i = stream.length() - 1; i >= 0 && i >= stream.length() - maxLen; i--) {
            int idx = stream.charAt(i) - 'a';
            if (node.children[idx] == null) return false;
            node = node.children[idx];
            if (node.isEnd) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        StreamOfCharacters sol = new StreamOfCharacters(new String[]{"cd", "f", "kl"});
        char[] queries = {'a','b','c','d','e','f','g','h','i','j','k','l'};
        // Expected: [F, F, F, T, F, T, F, F, F, F, F, T]
        StringBuilder out = new StringBuilder("[");
        for (int i = 0; i < queries.length; i++) {
            if (i > 0) out.append(", ");
            out.append(sol.query(queries[i]) ? "T" : "F");
        }
        out.append("]");
        System.out.println(out);
    }
}
