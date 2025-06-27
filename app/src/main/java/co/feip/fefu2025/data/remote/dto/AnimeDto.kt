package co.feip.fefu2025.data.remote.dto

import com.google.gson.annotations.SerializedName


data class AnimeStatisticsResponse(
    val data: StatisticsDataDto
)

data class StatisticsDataDto(
    val scores: List<ScoreDto>
)

data class ScoreDto(
    val score: Int,
    val votes: Int
)



data class AnimeListResponse(
    val data: List<AnimeDto>,
    val pagination: PaginationDto
)

data class PaginationDto(
    @SerializedName("last_visible_page")
    val lastVisiblePage: Int,
    @SerializedName("has_next_page")
    val hasNextPage: Boolean,
    val items: ItemsDto
)

data class ItemsDto(
    val total: Int
)

data class RecommendationsResponse(
    val data: List<RecommendationEntry>
)

data class AnimeDetailResponse(
    val data: AnimeDto
)

data class RecommendationEntry(
    val entry: AnimeDto?
)

data class AnimeDto(
    @SerializedName("mal_id")
    val malId: Int,
    val url: String,
    val images: ImagesDto,
    val title: String,
    val score: Double?,
    val year: Int?,
    val synopsis: String?,
    val episodes: Int?,
    val status: String?,
    val genres: List<GenreDto>?
)


data class ImagesDto(
    val jpg: ImageUrlDto,
    val webp: ImageUrlDto
)

data class ImageUrlDto(
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("large_image_url")
    val largeImageUrl: String?
)

data class GenreDto(
    val name: String
)