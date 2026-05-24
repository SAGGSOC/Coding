import java.util.*;

/**
 * LC 1258 - Synonymous Sentences.
 *
 * Uses integer-indexed DisjointSet (union by rank, path compression)
 * + backtracking to generate all synonym combinations.
 *
 * Steps:
 *   1. Assign each unique synonym word an integer index.
 *   2. Union synonym pairs using DisjointSet.
 *   3. Group words by their ultimate parent to form synonym groups (sorted).
 *   4. For each word in the sentence, get its sorted synonym group.
 *   5. Backtrack to generate all sentence combinations.
 */
public class SynonymousSentences {

    // ---- Disjoint Set (Union by Rank + Path Compression) ----
    static class DisjointSet {
        int[] parent;
        int[] rank;

        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }

        int findUlpParent(int node) {
            if (node == parent[node]) return node;
            parent[node] = findUlpParent(parent[node]);
            return parent[node];
        }

        void unionByRank(int u, int v) {
            int pu = findUlpParent(u);
            int pv = findUlpParent(v);
            if (pu == pv) return;
            if (rank[pu] < rank[pv]) parent[pu] = pv;
            else if (rank[pv] < rank[pu]) parent[pv] = pu;
            else { parent[pv] = pu; rank[pu]++; }
        }
    }

    public List<String> generateSentences(List<List<String>> synonyms, String text) {
        // Step 1: Map each unique word to an integer index.
        Map<String, Integer> wordToId = new HashMap<>();
        List<String> idToWord = new ArrayList<>();

        for (List<String> pair : synonyms) {
            for (String w : pair) {
                if (!wordToId.containsKey(w)) {
                    wordToId.put(w, idToWord.size());
                    idToWord.add(w);
                }
            }
        }

        // Step 2: Union synonym pairs.
        int n = idToWord.size();
        DisjointSet ds = new DisjointSet(n);
        for (List<String> pair : synonyms) {
            int u = wordToId.get(pair.get(0));
            int v = wordToId.get(pair.get(1));
            ds.unionByRank(u, v);
        }

        // Step 3: Group words by ultimate parent. Each group sorted lexicographically.
        Map<Integer, TreeSet<String>> groups = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int root = ds.findUlpParent(i);
            groups.computeIfAbsent(root, k -> new TreeSet<>()).add(idToWord.get(i));
        }

        // Step 4: For each word in the sentence, find its synonym options.
        String[] words = text.split(" ");
        List<List<String>> options = new ArrayList<>();
        for (String w : words) {
            if (wordToId.containsKey(w)) {
                int root = ds.findUlpParent(wordToId.get(w));
                options.add(new ArrayList<>(groups.get(root)));
            } else {
                options.add(Collections.singletonList(w));
            }
        }

        // Step 5: Backtrack to generate all combinations.
        List<String> result = new ArrayList<>();
        backtrack(options, 0, new StringBuilder(), result);
        Collections.sort(result);
        return result;
    }

    private void backtrack(List<List<String>> options, int idx,
                           StringBuilder current, List<String> result) {
        if (idx == options.size()) {
            result.add(current.toString().trim());
            return;
        }
        int lenBefore = current.length();
        for (String word : options.get(idx)) {
            if (idx > 0) current.append(' ');
            current.append(word);
            backtrack(options, idx + 1, current, result);
            current.setLength(lenBefore);
        }
    }

    public static void main(String[] args) {
        SynonymousSentences sol = new SynonymousSentences();

        List<List<String>> synonyms = Arrays.asList(
            Arrays.asList("happy", "joy"),
            Arrays.asList("sad", "sorrow"),
            Arrays.asList("joy", "cheerful")
        );
        String text = "I am happy today but was sad yesterday";

        List<String> res = sol.generateSentences(synonyms, text);
        for (String s : res) System.out.println(s);
    }
}
