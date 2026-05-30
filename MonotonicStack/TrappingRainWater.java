import java.util.*;

/**
 * LC 42 - Trapping Rain Water.
 *
 * Pattern: Monotonic Decreasing Stack.
 *
 * Approach: Use a stack to find "valleys" bounded by taller bars on both sides.
 * When we encounter a bar taller than the stack top, we've found the right boundary
 * of a pool. The new stack top (after popping) is the left boundary.
 *
 * For each popped bar (the valley bottom):
 *   - boundedHeight = min(leftBar, rightBar) - valleyHeight
 *   - width = rightIndex - leftIndex - 1
 *   - water += boundedHeight * width
 *
 * Time  : O(n)
 * Space : O(n)
 */
public class TrappingRainWater {

    public int trap(int[] height) {
        int n = height.length;
        int water = 0;
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int bottom = stack.pop();
                if (stack.isEmpty()) break; // no left boundary

                int left = stack.peek();
                int boundedHeight = Math.min(height[left], height[i]) - height[bottom];
                int width = i - left - 1;
                water += boundedHeight * width;
            }
            stack.push(i);
        }
        return water;
    }

    public static void main(String[] args) {
        TrappingRainWater sol = new TrappingRainWater();
        System.out.println(sol.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1})); // 6
        System.out.println(sol.trap(new int[]{4, 2, 0, 3, 2, 5}));                     // 9
    }
}
