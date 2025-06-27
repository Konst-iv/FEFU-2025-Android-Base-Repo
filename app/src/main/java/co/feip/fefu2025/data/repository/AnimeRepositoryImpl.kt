package co.feip.fefu2025.data.repository

import co.feip.fefu2025.data.remote.JikanApiService
import co.feip.fefu2025.data.remote.toDomain
import co.feip.fefu2025.data.remote.toDomainRatings
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.PaginatedResult
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class AnimeRepositoryImpl(
    private val apiService: JikanApiService
) : AnimeRepository {

    override suspend fun getAnimeList(page: Int): PaginatedResult<Anime> {
        val response = apiService.getTopAnime(page = page)
        return PaginatedResult(
            items = response.data.toDomain(),
            hasNextPage = response.pagination.hasNextPage
        )
    }

    override suspend fun searchAnime(query: String, page: Int): PaginatedResult<Anime> {
        val response = apiService.searchAnime(query = query, page = page)
        return PaginatedResult(
            items = response.data.toDomain(),
            hasNextPage = response.pagination.hasNextPage
        )
    }

    override suspend fun getAnimeById(id: Int): Anime? {
        return try {
            coroutineScope {
                val detailDeferred = async { apiService.getAnimeDetail(id) }
                val statsDeferred = async { apiService.getAnimeStatistics(id) }
                val recommendationsDeferred = async { apiService.getAnimeRecommendations(id) }

                val animeDetailResponse = detailDeferred.await()
                val animeStatsResponse = statsDeferred.await()
                val recommendationsResponse = recommendationsDeferred.await()

                val anime = animeDetailResponse.data.toDomain()
                val ratingsMap = animeStatsResponse.data.scores.toDomainRatings()

                val recommendations = recommendationsResponse.data
                    .mapNotNull { it.entry?.toDomain() }
                    .take(5)

                anime.copy(
                    ratings = ratingsMap,
                    recommendations = recommendations
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getAnimeRecommendations(id: Int): List<Anime> {
        return try {
            val response = apiService.getAnimeRecommendations(id)
            response.data
                .mapNotNull { it.entry?.toDomain() }
                .take(20)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}