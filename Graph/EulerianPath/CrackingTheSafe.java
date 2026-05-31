import java.util.*;

/**
 * LC 753 - Cracking the Safe.
 *
 * Pattern: Eulerian Path on a De Bruijn Graph (Hierholzer's Algorithm).
 *
 * Model:
 *   - Nodes = all (n-1)-digit strings.
 *   - Edges = all n-digit strings. Edge from "AB" to "BC" = string "ABC".
 *   - Finding shortest string = Eulerian circuit (visit every edge once).
 *
 * Algorithm (Hierholzer's DFS):
 *   - Start from "00...0" (n-1 zeros).
 *   - DFS: try appending each digit 0..k-1. If the n-digit combo is unvisited,
 *     mark visited and recurse on the new (n-1) suffix.
 *   - Post-order: append the digit that led here.
 *   - Prepend the starting node at the end.
 *
 * Result length: k^n + (n-1). This is optimal — every n-digit combo appears exactly once.
 *
 * Time  : O(k^n)
 * Space : O(k^n)
 */
public class CrackingTheSafe {

    public String crackSafe(int n, int k) {
        // Edge case: n=1 → just list all digits.
        if (n == 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < k; i++) 
                sb.append(i);
            return sb.toString();
        }

        // Start node: (n-1) zeros.
        String start = String.join("", Collections.nCopies(n - 1, "0"));

        Set<String> visited = new HashSet<>();
        StringBuilder result = new StringBuilder();

        dfs(start, k, n, visited, result);

        // Prepend the starting node (first n-1 characters).
        result.append(start);
        return result.reverse().toString();
    }

    private void dfs(String node, int k, int n, Set<String> visited, StringBuilder result) {
        for (int i = 0; i < k; i++) {
            String edge = node + i; // this is the n-digit combination
            if (!visited.contains(edge)) {
                visited.add(edge);
                // Next node = last (n-1) chars of the edge.
                String nextNode = edge.substring(1);
                dfs(nextNode, k, n, visited, result);
                // Post-order: append the digit.
                result.append(i);
            }
        }
    }

    public static void main(String[] args) {
        CrackingTheSafe sol = new CrackingTheSafe();

        String res1 = sol.crackSafe(1, 2);
        System.out.println(res1); // "01" or "10"
        System.out.println("Length: " + res1.length()); // 2

        String res2 = sol.crackSafe(2, 2);
        System.out.println(res2); // "01100" or similar (length 5)
        System.out.println("Length: " + res2.length()); // 5 = 2^2 + 1

        String res3 = sol.crackSafe(2, 3);
        System.out.println(res3); // length = 3^2 + 1 = 10
        System.out.println("Length: " + res3.length()); // 10
    }
}
