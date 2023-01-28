package com.nishantp.payback.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nishantp.payback.main.domain.model.Image
import com.nishantp.payback.main.domain.usecase.SearchImagesUseCase
import com.nishantp.payback.utils.DataState
import com.nishantp.payback.utils.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val searchImagesUseCase: SearchImagesUseCase) :
    ViewModel() {

    private val _searchImageState = MutableLiveData(
        SearchImageState(
            images = emptyList(),
            isError = false,
            errorMessage = null,
            isLoading = false
        )
    )

    val searchImageState: LiveData<SearchImageState> = _searchImageState

    private val _query = MutableLiveData("")
    val query: LiveData<String> = _query

    fun getImages(query: String) {
        _query.value = query

        _searchImageState.value = searchImageState.value?.copy(
            isError = false,
            isLoading = true
        )

        viewModelScope.launch {
            searchImagesUseCase(query = query).onEach {
                when (it) {
                    is DataState.Success -> {
                        _searchImageState.value = searchImageState.value?.copy(
                            isError = false,
                            images = it.data,
                            isLoading = false
                        )
                    }

                    is DataState.ErrorHandler -> {
                        _searchImageState.value = searchImageState.value?.copy(
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

data class SearchImageState(
    val images: List<Image>,
    val isError: Boolean,
    val errorMessage: UiText? = null,
    val isLoading: Boolean = false
)