package com.service.utils

import java.net.UnknownHostException
import com.service.entity.Result
import retrofit2.Response

const val NO_INTERNET_CONNECTION = -10

suspend fun <R> makeApiCall(
    call: suspend () -> Result<R>,
    errorCode: Int = 4567
) = try {
    call()
} catch (e: Exception) {
    if (e is UnknownHostException) {
        Result.Error(NO_INTERNET_CONNECTION,e.message)
    } else {
        Result.Error(errorCode, e.message)
    }
}

fun <R> analyzeResponse(response: Response<R>): Result<R> {
    return when {
        response.isSuccessful -> {
            Result.Success(response.body())
        }
        else -> {
            Result.Error(
                    response.code(),
                    response.message()
            )
        }
    }
}