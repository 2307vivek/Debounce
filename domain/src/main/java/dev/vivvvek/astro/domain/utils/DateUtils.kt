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
package dev.vivvvek.astro.domain.utils

import dev.vivvvek.astro.domain.models.Date
import java.time.LocalDate
import java.util.Locale

class DateUtils {
    fun getDateFromString(stringDate: String): Date {
        val date = LocalDate.parse(stringDate)
        return Date(
            year = date.year,
            monthName = date.month.toString().lowercase()
                .replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString()
                },
            dayOfWeek = date.dayOfWeek.toString().lowercase()
                .replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString()
                },
            day = date.dayOfMonth
        )
    }
}
