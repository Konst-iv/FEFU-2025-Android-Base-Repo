package co.feip.fefu2025.presentation.search

import co.feip.fefu2025.domain.model.Anime

sealed class SearchScreenState {
    object Idle : SearchScreenState()
    object Loading : SearchScreenState()
    object Empty : SearchScreenState()
    data class Error(val message: String) : SearchScreenState()
    data class Success(
        val animeList: List<Anime>,
        val isLoadingMore: Boolean = false,
        val canLoadMore: Boolean = true
    ) : SearchScreenState()
}