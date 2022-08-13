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
package dev.vivvvek.astro.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.vivvvek.astro.domain.models.AstroImage
import dev.vivvvek.astro.domain.models.Date
import okhttp3.internal.trimSubstring

@Composable
fun ImageDetails(
    modifier: Modifier = Modifier,
    image: AstroImage
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(
                enabled = true,
                state = scrollState
            )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.url)
                .crossfade(true)
                .build(),
            contentDescription = image.title,
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(220, 220, 220))
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.height(12.dp))
        ImageDetailContent(image = image)
    }
}

@Composable
fun ImageDetailContent(
    modifier: Modifier = Modifier,
    image: AstroImage
) {
    Column(modifier = modifier) {
        ImageNameAndDate(
            name = image.title,
            date = image.date,
            copyright = image.copyright
        )
        Spacer(modifier = Modifier.height(24.dp))

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = image.explanation,
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Normal),
                lineHeight = 22.sp
            )
        }
        Spacer(modifier = Modifier.height(72.dp))
    }
}

@Composable
fun ImageNameAndDate(
    name: String,
    date: Date,
    copyright: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = name,
            style = MaterialTheme.typography.h4
        )
        date.apply {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = "${dayOfWeek.trimSubstring(0, 3)}, $monthName $day $year",
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
