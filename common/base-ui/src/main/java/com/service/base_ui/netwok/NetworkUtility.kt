package com.service.base_ui.netwok

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
private fun Context.observeConnectivityAsFlow(): Flow<ConnectionState> = callbackFlow @androidx.annotation.RequiresPermission(
    android.Manifest.permission.ACCESS_NETWORK_STATE
) {
    val connectivityManager = getSystemService(ConnectivityManager::class.java)

    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(
            network: Network,
            capabilities: NetworkCapabilities
        ) {
            val hasInternet = capabilities
                .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            val isValidated = capabilities
                .hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

            if (hasInternet && isValidated) {
                trySend(ConnectionState.Available)
            } else {
                trySend(ConnectionState.Unavailable)
            }
        }

        override fun onLost(network: Network) {
            trySend(ConnectionState.Unavailable)
        }

        override fun onUnavailable() {
            trySend(ConnectionState.Unavailable)
        }
    }

    val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()

    connectivityManager.registerNetworkCallback(request, callback)

    // Emit initial state
    trySend(getCurrentConnectivityState(connectivityManager))

    awaitClose {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}.distinctUntilChanged()

@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
private fun getCurrentConnectivityState(
    connectivityManager: ConnectivityManager
): ConnectionState {
    val activeNetwork = connectivityManager.activeNetwork
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

    val connected = capabilities != null
            && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)

    return if (connected) ConnectionState.Available else ConnectionState.Unavailable
}
@RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
@Composable
fun rememberConnectivityState(): State<ConnectionState> {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    return produceState(
        initialValue = getCurrentConnectivityState(
            context.getSystemService(ConnectivityManager::class.java)
        )
    ) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            context.observeConnectivityAsFlow().collect { value = it }
        }
    }
}