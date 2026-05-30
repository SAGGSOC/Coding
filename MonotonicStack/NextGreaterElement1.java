import java.util.*;

/**
 * LC 496 - Next Greater Element I.
 *
 * Pattern: Monotonic Decreasing Stack + HashMap.
 * Traversal: RIGHT TO LEFT (start from end).
 *
 * Approach (right-to-left):
 *   1. Traverse nums2 from the end. For each element:
 *      - Pop everything from stack that is <= current (they can't be NGE for anyone).
 *      - Stack top (if exists) is the NGE for current element.
 *      - Push current onto stack.
 *   2. Store results in a HashMap (value → its NGE).
 *   3. For each element in nums1, look up the map.
 *
 * Time  : O(n + m)
 * Space : O(n)
 */
public class NextGreaterElement1 {

    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Map<Integer, Integer> ngeMap = new HashMap<>();
        Stack<Integer> stack = new Stack<>(); // stores values

        // Traverse nums2 from RIGHT to LEFT.
        for (int i = nums2.length - 1; i >= 0; i--) {
            int curr = nums2[i];

            // Pop all elements <= current (they can't be NGE for anything to the left).
            while (!stack.isEmpty() && stack.peek() <= curr) {
                stack.pop();
            }

            // Stack top is the NGE for current. Empty stack means no NGE.
            ngeMap.put(curr, stack.isEmpty() ? -1 : stack.peek());

            // Push current onto stack.
            stack.push(curr);
        }

        // Build result for nums1.
        int[] result = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            result[i] = ngeMap.getOrDefault(nums1[i], -1);
        }
        return result;
    }

    public static void main(String[] args) {
        NextGreaterElement1 sol = new NextGreaterElement1();
        System.out.println(Arrays.toString(sol.nextGreaterElement(
            new int[]{4, 1, 2}, new int[]{1, 3, 4, 2})));
        // [-1, 3, -1]

        System.out.println(Arrays.toString(sol.nextGreaterElement(
            new int[]{2, 4}, new int[]{1, 2, 3, 4})));
        // [3, -1]
    }
}
