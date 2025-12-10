package com.example.domain.model

data class Meal(
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val category: String?,          // e.g. "Dessert"
    val area: String?,              // e.g. "Uruguayan"
    val instructions: String?,      // long text
    val ingredientsText: String?,   // preformatted multi-line: "100g Sugar\n350g Milk\n..."
    val youtubeUrl: String?,        // strYoutube
    val sourceUrl: String?,         // strSource
    val isFavorite: Boolean
)
