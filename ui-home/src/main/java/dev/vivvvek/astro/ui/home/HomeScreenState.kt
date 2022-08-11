package dev.vivvvek.astro.ui.home

import dev.vivvvek.astro.domain.AstroImage

data class HomeScreenState(
    val isLoading: Boolean = false,
    val images: List<AstroImage> = emptyList(),
    val error: String? = null
)
