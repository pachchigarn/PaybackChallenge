package com.nishantp.payback.main.data.repository

import android.util.Log
import com.nishantp.payback.R
import com.nishantp.payback.main.data.localdb.ImageDao
import com.nishantp.payback.main.data.localdb.ImageEntity
import com.nishantp.payback.main.data.mapper.toImage
import com.nishantp.payback.main.data.mapper.toImageEntityList
import com.nishantp.payback.main.data.mapper.toImageList
import com.nishantp.payback.main.data.mapper.toListOfImages
import com.nishantp.payback.main.data.remote.SearchImagesApi
import com.nishantp.payback.main.data.remote.safeApiCall
import com.nishantp.payback.main.domain.model.Image
import com.nishantp.payback.main.domain.repository.ImageRepository
import com.nishantp.payback.utils.DataState
import com.nishantp.payback.utils.UiText
import kotlinx.coroutines.Dispatchers

class ImageRepositoryImpl(
    private val searchImagesApi: SearchImagesApi,
    private val imageDao: ImageDao
) :
    ImageRepository {

    override suspend fun getImages(query: String): DataState<List<Image>> {

        val data = safeApiCall(dispatcher = Dispatchers.IO) {
            searchImagesApi.getImages(q = query).toListOfImages()
        }

        when (data) {
            is DataState.Success -> {
                try {
                    if (data.data.isNotEmpty()) {
                        deletePreviouslySavedImagesFromLocalDb()
                        saveImages(imageEntityList = data.data.toImageEntityList())
                    } else {
                        return DataState.ErrorHandler(exception = Exception(), errorMessage = UiText.StringResource(R.string.no_search_results))
                    }

                } catch (exception: Exception) {
                    Log.d(ImageRepositoryImpl::class.java.simpleName, "Error while saving to local DB.")
                    return data
                }

                return DataState.Success(data = getImagesFromLocalDb())
            }
            else -> {
                return data
            }
        }
    }

    override suspend fun getImage(id: Int): DataState<Image> {
        return try {
            DataState.Success(data = imageDao.getImageById(id = id).toImage())
        } catch (exception: Exception) {
            DataState.ErrorHandler(exception = exception, errorMessage = UiText.StringResource(R.string.generic_error_message))
        }
    }

    override suspend fun getImagesLocal(): DataState<List<Image>> {
        return DataState.Success(data = getImagesFromLocalDb())
    }

    override suspend fun saveImages(imageEntityList: List<ImageEntity>) {
        imageDao.insertImages(listOfImages = imageEntityList)
    }

    private suspend fun getImagesFromLocalDb(): List<Image> {
        return imageDao.getImages().toImageList()
    }

    private suspend fun deletePreviouslySavedImagesFromLocalDb() {
        imageDao.nukeTable()
    }
}