package dev.vivvvek.astro.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun DetailsScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "DetailsScreen")
    }
}