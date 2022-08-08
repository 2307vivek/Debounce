/*
 * MIT License
 *
 * Copyright (c) 2022 Vivek Singh
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.vivvvek.astro.ui.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    ZoomableGrid(
        initialColumns = 3,
        maximumColumns = 7
    )
}

/**
 * A [LazyVerticalGrid] which you can zoom in or out to change the number of columns accordingly.
 *
 *  @param initialColumns the number of columns the grid starts with.
 *  @param maximumColumns the maximum number of columns the grid can have. After which
 *  zooming in will have no effect on the number of columns.
* */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ZoomableGrid(
    initialColumns: Int,
    maximumColumns: Int,
    modifier: Modifier = Modifier
) {
    var columns by remember { mutableStateOf(initialColumns) }
    var scale by remember { mutableStateOf(1f) }
    val gridZoom = remember { Animatable(1f) }

    val coroutineScope = rememberCoroutineScope()
    val transformableState = rememberTransformableState { zoom, _, _ ->
        scale *= zoom
        coroutineScope.launch { gridZoom.snapTo(lerp(0.7f, 1f, scale)) }
    }
    LaunchedEffect(transformableState.isTransformInProgress) {
        if (!transformableState.isTransformInProgress && gridZoom.value != 1f) {
            columns = if (scale.isZoomedIn) {
                (columns - 1).coerceAtLeast(1)
            } else {
                (columns + 1).coerceAtMost(maximumColumns)
            }
            gridZoom.animateTo(
                targetValue = 1f,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
            )
            scale = 1f
        }
    }
    Box(modifier = Modifier.transformable(transformableState)) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(columns),
            contentPadding = PaddingValues(2.dp),
            modifier = modifier
                .graphicsLayer {
                    scaleY = gridZoom.value
                    scaleX = gridZoom.value
                }
        ) {
            items(50) {
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .aspectRatio(1f)
                        .background(Color.Green)
                )
            }
        }
    }
}

internal fun lerp(a: Float, b: Float, fraction: Float) =
    a + (b - a) * fraction

internal val Float.isZoomedIn: Boolean
    get() = this > 1
