package dev.vivvvek.astro.domain

sealed class Response<T> {
    class Success<T>(val data: T): Response<T>()
    class Error<T>(val error: String): Response<T>()
}