package com.service.base_ui.netwok

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}