package DynamicProgramming;

import java.util.HashSet;
import java.util.Set;

public class FrogJump {

    // Pure recursion: explore all possible jumps from current position.
    // At each stone, the frog can jump k-1, k, or k+1 units forward.
    private static boolean solve(int pos, int k, int target, Set<Integer> stones) {
        if (pos == target) return true;
        if (pos > target || !stones.contains(pos)) return false;

        // Try k+1, k, k-1 (skip 0 since frog must move forward)
        for (int step = k + 1; step >= k - 1; step--) {
            if (step <= 0) continue;
            if (solve(pos + step, step, target, stones)) return true;
        }
        return false;
    }

    public boolean canCross(int[] stones) {
        if (stones.length == 0) return true;
        if (stones[1] != 1) return false; // first jump must be exactly 1

        Set<Integer> stoneSet = new HashSet<>();
        for (int s : stones) stoneSet.add(s);

        int target = stones[stones.length - 1];
        // Start from stone at position 1 with last jump k = 1
        return solve(1, 1, target, stoneSet);
    }

    public static void main(String[] args) {
        FrogJump fj = new FrogJump();
        System.out.println(fj.canCross(new int[]{0, 1, 3, 5, 6, 8, 12, 17})); // true
        System.out.println(fj.canCross(new int[]{0, 1, 2, 3, 4, 8, 9, 11}));  // false
    }
}
