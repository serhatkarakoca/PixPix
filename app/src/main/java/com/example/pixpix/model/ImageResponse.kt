package com.example.pixpix.model


data class ImageResponse(
    val hits: List<ImageModel>,
    val totalHits: Int,
    val total: Int
)