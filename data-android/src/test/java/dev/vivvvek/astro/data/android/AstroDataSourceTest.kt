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
import dev.vivvvek.astro.domain.Response
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.io.FileInputStream
import java.io.InputStream

class AstroDataSourceTest {

    private lateinit var dataSource: AstroDataSource
    private lateinit var jsonProvider: TestJsonProvider
    @Before
    fun setup() {
        jsonProvider = TestJsonProvider()
        dataSource = AstroDataSource(jsonProvider)
    }

    @Test
    fun `check if the json file is not null, return_true`() = runBlocking {
        val jsonString = dataSource.readJson(jsonProvider.provideJsonInputStream("data.json"))
        assertThat(jsonString).isNotNull()
    }

    @Test
    fun `check if the json file is not Empty, return_true`() = runBlocking {
        val jsonString = dataSource.readJson(jsonProvider.provideJsonInputStream("data.json"))
        assertThat(jsonString).isNotEmpty()
    }

    @Test
    fun `check the json file is valid return_true`() = runBlocking {
        val response = dataSource.getImages()
        assertThat(response).isInstanceOf(Response.Success::class.java)
    }

    @Test
    fun `check correct model deserialization, return_true`() = runBlocking {
        val response = dataSource.getImages()
        assertThat(response).isInstanceOf(Response.Success::class.java)

        when (response) {
            is Response.Success -> {
                assertThat(response.data[0].title).isEqualTo("Starburst Galaxy M94 from Hubble")
                assertThat(response.data[1].title).isEqualTo("M27: The Dumbbell Nebula")
                assertThat(response.data[2].title).isEqualTo("Electric Night")
            }
            else -> {}
        }
    }
}
