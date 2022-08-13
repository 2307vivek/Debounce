package dev.vivvvek.astro.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
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
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "DetailsScreen")
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Navigate back ${viewModel.selectedImageIndex}")
        }
    }
}