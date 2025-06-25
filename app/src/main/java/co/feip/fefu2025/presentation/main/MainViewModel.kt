package co.feip.fefu2025.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAnimeListUseCase: GetAnimeListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val state: StateFlow<MainScreenState> = _state

    init {
        loadAnimeList()
    }

    fun loadAnimeList() {
        viewModelScope.launch {
            _state.value = MainScreenState.Loading
            try {
                val animeList = getAnimeListUseCase()
                _state.value = MainScreenState.Success(animeList)
            } catch (e: Exception) {
                _state.value = MainScreenState.Error(e.message ?: "Ошибка загрузки")
                // Попробуем загрузить снова через 3 секунды
                kotlinx.coroutines.delay(3000)
                loadAnimeList()
            }
        }
    }

    class Factory(private val useCase: GetAnimeListUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(useCase) as T
        }
    }
}