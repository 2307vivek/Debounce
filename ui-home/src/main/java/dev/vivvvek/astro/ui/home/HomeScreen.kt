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

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.vivvvek.astro.domain.SortOrder
import dev.vivvvek.astro.domain.models.AstroImage

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    viewModel: AstroViewModel,
    navController: NavController
) {
    val state by viewModel.homeScreenState.collectAsState()

    var sortOrder by rememberSaveable { mutableStateOf(SortOrder.LATEST) }

    LaunchedEffect(sortOrder) {
        viewModel.getAllImages(sortOrder)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            AstroTopAppBar(
                onMenuItemClick = {
                    if (it == sortOrder) return@AstroTopAppBar
                    sortOrder = if (it == SortOrder.LATEST)
                        SortOrder.LATEST
                    else SortOrder.OLDEST
                }
            )
        },
    ) {
        Box(
            modifier = Modifier.padding(it),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.error == null && state.images.isNotEmpty()) {
                AnimatedContent(targetState = sortOrder) {
                    ImageGrid(
                        imagesGrouped = state.images,
                        modifier = Modifier.fillMaxHeight().navigationBarsPadding(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        onImageClick = { id ->
                            viewModel.getIndexOfImage(id)
                            navController.navigate("DetailScreen")
                        }
                    )
                }
            }
            if (state.error != null) {
                Text(text = state.error ?: "")
            }
        }
    }
}

@Composable
fun ImageGrid(
    modifier: Modifier = Modifier,
    onImageClick: (Int) -> Unit,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    imagesGrouped: Map<Int, List<AstroImage>>
) {
    ZoomableGrid(
        maximumColumns = 7,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        contentPadding = contentPadding,
        modifier = modifier,
    ) { columns ->
        imagesGrouped.forEach { (weekNumber, imagesByWeek) ->
            item {
                GridHeader(
                    weekNumber = weekNumber,
                    monthName = imagesByWeek[0].date.monthName,
                    numberOfImages = imagesByWeek.size
                )
            }
            items(imagesByWeek.chunked(columns)) { images ->
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    images.forEach { image ->
                        AstroGridItem(
                            image = image,
                            modifier = Modifier
                                .weight(1f)
                                .padding(2.dp)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color(250, 250, 250)),
                            onImageClick = onImageClick
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AstroGridItem(
    image: AstroImage,
    onImageClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.clickable {
            onImageClick(image.id)
        },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.url)
                .crossfade(true)
                .build(),
            contentDescription = image.title,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun AstroTopAppBar(
    onMenuItemClick: (SortOrder) -> Unit,
    modifier: Modifier = Modifier
) {
    var isMenuVisible by remember { mutableStateOf(false) }
    TopAppBar(
        title = { Text(text = "Astro") },
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface,
        actions = {
            IconButton(onClick = { isMenuVisible = !isMenuVisible }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu Icon")
            }
            DropdownMenu(
                expanded = isMenuVisible,
                onDismissRequest = { isMenuVisible = !isMenuVisible }
            ) {
                DropdownMenuItem(
                    onClick = {
                        isMenuVisible = !isMenuVisible
                        onMenuItemClick(SortOrder.LATEST)
                    }
                ) {
                    Text(text = "Sort by Latest")
                }
                DropdownMenuItem(
                    onClick = {
                        isMenuVisible = !isMenuVisible
                        onMenuItemClick(SortOrder.OLDEST)
                    }
                ) {
                    Text(text = "Sort by Oldest")
                }
            }
        },
        modifier = modifier
    )
}
