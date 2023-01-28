package com.nishantp.payback.main.data.remote

import com.nishantp.payback.constants.AppConstants
import com.nishantp.payback.main.data.remote.dto.PixabayImageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchImagesApi {

    @GET("api/")
    suspend fun getImages(@Query("q") q: String, @Query("key") key: String = AppConstants.PIXABAY_API_KEY): PixabayImageDto
}