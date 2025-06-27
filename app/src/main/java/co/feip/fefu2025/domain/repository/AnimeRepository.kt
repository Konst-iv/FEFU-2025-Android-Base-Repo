package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.PaginatedResult

interface AnimeRepository {
    suspend fun getAnimeList(page: Int): PaginatedResult<Anime>
    suspend fun getAnimeById(id: Int): Anime?
    suspend fun searchAnime(query: String, page: Int): PaginatedResult<Anime>
    suspend fun getAnimeRecommendations(id: Int): List<Anime>

}