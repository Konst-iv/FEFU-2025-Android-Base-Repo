package co.feip.fefu2025.data.remote

import co.feip.fefu2025.data.remote.dto.AnimeDetailResponse
import co.feip.fefu2025.data.remote.dto.AnimeListResponse
import co.feip.fefu2025.data.remote.dto.AnimeStatisticsResponse
import co.feip.fefu2025.data.remote.dto.RecommendationsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanApiService {

    companion object {
        const val PAGE_SIZE = 24
    }

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int,
        @Query("limit") limit: Int = PAGE_SIZE
    ): AnimeListResponse

    @GET("anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = PAGE_SIZE
    ): AnimeListResponse

    @GET("anime/{id}")
    suspend fun getAnimeDetail(@Path("id") id: Int): AnimeDetailResponse

    @GET("anime/{id}/recommendations")
    suspend fun getAnimeRecommendations(@Path("id") id: Int): RecommendationsResponse

    @GET("anime/{id}/statistics")
    suspend fun getAnimeStatistics(@Path("id") id: Int): AnimeStatisticsResponse
}