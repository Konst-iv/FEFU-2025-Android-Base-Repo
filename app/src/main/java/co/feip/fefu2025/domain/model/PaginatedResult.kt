package co.feip.fefu2025.domain.model


data class PaginatedResult<T>(
    val items: List<T>,
    val hasNextPage: Boolean
)