/** Sort both outer and inner lists before comparison */
fun normalize(list: List<List<String>>): List<List<String>> {
    return list.map { it.sorted() }.sortedBy { it.joinToString() }
}