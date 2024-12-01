package com.example.finalassignment3.realThirdTask.realThirdTask.model

data class ImageResponse(
    val total: Int,         // Total number of images
    val totalPages: Int,    // Total number of pages
    val items: List<ImageItem> // List of images
)

data class ImageItem(
    val imageUrl: String,    // Full-size image URL
    val previewUrl: String   // Preview image URL
)
