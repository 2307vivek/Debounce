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
package dev.vivvvek.astro.data.android

import dev.vivvvek.astro.domain.AstroImage
import dev.vivvvek.astro.domain.AstroRepository
import dev.vivvvek.astro.domain.Response
import dev.vivvvek.astro.domain.SortOrder
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultAstroRepository @Inject constructor(
    private val astroDataSource: AstroDataSource
) : AstroRepository {

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
