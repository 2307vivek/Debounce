package dev.vivvvek.astro.ui.details

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun ImageDetailTopAppBar(
    onBackButtonClicked: () -> Unit,
) {
    TopAppBar(
        title = { },
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = {
            IconButton(onClick = { onBackButtonClicked() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back icon"
                )
            }
        },
        elevation = 0.dp,
    )
}