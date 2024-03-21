package com.example.lr8.models

data class Artwork(
    var id: Int = -1,
    var title: String,
    var year: Int,
    var description: String,
    var author: Author,
    var genre: ArtGenre
)