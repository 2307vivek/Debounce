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
package dev.vivvvek.astro.data.android.di

import android.content.Context
import android.content.res.AssetManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.vivvvek.astro.data.android.DefaultAstroRepository
import dev.vivvvek.astro.data.android.DefaultJsonProvider
import dev.vivvvek.astro.data.android.JsonProvider
import dev.vivvvek.astro.domain.AstroRepository
import javax.inject.Singleton

@Module(includes = [DatasourceModule.RepositoryModule::class])
@InstallIn(SingletonComponent::class)
class DatasourceModule {

    @Provides
    @Singleton
    fun provideAssetManager(@ApplicationContext context: Context): AssetManager {
        return context.assets
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        fun provideDefaultAstroRepository(
            repository: DefaultAstroRepository
        ): AstroRepository

        @Binds
        fun provideJson(
            jsonProvider: DefaultJsonProvider
        ): JsonProvider
    }
}
