package com.nishantp.payback.main.domain.model

data class Image(
    val id: Int,
    val previewImageUrl: String,
    val imageUrl: String,
    val username: String,
    val tags: String,
    val likes: Int,
    val downloads: Int,
    val comments: Int
)
