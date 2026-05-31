import java.util.*;

/**
 * LC 421 - Maximum XOR of Two Numbers in an Array.
 *
 * Pattern: Binary Trie (bit-by-bit greedy).
 *
 * Approach:
 *   1. Insert all numbers into a binary trie (32 bits, MSB first).
 *   2. For each number, traverse the trie greedily picking the OPPOSITE bit
 *      at each level to maximize XOR.
 *
 * Why opposite bit? XOR is 1 when bits differ. So at each bit position,
 * we want to go the opposite direction to get a 1 in the XOR result.
 *
 * Time  : O(32 * n) = O(n)
 * Space : O(32 * n) for the trie
 */
public class MaxXORTwoNumbers {

    static class TrieNode {
        TrieNode[] children = new TrieNode[2]; // 0 and 1
    }

    public int findMaximumXOR(int[] nums) {
        TrieNode root = new TrieNode();

        // Insert all numbers (32 bits, MSB first).
        for (int num : nums) {
            TrieNode node = root;
            for (int i = 31; i >= 0; i--) {
                int bit = (num >> i) & 1;
                if (node.children[bit] == null) node.children[bit] = new TrieNode();
                node = node.children[bit];
            }
        }

        // For each number, find max XOR by greedily picking opposite bits.
        int maxXor = 0;
        for (int num : nums) {
            TrieNode node = root;
            int xor = 0;
            for (int i = 31; i >= 0; i--) {
                int bit = (num >> i) & 1;
                int opposite = 1 - bit;
                if (node.children[opposite] != null) {
                    xor |= (1 << i); // this bit contributes 1 to XOR
                    node = node.children[opposite];
                } else {
                    node = node.children[bit];
                }
            }
            maxXor = Math.max(maxXor, xor);
        }
        return maxXor;
    }

    public static void main(String[] args) {
        MaxXORTwoNumbers sol = new MaxXORTwoNumbers();
        System.out.println(sol.findMaximumXOR(new int[]{3, 10, 5, 25, 2, 8})); // 28
        System.out.println(sol.findMaximumXOR(new int[]{14, 70, 53, 83, 49, 91, 36, 80, 92, 51, 66, 70})); // 127
    }
}
