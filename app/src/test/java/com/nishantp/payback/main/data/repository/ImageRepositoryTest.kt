package com.nishantp.payback.main.data.repository

import com.nishantp.payback.main.data.localdb.ImageEntity
import com.nishantp.payback.main.data.mapper.toListOfImages
import com.nishantp.payback.main.data.remote.dto.HitDto
import com.nishantp.payback.main.data.remote.dto.PixabayImageDto
import com.nishantp.payback.main.domain.model.Image
import com.nishantp.payback.main.domain.repository.ImageRepository
import com.nishantp.payback.utils.DataState
import kotlin.random.Random

class ImageRepositoryTest : ImageRepository {

    override suspend fun getImages(query: String): DataState<List<Image>> {
        return DataState.Success(data = getFakeDto().toListOfImages())
    }

    override suspend fun getImage(id: Int): DataState<Image> {
        TODO("Not yet implemented")
    }

    override suspend fun getImagesLocal(): DataState<List<Image>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveImages(imageEntityList: List<ImageEntity>) {
        TODO("Not yet implemented")
    }

    private fun getFakeDto(): PixabayImageDto {
        return PixabayImageDto(hitDtos = getFakeListHitDto(), total = 500, totalHits = 12324)
    }

    private fun getFakeListHitDto(): List<HitDto> {
        return return (1..20).map {
            getFakeHitDto()
        }
    }

    private fun getFakeHitDto(): HitDto {
        return HitDto(
            collections = 12,
            comments = Random.nextInt(),
            downloads = Random.nextInt(),
            id = Random.nextInt(),
            imageHeight = 20,
            imageSize = 200,
            imageWidth = 200,
            largeImageURL = "",
            likes = Random.nextInt(),
            pageURL = "",
            previewHeight = 200,
            previewURL = "",
            previewWidth = 200,
            tags = "",
            type = "",
            user = "Fake User ${Random.nextInt()}",
            userId = Random.nextInt(),
            userImageURL = "",
            views = Random.nextInt(),
            webformatHeight = 200,
            webformatURL = "",
            webformatWidth = 200
        )
    }
}