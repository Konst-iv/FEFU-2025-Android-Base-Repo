package co.feip.fefu2025.presentation.recommendations

import co.feip.fefu2025.domain.model.Anime

sealed class RecommendationsState {
    object Loading : RecommendationsState()
    data class Error(val message: String) : RecommendationsState()
    data class Success(
        val recommendations: List<Anime>,
        val isLoadingMore: Boolean,
        val canLoadMore: Boolean
    ) : RecommendationsState()
}