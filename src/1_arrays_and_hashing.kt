fun main() {
    /** Testing [containsDuplicate] */
    check(containsDuplicate(intArrayOf(1, 2, 3, 3)))
    check(!containsDuplicate(intArrayOf(1, 2, 3, 4)))

    /** Testing [isValidAnagram] */
    check(isValidAnagram("racecar", "carrace"))
    check(!isValidAnagram("jar", "jam"))

    /** Testing [groupAnagrams] */
    check(normalize(groupAnagrams(listOf("act","pots","tops","cat","stop","hat"))) == normalize(listOf(listOf("hat"), listOf("act", "cat"), listOf("stop", "pots", "tops"))))
    check(normalize(groupAnagrams(listOf("x"))) == normalize(listOf(listOf("x"))))
    check(normalize(groupAnagrams(listOf(""))) == normalize(listOf(listOf(""))))

    /** Testing [topKFrequent] */
    check(topKFrequent(intArrayOf(1,2,2,3,3,3), 2).contentEquals(intArrayOf(2, 3)))
    check(topKFrequent(intArrayOf(7,7), 1).contentEquals(intArrayOf(7)))

//    /** Testing [encode] & [decode] */
//    check(decode(encode(listOf("neet","code","love","you"))) == listOf("neet","code","love","you"))
//    check(decode(encode(listOf("we","say",":","yes"))) == listOf("we","say",":","yes"))

    /** Testing [productExceptSelf] */
    check(productExceptSelf(intArrayOf(1,2,4,6)).contentEquals(intArrayOf(48,24,12,8)))
    check(productExceptSelf(intArrayOf(-1,0,1,2,3)).contentEquals(intArrayOf(0,-6,0,0,0)))

    /** Testing [longestConsecutive] */
    check(longestConsecutive(intArrayOf(2,20,4,10,3,4,5)) == 4)
    check(longestConsecutive(intArrayOf(0,3,2,5,4,6,1,1)) == 7)
}

/**
 * Contains Duplicate
 * ---
 * Given an integer array `nums`,
 * return `true` if any value appears more than once in the array,
 * otherwise return `false`.
 */
fun containsDuplicate(nums: IntArray): Boolean {
    val set = mutableSetOf<Int>()
    for (num in nums) {
        if (set.contains(num)) {
            return true
        } else {
            set.add(num)
        }
    }
    return false
}

/**
 * Valid Anagram
 * ---
 * Given two strings `s` and `t`,
 * return `true` if the two strings are anagrams of each other,
 * otherwise return `false`.
 *
 * An anagram is a string that contains the exact same characters as another string,
 * but the order of the characters can be different.
 */
fun isValidAnagram(s: String, t: String): Boolean {
    val map = hashMapOf<Char, Int>()
    for (c in s) {
        if (map.contains(c)) {
            map.put(c, map.get(c)!! + 1)
        } else {
            map.put(c, 1)
        }
    }
    for (c in t) {
        if (map.contains(c)) {
            map.put(c, map.get(c)!! - 1)
            if (map.get(c) == 0) {
                map.remove(c)
            }
        } else {
            return false
        }
    }
    return map.isEmpty()
}

/**
 * Group Anagrams
 * ---
 * Given an array of strings `strs`, group all anagrams together into sublists.
 * You may return the output in any order.
 */
// given strs = ["eat", "tea", "tan", "ate", "nat"]
// map = {
//      [1 (0th index i.e. a), .. ,1 (index of e), .. ,1 (index of t), ..] -> ["eat", "tea", "ate"],
//      [1 (0th index i.e. a), .. ,1 (index of n), .. ,1 (index of t), ..] -> ["tan", "nat"]
// }
fun groupAnagrams(strs: List<String>): List<List<String>> {
    val map = hashMapOf<List<Int>, MutableList<String>>() // mapping charCount to list of anagrams
    strs.forEach { str ->
        val counts = MutableList(26) { 0 }
        str.forEach { c ->
            counts[c - 'a']++ // getAscii helper function
        }
        map.getOrPut(counts) { mutableListOf() }.add(str)
    }
    return map.values.toList()
}

/**
 * Top K Frequent Elements
 * ---
 * Given an integer array `nums` and an integer `k`, return the `k` most frequent elements within the array.
 *
 * The test cases are generated such that the answer is always unique.
 *
 * You may return the output in any order.
 */
fun topKFrequent(nums: IntArray, k: Int): IntArray {
    val map = hashMapOf<Int, Int>()
    nums.forEach { num ->
        if (map.contains(num)) {
            map.put(num, map.get(num)!!+1)
        } else {
            map.put(num, 1)
        }
    }
    return map.filterValues { it >= k }.keys.toIntArray()
}

/**
 * Encode and Decode Strings
 * ---
 * Design an algorithm to encode a list of strings to a single string.
 * The encoded string is then decoded back to the original list of strings.
 *
 * Please implement `encode` and `decode`.
 */
//fun encode(strs: List<String>): String {
//    return ""
//}
//
//fun decode(str: String): List<String> {
//    return emptyList()
//}

/**
 * Products of Array Except Self
 * ---
 * Given an integer array `nums`, return an array `output` where
 * `output[i]` is the product of all the elements of `nums` except `nums[i]`.
 *
 * Each product is guaranteed to fit in a 32-bit integer.
 *
 * Follow-up: Could you solve it in `O(n)` time without using the division operation?
 */
fun productExceptSelf(nums: IntArray): IntArray {
    val pre = IntArray(nums.size)
    for (i in 0 .. nums.size-1) {
        if (i == 0) {
            pre[i] = 1
        } else {
            pre[i] = pre[i-1] * nums[i-1]
        }
    }
    val post = IntArray(nums.size)
    for (i in nums.size-1 downTo 0) {
        if (i == nums.size-1) {
            post[i] = 1
        } else {
            post[i] = post[i+1] * nums[i+1]
        }
    }
    return pre.zip(post) { a, b -> a * b }.toIntArray()
}

/**
 * Longest Consecutive Sequence
 * ---
 * Given an array of integers `nums`, return the length of the longest consecutive sequence of elements that can be formed.
 *
 * You must write an algorithm that runs in `O(n)` time.
 */
fun longestConsecutive(nums: IntArray): Int {
    val numSet = hashSetOf<Int>()
    nums.forEach { numSet.add(it) }
    var maxLength = 0
    numSet.forEach { num ->
        if (!numSet.contains(num-1)) {
            var currentNum = num
            var currentLength = 1
            while (numSet.contains(currentNum + 1)) {
                currentNum++
                currentLength++
            }
            maxLength = maxOf(maxLength, currentLength)
        }
    }
    return maxLength
}