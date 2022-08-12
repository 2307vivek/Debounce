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

import android.content.res.AssetManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultJsonProvider @Inject constructor(private val assetManager: AssetManager) : JsonProvider {
    override suspend fun provideJsonInputStream(fileName: String): InputStream {
        return withContext(Dispatchers.IO) {
            assetManager.open(fileName)
        }
    }
}

interface JsonProvider {
    suspend fun provideJsonInputStream(fileName: String): InputStream
}
