package com.service.splash_domain.usecase.location.provider

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.service.entity.LatLon
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject
import kotlin.coroutines.resume

internal class GetLocationProviderUseCaseImpl @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) : GetLocationProviderUseCase {

    override suspend fun invoke(): LatLon {
        val result = withTimeoutOrNull(5_000L) {
            try {
                fetchCurrent() ?: fetchLast()
            } catch (e: SecurityException) {
                null
            }
        }
        return result ?: LatLon(40.7128, -74.0060)
    }

    @Suppress("MissingPermission")
    private suspend fun fetchCurrent(): LatLon? = suspendCancellableCoroutine { cont ->
        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
            .addOnSuccessListener { loc ->
                val ll = loc?.let { LatLon(it.latitude, it.longitude) }
                if (cont.isActive) cont.resume(ll)
            }
            .addOnFailureListener { if (cont.isActive) cont.resume(null) }
    }

    @Suppress("MissingPermission")
    private suspend fun fetchLast(): LatLon? = suspendCancellableCoroutine { cont ->
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { loc ->
                val ll = loc?.let { LatLon(it.latitude, it.longitude) }
                if (cont.isActive) cont.resume(ll)
            }
            .addOnFailureListener { if (cont.isActive) cont.resume(null) }
    }
}