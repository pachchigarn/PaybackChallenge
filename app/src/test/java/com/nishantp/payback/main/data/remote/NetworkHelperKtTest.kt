package com.nishantp.payback.main.data.remote

import com.nishantp.payback.utils.DataState
import com.nishantp.payback.utils.UiText
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class NetworkHelperKtTest {

    @Test
    fun `when lambda returns successfully then it should emit the result as success`() {
        runTest {
            val lambdaResult = true
            val result = safeApiCall(UnconfinedTestDispatcher()) { lambdaResult }
            assertEquals(DataState.Success(lambdaResult), result)
        }
    }

    @Test
    fun `when lambda throws IOException then it should emit the result as NetworkError`() {
        val ioException = IOException()
        runTest {
            val result = safeApiCall(UnconfinedTestDispatcher()) { throw ioException }
            assertEquals(DataState.ErrorHandler(exception = ioException, errorMessage = UiText.DynamicString(value = "Network Exception")), result)
        }
    }

    @Test
    fun `when lambda throws HttpException then it should emit the result as UnknownError`() {
        val errorBody = "{\"errors\": [\"Unexpected parameter\"]}".toResponseBody("application/json".toMediaTypeOrNull())
        val httpException = HttpException(Response.error<Any>(422, errorBody))
        runTest {
            val result = safeApiCall(UnconfinedTestDispatcher()) { throw httpException }
            assertEquals(DataState.ErrorHandler(exception = httpException, errorMessage = UiText.DynamicString(value = "Unknown Error ${httpException.response()?.code()}")), result)
        }
    }
}