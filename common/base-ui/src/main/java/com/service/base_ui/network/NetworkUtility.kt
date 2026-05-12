package com.service.base_ui.network

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
@RequiresPermission(value = "android.permission.ACCESS_NETWORK_STATE")
@ExperimentalCoroutinesApi
private fun Context.observeConnectivityAsFlow(): Flow<ConnectionState> = callbackFlow @androidx.annotation.RequiresPermission(
    android.Manifest.permission.ACCESS_NETWORK_STATE
) {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    trySend(getCurrentConnectivityState(connectivityManager))

    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            trySend(ConnectionState.Available)
        }

        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
        override fun onLost(network: Network) {
            trySend(getCurrentConnectivityState(connectivityManager))
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities,
        ) {
            val hasInternet = networkCapabilities
                .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            trySend(
                if (hasInternet) ConnectionState.Available else ConnectionState.Unavailable
            )
        }
    }

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    connectivityManager.registerNetworkCallback(networkRequest, callback)

    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
private fun getCurrentConnectivityState(
    connectivityManager: ConnectivityManager,
): ConnectionState {
    val activeNetwork = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    val hasInternet = capabilities
        ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    return if (hasInternet) ConnectionState.Available else ConnectionState.Unavailable
}
@RequiresPermission(value = "android.permission.ACCESS_NETWORK_STATE")
@ExperimentalCoroutinesApi
@Composable
fun connectivityState(): State<ConnectionState> {
    val context = LocalContext.current
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return produceState(initialValue = getCurrentConnectivityState(connectivityManager)) {
        context.observeConnectivityAsFlow()
            .distinctUntilChanged()
            .collect { value = it }
    }
}
@RequiresPermission(value = "android.permission.ACCESS_NETWORK_STATE")
@ExperimentalCoroutinesApi
@Composable
fun ConnectivityStatus(isConnectedCallback: (isConnected: Boolean) -> Unit) {
    val connection by connectivityState()
    val isConnected = connection == ConnectionState.Available
    LaunchedEffect(isConnected) {
        isConnectedCallback.invoke(isConnected)
    }
}
