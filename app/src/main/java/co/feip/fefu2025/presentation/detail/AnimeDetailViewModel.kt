package co.feip.fefu2025.presentation.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeDetailUseCase

class AnimeDetailViewModel(
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
    animeId: Int
) : ViewModel() {

    val anime = mutableStateOf<Anime?>(null)

    init {
        anime.value = getAnimeDetailUseCase(animeId)
    }

    class Factory(
        private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
        private val animeId: Int
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AnimeDetailViewModel(getAnimeDetailUseCase, animeId) as T
        }
    }
}
