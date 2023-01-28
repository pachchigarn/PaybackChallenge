package com.nishantp.payback.main.domain.usecase

import com.nishantp.payback.main.domain.model.Image
import com.nishantp.payback.main.domain.repository.ImageRepository
import com.nishantp.payback.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchImagesUseCase(private val repository: ImageRepository) {

    suspend operator fun invoke(query: String): Flow<DataState<List<Image>>> = flow {
        emit(value = repository.getImages(query = query))
    }
}