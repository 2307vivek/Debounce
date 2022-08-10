package dev.vivvvek.astro.data.android.di

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.vivvvek.astro.data.android.DefaultAstroRepository
import dev.vivvvek.astro.domain.AstroRepository
import java.io.InputStream
import javax.inject.Singleton

@Module(includes = [DatasourceModule.RepositoryModule::class])
@InstallIn(SingletonComponent::class)
class DatasourceModule {

    @Provides
    @Singleton
    fun provideInputStream(@ApplicationContext context: Context) : InputStream {
        return context.assets.open("data.json")
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface RepositoryModule {
        @Binds
        abstract fun provideDefaultAstroRepository(
            repository: DefaultAstroRepository
        ) : AstroRepository
    }
}