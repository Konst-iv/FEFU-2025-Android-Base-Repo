package co.feip.fefu2025.presentation.main

import co.feip.fefu2025.domain.model.Anime

sealed class MainScreenState {
    object Loading : MainScreenState()
    data class Error(val message: String) : MainScreenState()
    data class Success(
        val animeList: List<Anime>,
        val isLoadingMore: Boolean = false,
        val canLoadMore: Boolean = true
    ) : MainScreenState()
}