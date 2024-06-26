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
package dev.vivvvek.astro.domain.models

import dev.vivvvek.astro.domain.utils.DateUtils

data class Image(
    val title: String,
    val copyright: String?,
    val date: String,
    val explanation: String,
    val url: String,
    val hdurl: String
)

fun Image.toAstroImage() = AstroImage(
    title = title,
    copyright = copyright ?: "Unknown",
    date = DateUtils().getDateFromString(date),
    explanation = explanation,
    url = url,
    hdUrl = hdurl
)
