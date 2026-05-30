import java.util.*;

/**
 * LC 84 - Largest Rectangle in Histogram.
 *
 * Pattern: Monotonic Increasing Stack (Next Smaller Element on both sides).
 *
 * For each bar, find:
 *   - leftSmaller[i]  = index of the nearest bar to the LEFT that is shorter.
 *   - rightSmaller[i] = index of the nearest bar to the RIGHT that is shorter.
 *   - Width = rightSmaller[i] - leftSmaller[i] - 1
 *   - Area = heights[i] * width
 *
 * Single-pass approach: use an increasing stack. When we pop a bar (it found a
 * shorter bar to its right), the new stack top is its left boundary.
 *
 * Time  : O(n)
 * Space : O(n)
 */
public class LargestRectangleHistogram {

    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i <= n; i++) {
            // Use 0 as a sentinel to flush remaining bars at the end.
            int currHeight = (i == n) ? 0 : heights[i];

            while (!stack.isEmpty() && currHeight < heights[stack.peek()]) {
                int h = heights[stack.pop()];
                // Width: from (left boundary + 1) to (i - 1).
                int width = stack.isEmpty() ? i : (i - stack.peek() - 1);
                maxArea = Math.max(maxArea, h * width);
            }
            stack.push(i);
        }
        return maxArea;
    }

    public static void main(String[] args) {
        LargestRectangleHistogram sol = new LargestRectangleHistogram();
        System.out.println(sol.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3})); // 10
        System.out.println(sol.largestRectangleArea(new int[]{2, 4}));              // 4
    }
}
