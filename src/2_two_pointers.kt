fun main() {
    /** Testing [isValidPalindrome] */
    check(isValidPalindrome("Was it a car or a cat I saw?"))
    check(!isValidPalindrome("tab a cat"))

    /** Testing [twoSum] */
    check(twoSum(intArrayOf(3,4,5,6), 7).contentEquals(intArrayOf(0, 1)))
    check(twoSum(intArrayOf(4,5,6), 10).contentEquals(intArrayOf(0, 2)))
    check(twoSum(intArrayOf(5,5), 10).contentEquals(intArrayOf(0, 1)))

    /** Testing [threeSum] */
    check(threeSum(intArrayOf(-1,0,1,2,-1,-4)) == listOf(listOf(-1,-1,2), listOf(-1,0,1)))
    check(threeSum(intArrayOf(0,1,1)) == emptyList<List<Int>>())
    check(threeSum(intArrayOf(0,0,0)) == listOf(listOf(0,0,0)))

    /** Testing [maxArea] */
    check(maxArea(intArrayOf(1,7,2,5,4,7,3,6)) == 36)
    check(maxArea(intArrayOf(2,2,2)) == 4)
}

/**
 * Valid Palindrome
 * ---
 * Given a string `s`, return `true` if it is a palindrome, otherwise return `false`.
 *
 * A palindrome is a string that reads the same forward and backward.
 * It is also case-insensitive and ignores all non-alphanumeric characters.
 *
 * Note: Alphanumeric characters consist of letters (A-Z, a-z) and numbers (0-9).
 */
fun isValidPalindrome(s: String): Boolean {
    var left = 0; var right = s.length-1
    while (left < right) {
        while (left < right && !s[left].isLetterOrDigit()) left++
        while (left < right && !s[right].isLetterOrDigit()) right--
        if (s[left].lowercaseChar() != s[right].lowercaseChar()) return false
        left++; right--
    }
    return true
}

/**
 * Two Sum
 * ---
 * Given an array of integers `nums` and an integer `target`,
 * return the indices `i` and `j` such that `nums[i] + nums[j] == target` and `i != j`.
 *
 * You may assume that every input has exactly one pair of indices `i` and `j`
 * that satisfy the condition.
 *
 * Return the answer with the smaller index first.
 */
fun twoSum(nums: IntArray, target: Int): IntArray {
    val _nums = MutableList(nums.size) { IntArray(2) }
    nums.forEachIndexed { index, i -> _nums.add(intArrayOf(i, index)) }
    _nums.sortBy { it[0] }
    var i = 0; var j = _nums.size-1; var sum: Int
    while (i < j) {
        sum = _nums[i][0] + _nums[j][0]
        when {
            sum < target -> i++
            sum > target -> j--
            else -> return intArrayOf(_nums[i][1], _nums[j][1])
        }
    }
    return intArrayOf()
}

/**
 * 3Sum
 * ---
 * Given an integer array `nums`, return all the triplets `[nums[i], nums[j], nums[k]]`
 * where `nums[i] + nums[j] + nums[k] == 0`, and the indices `i`, `j` and `k` are all distinct.
 *
 * The output should not contain any duplicate triplets.
 * You may return the output and the triplets in any order.
 */
fun threeSum(nums: IntArray): List<List<Int>> {
    val res = mutableSetOf<List<Int>>()
    nums.sort()
    nums.forEachIndexed { index, i ->
        var left = index+1; var right = nums.size-1
        while (left < right) {
            val sum = i + nums[left] + nums[right]
            when {
                sum < 0 -> left++
                sum > 0 -> right--
                else -> {
                    res.add(listOf(i, nums[left], nums[right]))
                    left++
                    right--
//                    while (left < right && nums[left] == nums[left - 1]) {
//                        left++
//                    }
                }
            }
        }
    }
    return res.toList()
}

/**
 * Container With Most Water
 * ---
 * You are given an integer array `heights` where `heights[i]` represents the height of the
 * `i-th` bar.
 *
 * You may choose any two bars to form a container.
 * Return the maximum amount of water a container can store.
 */
fun maxArea(heights: IntArray): Int {
    var left = 0; var right = heights.size-1
    var maxArea = 0
    while (left < right) {
        val area = minOf(heights[left], heights[right]) * (right - left)
        maxArea = maxOf(maxArea, area)
        if (heights[left] <= heights[right]) {
            left++
        } else {
            right--
        }
    }
    return maxArea
}