package menu

fun main() {
    try {
        RecommendService().run()
    } catch (e: Exception) {
        when (e) {
            is IllegalArgumentException -> throw IllegalStateException(e.message)
            is IllegalStateException -> throw IllegalStateException(e.message)
        }
    }

}
