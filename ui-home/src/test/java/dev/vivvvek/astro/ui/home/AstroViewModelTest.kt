package dev.vivvvek.astro.ui.home

import com.google.common.truth.Truth
import dev.vivvvek.astro.FakeRepository
import dev.vivvvek.astro.domain.SortOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AstroViewModelTest {

    private lateinit var astroViewModel: AstroViewModel
    private lateinit var repository: FakeRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        repository = FakeRepository()
        astroViewModel = AstroViewModel(repository, StandardTestDispatcher())
    }

    @Test
    fun `observe home screen state with latest images, returns_true`() = runBlocking {
        val resultList = arrayListOf<HomeScreenState>()

        repository.shouldCauseError(false)
        astroViewModel.getAllImages(SortOrder.LATEST)
        val job = launch {
            astroViewModel.homeScreenState.toList(resultList)
        }

        Truth.assertThat(resultList.last().error).isNull()
        Truth.assertThat(resultList.last().images[0]?.get(0)?.title).isEqualTo("Title 3")

        job.cancel()
    }
}