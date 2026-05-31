import java.util.*;

/**
 * LC 269 - Alien Dictionary.
 *
 * Pattern: Topological Sort (Kahn's BFS / Indegree).
 *
 * ASCII-based approach:
 *   - Use c - 'a' as the node ID (0 to 25).
 *   - V = K = number of unique characters (only those are passed to topoSort).
 *   - adj is size K, indexed by a compact mapping.
 *
 * Actually simplest: just use K=26, pass uniqueCount separately for validation.
 * topoSort gets V=26, but we only seed indegree=0 nodes that are present.
 * Then check topo.size() == uniqueCount.
 *
 * Time  : O(C) where C = total characters across all words.
 * Space : O(1) — fixed 26 nodes.
 */
public class AlienDictionary {

    public String alienOrder(String[] words) {
        // Step 1: Track which characters are present (using ascii offset).
        boolean[] present = new boolean[26];
        int uniqueCount = 0;
        for (String word : words) {
            for (char c : word.toCharArray()) {
                if (!present[c - 'a']) {
                    present[c - 'a'] = true;
                    uniqueCount++;
                }
            }
        }

        // Step 2: Build adjacency list using c - 'a' as node ID.
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < 26; i++) adj.add(new ArrayList<>());

        for (int i = 0; i < words.length - 1; i++) {
            String w1 = words[i];
            String w2 = words[i + 1];

            if (w1.length() > w2.length() && w1.startsWith(w2)) return "";

            int minLen = Math.min(w1.length(), w2.length());
            for (int j = 0; j < minLen; j++) {
                if (w1.charAt(j) != w2.charAt(j)) {
                    adj.get(w1.charAt(j) - 'a').add(w2.charAt(j) - 'a');
                    break;
                }
            }
        }

        // Step 3: Topological sort on 26 nodes, only process present ones.
        List<Integer> topo = topoSort(26, adj, present);

        // Step 4: Validate — topo must contain exactly uniqueCount nodes.
        if (topo.size() != uniqueCount) return "";

        StringBuilder sb = new StringBuilder();
        for (int node : topo) sb.append((char) (node + 'a'));
        return sb.toString();
    }

    /**
     * Standard Kahn's topoSort.
     * V = 26 (fixed alphabet size).
     * Only nodes marked present[] are considered.
     */
    private List<Integer> topoSort(int V, List<List<Integer>> adj, boolean[] present) {
        int[] indegree = new int[V];
        for (int i = 0; i < V; i++) {
            for (int it : adj.get(i)) {
                indegree[it]++;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            if (present[i] && indegree[i] == 0) {
                q.offer(i);
            }
        }

        List<Integer> topo = new ArrayList<>();
        while (!q.isEmpty()) {
            int node = q.poll();
            topo.add(node);
            for (int it : adj.get(node)) {
                indegree[it]--;
                if (indegree[it] == 0) {
                    q.offer(it);
                }
            }
        }
        return topo;
    }

    public static void main(String[] args) {
        AlienDictionary sol = new AlienDictionary();

        System.out.println(sol.alienOrder(new String[]{"wrt", "wrf", "er", "ett", "rftt"}));
        // "wertf"

        System.out.println(sol.alienOrder(new String[]{"z", "x"}));
        // "zx"

        System.out.println(sol.alienOrder(new String[]{"z", "x", "z"}));
        // ""

        System.out.println(sol.alienOrder(new String[]{"abc", "ab"}));
        // ""
    }
}
