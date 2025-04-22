package co.feip.fefu2025.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import co.feip.fefu2025.domain.model.Anime
import co.feip.fefu2025.domain.usecase.GetAnimeListUseCase

class MainViewModel(private val getAnimeListUseCase: GetAnimeListUseCase) : ViewModel() {
    val animeList: SnapshotStateList<Anime> = mutableStateListOf()

    init {
        animeList.addAll(getAnimeListUseCase())
    }

    class Factory(private val useCase: GetAnimeListUseCase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(useCase) as T
        }
    }
}