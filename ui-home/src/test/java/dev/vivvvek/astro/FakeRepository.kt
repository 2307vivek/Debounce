/*
 * Copyright 2021 Vivek Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.vivvvek.astro

import dev.vivvvek.astro.domain.AstroRepository
import dev.vivvvek.astro.domain.Response
import dev.vivvvek.astro.domain.models.Image

class FakeRepository() : AstroRepository {

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
