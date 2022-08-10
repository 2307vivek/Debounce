package dev.vivvvek.astro.data.android

import org.junit.Before
import org.junit.Test
import java.io.FileInputStream
import java.io.InputStream
import com.google.common.truth.Truth.assertThat
import dev.vivvvek.astro.domain.Response
import kotlinx.coroutines.runBlocking


class AstroDataSourceTest {

    private lateinit var dataSource: AstroDataSource
    private lateinit var inputStream: InputStream

    private val jsonFilePath = "../data-android/src/main/assets/data.json"

    @Before
    fun setup() {
        dataSource = AstroDataSource()
        inputStream = FileInputStream(jsonFilePath)
    }

    @Test
    fun `check if the json file is not null, return_true`() {
        val jsonString = dataSource.readJson(inputStream)
        assertThat(jsonString).isNotNull()
    }

    @Test
    fun `check if the json file is not Empty, return_true`() {
        val jsonString = dataSource.readJson(inputStream)
        assertThat(jsonString).isNotEmpty()
    }

    @Test
    fun `check the json file is valid return_true`() = runBlocking {
        val response = dataSource.getImages(inputStream)
        assertThat(response).isInstanceOf(Response.Success::class.java)
    }

    @Test
    fun `check correct model deserialization, return_true` () = runBlocking {
        val response = dataSource.getImages(inputStream)
        assertThat(response).isInstanceOf(Response.Success::class.java)

        when(response) {
            is Response.Success -> {
                assertThat(response.data[0].title).isEqualTo("Starburst Galaxy M94 from Hubble")
                assertThat(response.data[1].title).isEqualTo("M27: The Dumbbell Nebula")
                assertThat(response.data[2].title).isEqualTo("Electric Night")
            }
            else -> {}
        }
    }
}