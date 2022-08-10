package dev.vivvvek.astro.data.android

import dev.vivvvek.astro.domain.AstroImage
import dev.vivvvek.astro.domain.AstroRepository
import dev.vivvvek.astro.domain.Response
import dev.vivvvek.astro.domain.SortOrder

class DefaultAstroRepository(val astroDataSource: AstroDataSource): AstroRepository {

    override suspend fun getAllImages(sortOrder: SortOrder): Response<List<AstroImage>> {
        return when (val response = astroDataSource.getImages()) {
            is Response.Success -> {
                if (sortOrder == SortOrder.LATEST)
                    Response.Success(response.data.sortedByDescending { it.date })
                else Response.Success(response.data.sortedBy { it.date })
            }
            is Response.Error -> Response.Error(response.error)
        }
    }
}