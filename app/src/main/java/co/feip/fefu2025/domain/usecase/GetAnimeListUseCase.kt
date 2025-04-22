package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.repository.AnimeRepository

class GetAnimeListUseCase(private val repository: AnimeRepository) {
    operator fun invoke(): List<Anime> = repository.getAnimeList()
}