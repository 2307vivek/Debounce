package dev.vivvvek.astro.data.android

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.vivvvek.astro.domain.AstroImage
import dev.vivvvek.astro.domain.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream

class AstroDataSource(private val inputStream: InputStream) {

    suspend fun getImages() : Response<List<AstroImage>> {
        return withContext(Dispatchers.IO) {
            try {
                val jsonString = readJson(inputStream)
                val imageType = object: TypeToken<List<AstroImage>>() {}.type
                val images: List<AstroImage> = Gson().fromJson(jsonString, imageType)
                Response.Success(images)
            } catch (e: IOException) {
                Response.Error("Something happened")
            }
        }
    }

    fun readJson(inputStream: InputStream) : String {
        return inputStream
            .bufferedReader()
            .use { it.readText() }
    }
}