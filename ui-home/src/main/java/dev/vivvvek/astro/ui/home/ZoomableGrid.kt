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

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * A [LazyColumn] which you can zoom in or out to change the number of columns accordingly.
 *
 *  @param initialColumns the number of columns the grid starts with.
 *  @param maximumColumns the maximum number of columns the grid can have. After which
 *  zooming in will have no effect on the number of columns.
 * */

@Composable
internal fun ZoomableGrid(
    modifier: Modifier = Modifier,
    initialColumns: Int = 3,
    maximumColumns: Int = 6,
    animationSpec: AnimationSpec<Float>,
    state: LazyListState = rememberLazyListState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: LazyListScope.(columns: Int) -> Unit
) {
    var maxColumns = maximumColumns
    var columns by remember { mutableStateOf(initialColumns) }
    var scale by remember { mutableStateOf(1f) }
    val gridZoom = remember { Animatable(1f) }

    if (initialColumns < 1) columns = 1
    if (maximumColumns < 1) maxColumns = 1

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
                (columns + 1).coerceAtMost(maxColumns)
            }
            gridZoom.animateTo(
                targetValue = 1f,
                animationSpec = animationSpec
            )
            scale = 1f
        }
    }
    Box(modifier = Modifier.transformable(transformableState)) {
        LazyColumn(
            contentPadding = contentPadding,
            state = state,
            verticalArrangement = verticalArrangement,
            modifier = modifier
                .graphicsLayer {
                    scaleY = gridZoom.value
                    scaleX = gridZoom.value
                },
        ) {
            this.content(columns)
        }
    }
}

internal fun lerp(a: Float, b: Float, fraction: Float) =
    a + (b - a) * fraction

internal val Float.isZoomedIn: Boolean
    get() = this > 1
