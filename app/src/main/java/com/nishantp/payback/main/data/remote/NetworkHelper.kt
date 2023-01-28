package com.nishantp.payback.main.data.remote

import com.nishantp.payback.utils.DataState
import com.nishantp.payback.utils.UiText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): DataState<T> {
    return withContext(dispatcher) {
        try {
            DataState.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> DataState.ErrorHandler(
                    exception = throwable,
                    errorMessage = UiText.DynamicString(value = "Network Exception")
                )
                is HttpException -> {
                    DataState.ErrorHandler(
                        throwable,
                        UiText.DynamicString(value = "Unknown Error ${throwable.response()?.code()}")
                    )
                }
                else -> {
                    DataState.ErrorHandler(
                        Exception(),
                        UiText.DynamicString(value = "Unknown Exception")
                    )
                }
            }
        }
    }
}