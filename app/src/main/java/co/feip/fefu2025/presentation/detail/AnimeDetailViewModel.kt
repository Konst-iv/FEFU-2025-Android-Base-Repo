// AnimeDetailViewModel.kt
package co.feip.fefu2025.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnimeDetailViewModel(
    private val getAnimeDetailUseCase: GetAnimeDetailUseCase,
    private val animeId: Int
) : ViewModel() {
    private val _state = MutableStateFlow<AnimeDetailState>(AnimeDetailState.Loading)
    val state: StateFlow<AnimeDetailState> = _state

    init {
        loadAnime()
    }

    fun loadAnime() {  // Убрали параметр animeId, так как он уже есть в классе
        viewModelScope.launch {
            _state.value = AnimeDetailState.Loading
            try {
                getAnimeDetailUseCase(animeId)?.let { anime ->
                    _state.value = AnimeDetailState.Success(anime)
                } ?: run {
                    _state.value = AnimeDetailState.Error("Аниме не найдено")
                }
            } catch (e: Exception) {
                _state.value = AnimeDetailState.Error(e.message ?: "Ошибка загрузки")
            }
        }
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