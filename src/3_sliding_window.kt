fun main() {
    /** Testing [maxProfit] */
    check(maxProfit(intArrayOf(10,1,5,6,7,1)) == 6)
    check(maxProfit(intArrayOf(10,8,7,5,2)) == 0)

    /** Testing [lengthOfLongestSubstring] */
    check(lengthOfLongestSubstring("zxyzxyz") == 3) // zxyzxyz -> xyz
    check(lengthOfLongestSubstring("xxxx") == 1) // xxxx -> x

    /** Testing [characterReplacement] */
    check(characterReplacement("XYYX", 2) == 4) // replace the 'X's with 'Y's, or replace the 'Y's with 'X's
    check(characterReplacement("AAABABB", 1) == 5) // replace the 'B' with 'A'

    /** Testing [minWindow] */
    // The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
    check(minWindow("ADOBECODEBANC", "ABC") == "BANC")
    // The entire string s is the minimum window.
    check(minWindow("a", "a") == "a")
    // Both 'a's from t must be included in the window. Since the largest window of s only has one 'a', return empty string.
    check(minWindow("a", "aa") == "")
}

/**
 * Best Time to Buy and Sell Stock
 * ---
 * You are given an integer array `prices` where `prices[i]` is the price of NeetCoin on the `i-th` day.
 *
 * You may choose a single day to buy one NeetCoin and choose a different day in the future to sell it.
 *
 * Return the maximum profit you can achieve. You may choose to not make any transactions,
 * in which case the profit would be `0`.
 */
fun maxProfit(prices: IntArray): Int {
    var maxProfit = 0
    var minBuy = Int.MAX_VALUE
    prices.forEach { price ->
        maxProfit = maxOf(maxProfit, price - minBuy)
        minBuy = minOf(minBuy, price)
    }
    return maxProfit
}

/**
 * Longest Substring Without Repeating Characters
 * ---
 * Given a string `s`, find the length of the longest substring without duplicate characters.
 */
fun lengthOfLongestSubstring(s: String): Int {
    var i = 0 // slow pointer
    var j = 0 // fast pointer
    var max = 0
    val set = hashSetOf<Char>()
    while (j < s.length) {
        // if character j is not in the hash set, then add it, move j forward and update the max length,
        // otherwise, delete from the head by using the slow pointer i until we can put character j to the hash set
        if (!set.contains(s[j])) {
            set.add(s[j++])
            max = maxOf(max, set.size)
        } else {
            set.remove(s[i++])
        }
    }
    return max
}

/**
 * Longest Repeating Character Replacement
 * ---
 * You are given a string `s` consisting of only uppercase english characters and an integer `k`.
 * You can choose up to `k` characters of the string and replace them with any other uppercase English character.
 *
 * After performing at most `k` replacements, return the length of the longest substring which contains only one
 * distinct character.
 */
fun characterReplacement(s: String, k: Int): Int {
    val frequency = IntArray(128) // ASCII size to hold character counts
    var maxFrequency = 0
    var left = 0
    var result = 0
    s.forEachIndexed { index, ch ->
        val rightChar = ch
        frequency[rightChar.code]++
        // Track the highest frequency of any single character in the current window
        maxFrequency = maxOf(maxFrequency, frequency[ch.code])
        // If more than `k` replacements are needed, shrink window from the left
        while ((index - left + 1) - maxFrequency > k) {
            val leftChar = s[left]
            frequency[leftChar.code]--
            left++
        }
        // Update the max window size
        result = maxOf(result, index - left + 1)
    }
    return result
}

//fun characterReplacement(s: String, k: Int): Int {
//    val frequency = IntArray(26)
//    var maxFrequency = 0
//    var left = 0
//    s.forEachIndexed { index, ch ->
//        maxFrequency = maxOf(maxFrequency, ++frequency[ch - 'A'])
//        if (index - left + 1 > maxFrequency + k) {
//            --frequency[s[left++] - 'A']
//        }
//    }
//    return s.length - left
//}

/**
 * Minimum Window Substring
 * ---
 * Given two strings `s` and `t`, return the shortest substring of `s` such that every character in `t`,
 * including duplicates, is present in the substring. If such a substring does not exist, return an empty string `""`.
 *
 * You may assume that the correct output is always unique.
 */
fun minWindow(s: String, t: String): String {
    val freq = IntArray(128) { 0 }
    t.forEach { freq[it.code]++ }
    var result = ""
    var count = t.length
    var left = 0; var right = 0
    while (right < s.length) {
        if (freq[s[right].code]-- > 0) {
            count--
        }
        while (count == 0) {
            if (result.isEmpty() || result.length > right - left + 1) {
                result = s.substring(left, right + 1)
            }
            freq[s[left].code]++
            if (freq[s[left++].code] > 0) {
                count++
            }
        }
        right++
    }
    return result
}