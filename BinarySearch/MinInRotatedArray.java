package BinarySearch;


class Solution {
    public int findMin(int[] nums) {
        int low = 0, high = nums.length - 1;
        int mmin = Integer.MAX_VALUE;
        while (low <= high) {
            int mid = low + (high - low) / 2;

            // Handles duplicates (LC 154). Safe to skip for LC 153.
            if (nums[low] == nums[mid] && nums[mid] == nums[high]) {
                mmin = Math.min(mmin, nums[mid]);
                low++;
                high--;
                continue;
            }

            // Left half is sorted → min of left half is nums[low].
            if (nums[low] <= nums[mid]) {
                mmin = Math.min(mmin, nums[low]);
                low = mid + 1;
            } else {
                // Right half is sorted → min of right half is nums[mid].
                mmin = Math.min(mmin, nums[mid]);
                high = mid - 1;
            }
        }
        return mmin;
    }
}
public class MinInRotatedArray {
    
}
