package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.repository.AnimeRepository

class GetAnimeDetailUseCase(private val repository: AnimeRepository) {
    operator fun invoke(id: Int): Anime? = repository.getAnimeById(id)
}