package co.feip.fefu2025.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.usecase.SearchAnimeUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val searchAnimeUseCase: SearchAnimeUseCase) : ViewModel() {

    private val _state = MutableStateFlow<SearchScreenState>(SearchScreenState.Idle)
    val state: StateFlow<SearchScreenState> = _state

    private var currentPage = 1
    private var isLoading = false
    private var currentQuery = ""
    private var searchJob: Job? = null

    fun onQueryChanged(query: String) {
        searchJob?.cancel()
        currentQuery = query
        if (query.isBlank()) {
            _state.value = SearchScreenState.Idle
            return
        }
        loadMoreItems(isRefresh = true)
    }

    fun loadMoreItems(isRefresh: Boolean = false) {
        searchJob?.cancel()
        if (isLoading || currentQuery.isBlank()) return

        val currentState = _state.value
        if (!isRefresh && currentState is SearchScreenState.Success && !currentState.canLoadMore) return

        searchJob = viewModelScope.launch {
            if (isRefresh) {
                delay(300)
                _state.value = SearchScreenState.Loading
                currentPage = 1
            }

            isLoading = true
            if (!isRefresh && currentState is SearchScreenState.Success) {
                _state.value = currentState.copy(isLoadingMore = true)
            }

            try {
                val result = searchAnimeUseCase(currentQuery, currentPage)
                if (isRefresh && result.items.isEmpty()) {
                    _state.value = SearchScreenState.Empty
                } else {
                    val currentList = if (isRefresh || _state.value !is SearchScreenState.Success) {
                        emptyList()
                    } else {
                        (_state.value as SearchScreenState.Success).animeList
                    }
                    _state.value = SearchScreenState.Success(
                        animeList = currentList + result.items,
                        isLoadingMore = false,
                        canLoadMore = result.hasNextPage
                    )
                    if (result.hasNextPage) {
                        currentPage++
                    }
                }
            } catch (e: Exception) {
                if (currentPage == 1) {
                    _state.value = SearchScreenState.Error(e.message ?: "Ошибка поиска")
                }
            } finally {
                isLoading = false
            }
        }
    }

    class Factory(private val useCase: SearchAnimeUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SearchViewModel(useCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}