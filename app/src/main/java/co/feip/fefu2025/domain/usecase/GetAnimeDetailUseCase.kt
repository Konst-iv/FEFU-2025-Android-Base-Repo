package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAnimeDetailUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(id: Int): Anime? = withContext(Dispatchers.IO) {
        repository.getAnimeById(id)
    }
}