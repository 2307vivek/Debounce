/*
 * MIT License
 *
 * Copyright (c) 2022 Vivek Singh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.vivvvek.astro.data.android

import dev.vivvvek.astro.domain.AstroImage
import dev.vivvvek.astro.domain.AstroRepository
import dev.vivvvek.astro.domain.Response
import dev.vivvvek.astro.domain.SortOrder

class DefaultAstroRepository(val astroDataSource: AstroDataSource) : AstroRepository {

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
