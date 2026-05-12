package com.service.radar_domain.usecase.fetch

import com.service.api.repository.multi.MultiLocationRepository
import com.service.entity.Result
import com.service.entity.domain.Location
import com.service.entity.response.weather.WeatherResponse
import com.service.entity.ui.CityCard
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

internal class FetchCityCardsUseCaseImpl @Inject constructor(
    private val multiRepo: MultiLocationRepository,
    private val dispatchers: DispatcherProvider,
) : FetchCityCardsUseCase {

    override suspend fun invoke(locations: List<Location>): List<CityCard> = withContext(dispatchers.io) {
        if (locations.isEmpty()) return@withContext emptyList()
        val lats = locations.joinToString(",") { it.latitude.toString() }
        val lons = locations.joinToString(",") { it.longitude.toString() }
        when (val res = multiRepo.getMultiLocationCurrentWeather(lats, lons)) {
            is Result.Success -> {
                val weathers: List<WeatherResponse> = res.data.orEmpty()
                locations.mapIndexed { idx, loc ->
                    val w = weathers.getOrNull(idx)?.current
                    CityCard(
                        location = loc,
                        tempC = w?.temperature?.roundToInt(),
                        weatherCode = w?.weatherCode,
                        isDay = (w?.isDay ?: 1) == 1,
                    )
                }
            }
            is Result.Error -> locations.map { CityCard(it, null, null, true) }
        }
    }
}
