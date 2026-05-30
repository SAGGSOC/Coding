import java.util.*;

/**
 * LC 503 - Next Greater Element II (Circular Array).
 *
 * Pattern: Monotonic Decreasing Stack + traverse array TWICE (simulate circular).
 *
 * The array is circular, so after the last element we wrap around to the beginning.
 * Trick: iterate from index 2*n-1 down to 0 (right to left), using i % n.
 * The stack holds VALUES (not indices) — for each position, pop everything smaller,
 * then the stack top is the NGE. If stack is empty, NGE = -1.
 *
 * Time  : O(n)
 * Space : O(n)
 */
public class NextGreaterElement2 {

    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>(); // stores values

        // Traverse from right to left, twice (to handle circular).
        for (int i = 2 * n - 1; i >= 0; i--) {
            int curr = nums[i % n];

            // Pop all elements <= current (they can't be NGE for anyone to the left).
            while (!stack.isEmpty() && stack.peek() <= curr) {
                stack.pop();
            }

            // Only record answer during the first pass (i < n).
            if (i < n) {
                result[i] = stack.isEmpty() ? -1 : stack.peek();
            }

            stack.push(curr);
        }
        return result;
    }

    public static void main(String[] args) {
        NextGreaterElement2 sol = new NextGreaterElement2();
        System.out.println(Arrays.toString(sol.nextGreaterElements(
            new int[]{1, 2, 1})));
        // [2, -1, 2]

        System.out.println(Arrays.toString(sol.nextGreaterElements(
            new int[]{1, 2, 3, 4, 3})));
        // [2, 3, 4, -1, 4]
    }
}
