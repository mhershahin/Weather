package com.service.utils.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


interface DispatcherProvider {
    val main: CoroutineDispatcher
        get() = Dispatchers.Main
    val default
        get() = Dispatchers.Default
    val io
        get() = Dispatchers.IO
    val unconfined
        get() = Dispatchers.Unconfined
}