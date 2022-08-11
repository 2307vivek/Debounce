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
package dev.vivvvek.astro.data.android

import com.google.common.truth.Truth.assertThat
import dev.vivvvek.astro.domain.utils.DateUtils
import org.junit.Before
import org.junit.Test

class DateUtilsTest {

    private lateinit var dateUtil: DateUtils

    @Before
    fun setUp() {
        dateUtil = DateUtils()
    }

    @Test
    fun `check returns correct month name from date, returns true`() {
        val monthName = dateUtil.getDateFromString("2033-03-25").monthName
        assertThat(monthName).isEqualTo("March")
    }

    @Test
    fun `check returns correct day name from date, returns true`() {
        val monthName = dateUtil.getDateFromString("2022-08-11").dayOfWeek
        assertThat(monthName).isEqualTo("Thursday")
    }

    @Test
    fun `check returns correct year from date, returns true`() {
        val monthName = dateUtil.getDateFromString("2022-08-11").year
        assertThat(monthName).isEqualTo(2022)
    }

    @Test
    fun `check returns correct day from date, returns true`() {
        val monthName = dateUtil.getDateFromString("2022-08-11").day
        assertThat(monthName).isEqualTo(11)
    }
}
