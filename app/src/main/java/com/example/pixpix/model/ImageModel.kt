package com.example.pixpix.model


data class ImageModel(
    val downloads: Int,
    val favorites: Int,
    val id: Int,
    val largeImageURL: String,
    val likes: Int,
    val pageURL: String,
    val previewURL: String,
    val tags: String,
    val type: String,
    val user: String,
    val userImageURL: String,
    val views: Int,
    val webformatURL: String,
)


