import java.util.*;

/**
 * LC 1268 - Search Suggestions System (Autocomplete).
 *
 * Pattern: Trie + DFS to collect lexicographically smallest matches.
 *
 * Approach:
 *   1. Insert all products into a Trie.
 *   2. For each prefix (typed so far), walk the Trie to the prefix node.
 *   3. DFS from that node to collect up to 3 words (lexicographic order is
 *      guaranteed by visiting children a-z in order).
 *
 * Time  : O(M) to build trie (M = total chars in products) + O(n * 3) per query prefix.
 * Space : O(M) for the trie.
 */
public class SearchSuggestions {

    // Trie Node
    static class TrieNode {
        TrieNode[] children = new TrieNode[26];
        boolean isEnd = false;
    }

    private TrieNode root = new TrieNode();

    private void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (node.children[idx] == null) node.children[idx] = new TrieNode();
            node = node.children[idx];
        }
        node.isEnd = true;
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        // Insert all products into trie.
        for (String p : products) insert(p);

        List<List<String>> result = new ArrayList<>();
        TrieNode node = root;

        for (int i = 0; i < searchWord.length(); i++) {
            int idx = searchWord.charAt(i) - 'a';
            if (node != null) node = node.children[idx];

            List<String> suggestions = new ArrayList<>();
            if (node != null) {
                // DFS from this node to find up to 3 words.
                dfs(node, new StringBuilder(searchWord.substring(0, i + 1)), suggestions);
            }
            result.add(suggestions);
        }
        return result;
    }

    // DFS collecting up to 3 words in lexicographic order.
    private void dfs(TrieNode node, StringBuilder prefix, List<String> suggestions) {
        if (suggestions.size() == 3) return; // only need top 3
        if (node == null) return;
        if (node.isEnd) suggestions.add(prefix.toString());

        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                prefix.append((char) ('a' + i));
                dfs(node.children[i], prefix, suggestions);
                prefix.deleteCharAt(prefix.length() - 1); // backtrack
                if (suggestions.size() == 3) return;
            }
        }
    }

    public static void main(String[] args) {
        SearchSuggestions sol = new SearchSuggestions();

        String[] products = {"mobile", "mouse", "moneypot", "monitor", "mousepad"};
        String searchWord = "mouse";

        List<List<String>> res = sol.suggestedProducts(products, searchWord);
        for (int i = 0; i < res.size(); i++) {
            System.out.println("\"" + searchWord.substring(0, i + 1) + "\" → " + res.get(i));
        }
        // "m"     → [mobile, moneypot, monitor]
        // "mo"    → [mobile, moneypot, monitor]
        // "mou"   → [mouse, mousepad]
        // "mous"  → [mouse, mousepad]
        // "mouse" → [mouse, mousepad]
    }
}
