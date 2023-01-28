package com.nishantp.payback.utils

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class ErrorHandler(val exception: Exception, val errorMessage: UiText) :
        DataState<Nothing>()
}
