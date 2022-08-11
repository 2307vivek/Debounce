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

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.vivvvek.astro.domain.SortOrder

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val state by viewModel.homeScreenState.collectAsState()

    Button(onClick = { viewModel.getAllImages(SortOrder.LATEST) }) {
        Text(text = "dufhduihf")
    }
    if (state.isLoading) {
        Text(text = "Loading")
    }

    if (state.error == null && state.images.isNotEmpty()) {
        ZoomableGrid(
            maximumColumns = 7,
            contentPadding = PaddingValues(2.dp)
        ) {
            items(state.images) { image ->
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image.url)
                            .crossfade(true)
                            .build(),
                        contentDescription = image.title,
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                }
            }
        }
    }

    if (state.error != null) {
        Text(text = state.error ?: "")
    }
}
