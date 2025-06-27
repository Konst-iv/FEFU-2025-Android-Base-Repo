package co.feip.fefu2025.data.remote

import co.feip.fefu2025.data.remote.dto.AnimeDto
import co.feip.fefu2025.data.remote.dto.ScoreDto
import co.feip.fefu2025.domain.model.Anime

fun List<AnimeDto>.toDomain(): List<Anime> {
    return this.map { it.toDomain() }
}

fun AnimeDto.toDomain(): Anime {
    return Anime(
        id = this.malId,
        title = this.title,
        rating = this.score?.toString() ?: "N/A",
        genres = this.genres?.map { it.name } ?: emptyList(),
        imageUrl = this.images.jpg.largeImageUrl ?: this.images.jpg.imageUrl,
        info = "${this.year ?: "Год не указан"} • ${this.status ?: "Статус неизвестен"}",
        episodesInfo = "${this.episodes ?: "?"} серий",
        description = this.synopsis
    )
}

fun List<ScoreDto>.toDomainRatings(): Map<Int, Int> {
    return this.associate { it.score to it.votes }
}