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
import dev.vivvvek.astro.domain.AstroRepository
import dev.vivvvek.astro.domain.Response
import dev.vivvvek.astro.domain.SortOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AstroRepository
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState

    fun getAllImages(sortOrder: SortOrder) {
        viewModelScope.launch {
            _homeScreenState.value = _homeScreenState.value.copy(isLoading = true)
            when (val res = repository.getAllImages()) {
                is Response.Success -> {
                    _homeScreenState.value = if (sortOrder == SortOrder.LATEST)
                        _homeScreenState.value.copy(
                            isLoading = false,
                            images = res.data.sortedByDescending { it.date }
                        )
                    else _homeScreenState.value.copy(
                        isLoading = false,
                        images = res.data.sortedBy { it.date }
                    )
                }
                is Response.Error -> {
                    _homeScreenState.value = _homeScreenState.value.copy(error = res.error)
                }
            }
        }
    }
}
