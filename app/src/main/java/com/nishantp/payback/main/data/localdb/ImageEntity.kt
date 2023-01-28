package com.nishantp.payback.main.data.localdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nishantp.payback.constants.AppConstants

@Entity(tableName = AppConstants.TABLE_NAME)
data class ImageEntity(
    @PrimaryKey val id: Int,
    val previewImageUrl: String,
    val imageUrl: String,
    val username: String,
    val tags: String,
    val likes: Int,
    val downloads: Int,
    val comments: Int
)