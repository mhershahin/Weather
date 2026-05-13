package com.service.splash_domain.usecase.location.provider

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.service.entity.LatLon
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume

internal class GetLocationProviderUseCaseImpl @Inject constructor(
    private val context: Context,
    private val dispatcherProvider: DispatcherProvider,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) : GetLocationProviderUseCase {

    override suspend fun invoke(): LatLon {
        val cords = withTimeoutOrNull(LOCATION_TIMEOUT_MILLIS) {
            try {
                fetchCurrent() ?: fetchLast()
            } catch (e: SecurityException) {
                null
            }
        }
        return if (cords != null) {
            val (lat, lon) = cords
            LatLon(lat, lon, resolveCityName(lat, lon))
        } else {
            FALLBACK_LOCATION
        }
    }

    private suspend fun resolveCityName(lat: Double, lon: Double): String? {
        if (!Geocoder.isPresent()) return null
        val geocoder = Geocoder(context, Locale.ENGLISH)

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            suspendCancellableCoroutine { cont ->
                geocoder.getFromLocation(lat, lon, 1, object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        cont.resume(addresses.firstOrNull()?.locality)
                    }
                    override fun onError(errorMessage: String?) {
                        cont.resume(null)
                    }
                })
            }
        } else {
            withContext(dispatcherProvider.io) {
                try {
                    @Suppress("DEPRECATION")
                    geocoder.getFromLocation(lat, lon, 1)?.firstOrNull()?.locality
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    @Suppress("MissingPermission")
    private suspend fun fetchCurrent(): Pair<Double, Double>? = suspendCancellableCoroutine { cont ->
        fusedLocationProviderClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
            .addOnSuccessListener { loc ->
                val pair = loc?.let { it.latitude to it.longitude }
                if (cont.isActive) cont.resume(pair)
            }
            .addOnFailureListener { if (cont.isActive) cont.resume(null) }
    }

    @Suppress("MissingPermission")
    private suspend fun fetchLast(): Pair<Double, Double>? = suspendCancellableCoroutine { cont ->
        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { loc ->
                val pair = loc?.let { it.latitude to it.longitude }
                if (cont.isActive) cont.resume(pair)
            }
            .addOnFailureListener { if (cont.isActive) cont.resume(null) }
    }

    private companion object {
        const val LOCATION_TIMEOUT_MILLIS = 5_000L
        // Default fallback when GPS is unavailable: New York City.
        val FALLBACK_LOCATION = LatLon(latitude = 40.7128, longitude = -74.0060)
    }
}