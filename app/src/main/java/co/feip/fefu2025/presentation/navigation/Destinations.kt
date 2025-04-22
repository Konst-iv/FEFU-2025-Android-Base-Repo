package co.feip.fefu2025.navigation

object Destinations {
    const val MAIN_ROUTE = "main"
    const val ANIME_DETAIL_ROUTE = "anime/{id}"
    const val RECOMMENDATIONS_ROUTE = "recommendations"

    fun animeDetail(id: Int): String = "anime/$id"
}