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

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import dev.vivvvek.astro.FakeRepository
import dev.vivvvek.astro.domain.SortOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AstroViewModelTest {

    private lateinit var astroViewModel: AstroViewModel
    private lateinit var repository: FakeRepository
    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun setup() {
        testDispatcher = StandardTestDispatcher()
        repository = FakeRepository()
        astroViewModel = AstroViewModel(repository, testDispatcher)
    }

    @Test
    fun `check correct observation and modeling of images when success, returns_true`() = runBlocking {
        repository.shouldCauseError(false)
        astroViewModel.getAllImages(SortOrder.LATEST)

        astroViewModel.homeScreenState.test {
            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()

            testDispatcher.scheduler.runCurrent()
            val imagesState = awaitItem()
            assertThat(imagesState.isLoading).isFalse()
            assertThat(imagesState.error).isNull()
            assertThat(imagesState.images).isNotEmpty()

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `check correct observation and modeling of images when error, returns_true`() = runBlocking {
        repository.shouldCauseError(true)
        astroViewModel.getAllImages(SortOrder.LATEST)

        astroViewModel.homeScreenState.test {
            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()

            testDispatcher.scheduler.runCurrent()
            val imagesState = awaitItem()
            assertThat(imagesState.isLoading).isFalse()
            assertThat(imagesState.error).isNotNull()
            assertThat(imagesState.images).isEmpty()

            cancelAndConsumeRemainingEvents()
        }
    }
}
