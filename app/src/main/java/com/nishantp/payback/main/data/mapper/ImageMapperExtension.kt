package com.nishantp.payback.main.data.mapper

import com.nishantp.payback.main.data.localdb.ImageEntity
import com.nishantp.payback.main.data.remote.dto.HitDto
import com.nishantp.payback.main.data.remote.dto.PixabayImageDto
import com.nishantp.payback.main.domain.model.Image

fun HitDto.toImage(): Image {
    return Image(
        id = id,
        previewImageUrl = previewURL,
        imageUrl = largeImageURL,
        username = user,
        tags = tags,
        likes = likes,
        downloads = downloads,
        comments = comments
    )
}

fun PixabayImageDto.toListOfImages(): List<Image> {
    return hitDtos.map { it.toImage() }
}

fun Image.toImageEntity(): ImageEntity {
    return ImageEntity(
        id = id,
        previewImageUrl = previewImageUrl,
        imageUrl = imageUrl,
        username = username,
        tags = tags,
        likes = likes,
        downloads = downloads,
        comments = comments
    )
}

fun List<Image>.toImageEntityList(): List<ImageEntity> {
    return this.map { it.toImageEntity() }
}

fun ImageEntity.toImage(): Image {
    return Image(
        id = id,
        previewImageUrl = previewImageUrl,
        imageUrl = imageUrl,
        username = username,
        tags = tags,
        likes = likes,
        downloads = downloads,
        comments = comments
    )
}

fun List<ImageEntity>.toImageList(): List<Image> {
    return this.map { it.toImage() }
}