package com.nishantp.payback.main.domain.usecase

import com.nishantp.payback.main.domain.model.Image
import com.nishantp.payback.main.domain.repository.ImageRepository
import com.nishantp.payback.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetImageDetailsUseCase(private val repository: ImageRepository) {

    suspend operator fun invoke(imageId: Int): Flow<DataState<Image>> = flow {
        emit(value = repository.getImage(imageId))
    }
}