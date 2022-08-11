package dev.vivvvek.astro.data.android

import java.io.FileInputStream
import java.io.InputStream

class TestJsonProvider : JsonProvider {
    override suspend fun provideJsonInputStream(fileName: String): InputStream {
        return FileInputStream("../data-android/src/main/assets/$fileName")
    }
}