package dev.vivvvek.astro

import dev.vivvvek.astro.domain.AstroRepository
import dev.vivvvek.astro.domain.Response
import dev.vivvvek.astro.domain.models.Image

class FakeRepository(): AstroRepository {

    private var shouldCauseError = false

    override suspend fun getAllImages(): Response<List<Image>> {
        return if (!shouldCauseError) {
            println("image size is ${images.size}")
            Response.Success(images)
        } else
            Response.Error("Some error happened")
    }

    fun shouldCauseError(error: Boolean) {
        shouldCauseError = error
    }

    private val images: List<Image> = listOf(
        Image(
            title = "Title 1",
            copyright = "Copyright 1",
            date = "2019-12-01",
            explanation = "explanation 1",
            url = "url 1",
            hdurl = "hdurl 1"
        ),
        Image(
            title = "Title 2",
            copyright = "Copyright 2",
            date = "2019-12-02",
            explanation = "explanation 2",
            url = "url 2",
            hdurl = "hdurl 2"
        ),
        Image(
            title = "Title 3",
            copyright = "Copyright 3",
            date = "2019-12-03",
            explanation = "explanation 3",
            url = "url 3",
            hdurl = "hdurl 3"
        )
    )
}