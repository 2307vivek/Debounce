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
package dev.vivvvek.astro.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.vivvvek.astro.data.android.di.MainDispatcher
import dev.vivvvek.astro.domain.AstroRepository
import dev.vivvvek.astro.domain.Response
import dev.vivvvek.astro.domain.SortOrder
import dev.vivvvek.astro.domain.models.AstroImage
import dev.vivvvek.astro.domain.models.toAstroImage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AstroViewModel @Inject constructor(
    private val repository: AstroRepository,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState

    var images = listOf<AstroImage>()
    var selectedImageIndex = -1

    fun getAllImages(sortOrder: SortOrder) {
        _homeScreenState.value = _homeScreenState.value.copy(isLoading = true)
        viewModelScope.launch(mainDispatcher) {
            when (val res = repository.getAllImages()) {
                is Response.Success -> {
                    val sortedImages = if (sortOrder == SortOrder.LATEST)
                        res.data.sortedByDescending { it.date }
                    else res.data.sortedBy { it.date }

                    val astroImages = sortedImages.map { it.toAstroImage() }
                    images = astroImages
                    _homeScreenState.value = _homeScreenState.value.copy(
                        isLoading = false,
                        images = astroImages.groupByWeek()
                    )
                }
                is Response.Error -> {
                    _homeScreenState.value = _homeScreenState.value.copy(
                        error = res.error,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun getIndexOfImage(id: Int) {
        val index = images.indexOfFirst {
            it.id == id
        }
        selectedImageIndex = index
    }
}

private fun List<AstroImage>.groupByWeek(): Map<Int, List<AstroImage>> {
    val imagesGroupedByWeek = this.groupBy {
        (it.date.day / 7) + 1
    }
    return imagesGroupedByWeek
}
