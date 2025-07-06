import java.util.Stack

fun main() {
    /** Testing [isValidParentheses] */
    check(isValidParentheses("[]"))
    check(isValidParentheses("([{}])"))
    check(!isValidParentheses("[(])"))
}

/**
 * Valid Parentheses
 * ---
 * You are given a string `s` consisting of the following characters: '(', ')', '{', '}', '[' and ']'.
 *
 * The input string `s` is valid if and only if:
 *
 * Every open bracket is closed by the same type of close bracket.
 * Open brackets are closed in the correct order.
 * Every close bracket has a corresponding open bracket of the same type.
 * Return `true` if `s` is a valid string, and `false` otherwise.
 */
fun isValidParentheses(s: String): Boolean {
    val stack = Stack<Char>()
    s.forEach { ch ->
        when (ch) {
            '(' -> stack.push(')')
            '{' -> stack.push('}')
            '[' -> stack.push(']')
            else -> if (stack.isEmpty() || stack.pop() != ch) {
                return false
            }
        }
    }
    return stack.isEmpty()
}