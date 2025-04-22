package co.feip.fefu2025.domain.repository

import co.feip.fefu2025.domain.model.Anime

interface AnimeRepository {
    fun getAnimeById(id: Int): Anime?
    fun getAnimeList(): List<Anime>
}
