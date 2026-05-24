/**
 * LC 396 - Rotate Function.
 *
 * F(k) = 0*arrk[0] + 1*arrk[1] + ... + (n-1)*arrk[n-1]
 * where arrk is nums rotated clockwise by k.
 *
 * Key recurrence:
 *   F(k) = F(k-1) + sum - n * nums[n - k]
 *
 * Proof:
 *   When rotating by 1 more position, every element's index increases by 1
 *   (contributing +sum to the total), EXCEPT the element that wraps from
 *   position n-1 to position 0 -- it loses (n-1) * its value and gains 0,
 *   net change = -(n-1)*val. Combined: +sum - n*val.
 *
 * Approach 1: Recursive with recurrence (O(n) time, O(n) stack).
 * Approach 2: Brute-force recursive (O(n^2) -- for understanding only).
 */
public class RotateFunction {

    // ---- Approach 1: O(n) recursive using the recurrence ----
    public int maxRotateFunction(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;

        int sum = 0;
        int f0 = 0;
        for (int i = 0; i < n; i++) {
            sum += nums[i];
            f0 += i * nums[i];
        }

        return recurse(nums, n, 1, f0, f0, sum);
    }

    private int recurse(int[] nums, int n, int k, int prevF, int best, int sum) {
        if (k == n) return best;
        int currF = prevF + sum - n * nums[n - k];
        best = Math.max(best, currF);
        return recurse(nums, n, k + 1, currF, best, sum);
    }

    // ---- Approach 2: Brute-force recursive (O(n^2)) ----
    public int maxRotateFunctionBrute(int[] nums) {
        int n = nums.length;
        int minVal = Integer.MIN_VALUE;
        return bruteRecurse(nums, n, 0, minVal);
    }

    private int bruteRecurse(int[] nums, int n, int k, int best) {
        if (k == n) return best;
        int fk = computeF(nums, n, k);
        return bruteRecurse(nums, n, k + 1, Math.max(best, fk));
    }

    private int computeF(int[] nums, int n, int k) {
        int total = 0;
        for (int j = 0; j < n; j++) {
            total += j * nums[(j - k + n) % n];
        }
        return total;
    }

    public static void main(String[] args) {
        RotateFunction sol = new RotateFunction();

        int[] nums1 = {4, 3, 2, 6};
        System.out.println(sol.maxRotateFunction(nums1));
        System.out.println(sol.maxRotateFunctionBrute(nums1));

        int[] nums2 = {100};
        System.out.println(sol.maxRotateFunction(nums2));
    }
}
