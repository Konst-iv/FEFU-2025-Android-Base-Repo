package co.feip.fefu2025.domain.model

data class Anime(
    val id: Int,
    val title: String,
    val rating: String,
    val genres: List<String>,
    val imageResId: Int,
    val info: String? = null,
    val episodesInfo: String? = null,
    val description: String? = null,
    val ratings: Map<Int, Int>? = null,
    val recommendationIds: List<Int>? = null,
    val recommendations: List<Anime>? = null
)