package dev.vivvvek.astro.ui.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dev.vivvvek.astro.ui.home.AstroViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: AstroViewModel
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            ImageDetailTopAppBar { navController.navigateUp() }
        }
    ) {

        val pagerState = rememberPagerState()

        LaunchedEffect(viewModel.selectedImageIndex) {
            pagerState.scrollToPage(viewModel.selectedImageIndex)
        }
        HorizontalPager(
            state = pagerState,
            count = viewModel.images.size,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) { index ->
            val image = remember(index) { viewModel.images[index] }
            ImageDetails(
                image = image,
                modifier = Modifier.fillMaxSize()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}