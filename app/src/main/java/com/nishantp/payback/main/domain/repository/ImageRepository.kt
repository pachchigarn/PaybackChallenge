package com.nishantp.payback.main.domain.repository

import com.nishantp.payback.main.data.localdb.ImageEntity
import com.nishantp.payback.main.domain.model.Image
import com.nishantp.payback.utils.DataState

interface ImageRepository {

    suspend fun getImages(query: String): DataState<List<Image>>

    suspend fun getImage(id: Int): DataState<Image>

    suspend fun getImagesLocal(): DataState<List<Image>>

    suspend fun saveImages(imageEntityList: List<ImageEntity>)
}