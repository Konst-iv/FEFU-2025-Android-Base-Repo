package co.feip.fefu2025.presentation.detail

import co.feip.fefu2025.domain.model.Anime

sealed class AnimeDetailState {
    object Loading : AnimeDetailState()
    data class Error(val message: String) : AnimeDetailState()
    data class Success(val anime: Anime) : AnimeDetailState()
}