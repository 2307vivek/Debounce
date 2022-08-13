package dev.vivvvek.astro.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import dev.vivvvek.astro.ui.home.AstroViewModel

@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: AstroViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "DetailsScreen")
    }
}