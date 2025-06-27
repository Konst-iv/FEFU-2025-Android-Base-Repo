// C:\Users\vaniy\Desktop\FEFU-2025-Android-Base-Repo\app\src\main\java\co\feip\fefu2025\presentation\recommendations\RecommendationsViewModel.kt

package co.feip.fefu2025.presentation.recommendations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeRecommendationsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecommendationsViewModel(
    private val getAnimeRecommendationsUseCase: GetAnimeRecommendationsUseCase,
    private val animeId: Int
) : ViewModel() {

    private val _state = MutableStateFlow<RecommendationsState>(RecommendationsState.Loading)
    val state: StateFlow<RecommendationsState> = _state

    // Хранилище для ВСЕХ загруженных рекомендаций
    private var fullRecommendationsList: List<Anime> = emptyList()
    private var currentPage = 0
    private var isLoading = false
    private val pageSize = 10 // Размер нашей "искусственной" страницы

    init {
        loadInitialRecommendations()
    }

    private fun loadInitialRecommendations() {
        viewModelScope.launch {
            _state.value = RecommendationsState.Loading
            try {
                // Загружаем все рекомендации один раз
                fullRecommendationsList = getAnimeRecommendationsUseCase(animeId)
                // Показываем первую страницу
                showNextPage()
            } catch (e: Exception) {
                _state.value = RecommendationsState.Error(e.message ?: "Ошибка загрузки рекомендаций")
            }
        }
    }

    fun loadMoreItems() {
        if (isLoading) return

        val currentState = _state.value
        if (currentState is RecommendationsState.Success && !currentState.canLoadMore) return

        viewModelScope.launch {
            isLoading = true
            // Показываем индикатор внизу
            if (currentState is RecommendationsState.Success) {
                _state.value = currentState.copy(isLoadingMore = true)
            }
            // Имитируем задержку сети для плавности
            delay(500)
            showNextPage()
            isLoading = false
        }
    }

    private fun showNextPage() {
        currentPage++
        val newItemsToShow = fullRecommendationsList.take(currentPage * pageSize)
        val canLoadMore = newItemsToShow.size < fullRecommendationsList.size

        _state.value = RecommendationsState.Success(
            recommendations = newItemsToShow,
            isLoadingMore = false,
            canLoadMore = canLoadMore
        )
    }

    // Этот метод нужен для кнопки "Повторить" в случае ошибки
    fun retry() {
        loadInitialRecommendations()
    }


    class Factory(
        private val useCase: GetAnimeRecommendationsUseCase,
        private val animeId: Int
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecommendationsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RecommendationsViewModel(useCase, animeId) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}