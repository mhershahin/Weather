package com.service.utils

import com.service.entity.Result
import retrofit2.Response
import java.net.UnknownHostException

const val NO_INTERNET_CONNECTION = -10
const val GENERIC_API_ERROR = -1

suspend fun <R> makeApiCall(
    call: suspend () -> Result<R>,
    errorCode: Int = GENERIC_API_ERROR,
) = try {
    call()
} catch (e: Exception) {
    if (e is UnknownHostException) {
        Result.Error(NO_INTERNET_CONNECTION, e.message)
    } else {
        Result.Error(errorCode, e.message)
    }
}

fun <R> analyzeResponse(response: Response<R>): Result<R> = when {
    response.isSuccessful -> Result.Success(response.body())
    else -> Result.Error(response.code(), response.message())
}
