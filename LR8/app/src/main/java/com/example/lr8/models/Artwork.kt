package com.example.lr8.models

data class Artwork(
    var id: Int = -1,
    var title: String,
    var genre: String,
    var year: Int,
    var description: String,
    var authorId: Int, // Ідентифікатор автора твору
    var typeId: Int // Ідентифікатор типу твору
)