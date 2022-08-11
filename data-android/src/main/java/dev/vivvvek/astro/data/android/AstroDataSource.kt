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

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.vivvvek.astro.domain.AstroImage
import dev.vivvvek.astro.domain.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AstroDataSource @Inject constructor(private val jsonProvider: JsonProvider) {

    suspend fun getImages(): Response<List<AstroImage>> {
        return withContext(Dispatchers.IO) {
            try {
                val jsonString = readJson(jsonProvider.provideJsonInputStream("data.json"))
                val imageType = object : TypeToken<List<AstroImage>>() {}.type
                val images: List<AstroImage> = Gson().fromJson(jsonString, imageType)
                Response.Success(images)
            } catch (e: IOException) {
                Response.Error("Something happened")
            }
        }
    }

    fun readJson(inputStream: InputStream): String {
        return inputStream
            .bufferedReader()
            .use { it.readText() }
    }
}
