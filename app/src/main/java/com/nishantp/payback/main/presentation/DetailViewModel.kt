package com.nishantp.payback.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nishantp.payback.main.domain.model.Image
import com.nishantp.payback.main.domain.usecase.GetImageDetailsUseCase
import com.nishantp.payback.utils.DataState
import com.nishantp.payback.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getImageDetailsUseCase: GetImageDetailsUseCase) :
    ViewModel() {


    private val _imageDetailState = MutableLiveData(
        ImageDetailState(
            image = null,
            isError = false,
            errorMessage = null,
            isLoading = true
        )
    )

    val imageDetailState: LiveData<ImageDetailState> = _imageDetailState

    fun loadImageDetails(imageId: Int) {
        viewModelScope.launch {
            getImageDetailsUseCase(imageId = imageId).onEach {
                when (it) {
                    is DataState.Success -> {
                        _imageDetailState.value = imageDetailState.value?.copy(
                            image = it.data,
                            isLoading = false
                        )

                    }

                    is DataState.ErrorHandler -> {
                        _imageDetailState.value = imageDetailState.value?.copy(
                            isError = true,
                            errorMessage = it.errorMessage,
                            isLoading = false
                        )
                    }
                }

            }.launchIn(viewModelScope)
        }
    }
}

data class ImageDetailState(
    val image: Image? = null,
    val isError: Boolean,
    val errorMessage: UiText? = null,
    val isLoading: Boolean = true
)