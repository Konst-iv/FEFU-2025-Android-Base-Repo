package co.feip.fefu2025.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getAnimeListUseCase: GetAnimeListUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val state: StateFlow<MainScreenState> = _state

    private var currentPage = 1
    private var isLoading = false

    init {
        loadMoreItems(isRefresh = true)
    }

    fun loadMoreItems(isRefresh: Boolean = false) {
        if (isLoading) return

        val currentState = _state.value
        if (!isRefresh && currentState is MainScreenState.Success && !currentState.canLoadMore) return

        viewModelScope.launch {
            isLoading = true
            if (isRefresh) {
                currentPage = 1
                _state.value = MainScreenState.Loading
            }

            if (!isRefresh && currentState is MainScreenState.Success) {
                _state.value = currentState.copy(isLoadingMore = true)
            }

            try {
                val result = getAnimeListUseCase(page = currentPage)
                val currentList = if (isRefresh || _state.value !is MainScreenState.Success) {
                    emptyList()
                } else {
                    (_state.value as MainScreenState.Success).animeList
                }
                _state.value = MainScreenState.Success(
                    animeList = currentList + result.items,
                    isLoadingMore = false,
                    canLoadMore = result.hasNextPage
                )
                if (result.hasNextPage) {
                    currentPage++
                }
            } catch (e: Exception) {
                if (currentPage == 1) {
                    _state.value = MainScreenState.Error(e.message ?: "Ошибка загрузки")
                }
            } finally {
                isLoading = false
            }
        }
    }

    class Factory(private val useCase: GetAnimeListUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(useCase) as T
        }
    }
}