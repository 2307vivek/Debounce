package dev.vivvvek.astro.domain.models

data class AstroImage(
    val title: String,
    val copyright: String,
    val date: Date,
    val explanation: String,
    val url: String,
    val hdUrl: String
)
