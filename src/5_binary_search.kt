fun main() {
    /** Testing [findMin] */
    check(findMin(intArrayOf(3,4,5,6,1,2)) == 1)
    check(findMin(intArrayOf(4,5,0,1,2,3)) == 0)
    check(findMin(intArrayOf(4,5,6,7)) == 4)

    /** Testing [findMin] */
    check(search(intArrayOf(3,4,5,6,1,2), 1) == 4)
    check(search(intArrayOf(3,5,6,0,1,2), 4) == -1)
}

/**
 * Find Minimum in Rotated Sorted Array
 * ---
 * You are given an array of length `n` which was originally sorted in ascending order.
 * It has now been rotated between `1` and `n` times.
 * For example, the array `nums = [1,2,3,4,5,6]` might become:
 *
 * `[3,4,5,6,1,2]` if it was rotated `4` times.
 * `[1,2,3,4,5,6]` if it was rotated `6` times.
 * Notice that rotating the array `4` times moves the last four elements of the array to the beginning.
 * Rotating the array `6` times produces the original array.
 *
 * Assuming all elements in the rotated sorted array `nums` are unique, return the minimum element of this array.
 *
 * A solution that runs in `O(n)` time is trivial, can you write an algorithm that runs in `O(log n)` time?
 */
private fun findMin(nums: IntArray): Int {
    var left = 0
    var right = nums.size - 1
    while (left < right) {
        val mid = (left + right) / 2
        if (nums[mid] < nums[right])
            right = mid
        else
            left = mid + 1
    }
    return nums[left]
}

/**
 * Search in Rotated Sorted Array
 * ---
 * You are given an array of length `n` which was originally sorted in ascending order.
 * It has now been rotated between `1` and `n` times. For example, the array `nums = [1,2,3,4,5,6]` might become:
 *
 * `[3,4,5,6,1,2]` if it was rotated 4 times.
 * `[1,2,3,4,5,6]` if it was rotated 6 times.
 * Given the rotated sorted array `nums` and an integer `target`, return the index of `target` within `nums`, or `-1`
 * if it is not present.
 *
 * You may assume all elements in the sorted rotated array `nums` are unique,
 *
 * A solution that runs in `O(n)` time is trivial, can you write an algorithm that runs in `O(log n)` time?
 */
private fun search(nums: IntArray, target: Int): Int {
    var left = 0
    var right = nums.lastIndex
    while (left <= right) {
        val mid = left + (right - left) / 2
        when {
            // Target found at the middle index.
            nums[mid] == target -> return mid
            // The rotation point is to the left of mid, and target is greater than or equal to the leftmost element,
            // so the target must be in the left subarray.
            nums[left] > nums[mid] && nums[left] <= target -> right = mid - 1
            // The rotation point is to the right of mid, and target is less than or equal to the rightmost element,
            // so the target must be in the right subarray.
            nums[right] < nums[mid] && nums[right] >= target -> left = mid + 1
            // Standard binary search case.
            nums[mid] > target -> right = mid - 1
            else -> left = mid + 1
        }
    }
    return -1
}