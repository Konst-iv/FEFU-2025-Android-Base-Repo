package co.feip.fefu2025.domain.usecase

import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.model.PaginatedResult
import co.feip.fefu2025.domain.repository.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchAnimeUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(query: String, page: Int): PaginatedResult<Anime> = withContext(Dispatchers.IO) {
        repository.searchAnime(query, page)
    }
}