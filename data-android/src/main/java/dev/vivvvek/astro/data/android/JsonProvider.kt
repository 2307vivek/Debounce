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
    suspend fun provideJsonInputStream(fileName: String) : InputStream
}