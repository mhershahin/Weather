package com.service.utils

interface Mapper<T, R> {
    operator fun invoke(param: T): R
}

interface MapperWithArguments<T, R, A> {
    operator fun invoke(param: T, arguments: A): R
}