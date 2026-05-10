package com.service.entity

sealed class Result<out S> {
    data class Success<S>(val data: S?) : Result<S>()
    data class Error<E>(val code: Int, val message: String?) : Result<E>()
}