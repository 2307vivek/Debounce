package dev.vivvvek.astro.domain

interface AstroRepository {
    suspend fun getAllImages(sortOrder: SortOrder) : Response<List<AstroImage>>
}