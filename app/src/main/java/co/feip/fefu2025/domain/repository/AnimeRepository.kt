// AnimeRepository.kt
package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.domain.model.Anime
import kotlinx.coroutines.delay

interface AnimeRepository {
    suspend fun getAnimeById(id: Int): Anime?
    suspend fun getAnimeList(): List<Anime>
    suspend fun searchAnime(query: String): List<Anime> // Добавляем метод поиска
}