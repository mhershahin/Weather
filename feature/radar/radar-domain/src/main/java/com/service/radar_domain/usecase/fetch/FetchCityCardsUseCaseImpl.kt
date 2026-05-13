package com.service.radar_domain.usecase.fetch

import com.service.api.repository.daily.DailyWeatherRepository
import com.service.api.repository.multi.MultiLocationRepository
import com.service.entity.Result
import com.service.entity.domain.Location
import com.service.entity.response.weather.WeatherResponse
import com.service.entity.ui.CityCardUi
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

internal class FetchCityCardsUseCaseImpl @Inject constructor(
    private val multiRepo: MultiLocationRepository,
    private val dailyRepo: DailyWeatherRepository,
    private val dispatchers: DispatcherProvider,
) : FetchCityCardsUseCase {

    override suspend fun invoke(locations: List<Location>): List<CityCardUi> = withContext(dispatchers.io) {
        if (locations.isEmpty()) return@withContext emptyList()
        runCatching {
            if (locations.size == 1) fetchSingle(locations.first(), locations)
            else fetchMulti(locations)
        }.getOrElse { placeholders(locations) }
    }

    private suspend fun fetchMulti(locations: List<Location>): List<CityCardUi> {
        val lats = locations.joinToString(",") { it.latitude.toString() }
        val lons = locations.joinToString(",") { it.longitude.toString() }
        return when (val res = multiRepo.getMultiLocationCurrentWeather(lats, lons)) {
            is Result.Success -> {
                val weathers: List<WeatherResponse> = res.data.orEmpty()
                locations.mapIndexed { idx, loc ->
                    val w = weathers.getOrNull(idx)?.current
                    CityCardUi(
                        location = loc,
                        tempC = w?.temperature?.roundToInt(),
                        weatherCode = w?.weatherCode,
                        isDay = (w?.isDay ?: 1) == 1,
                    )
                }
            }
            is Result.Error -> placeholders(locations)
        }
    }

    private suspend fun fetchSingle(loc: Location, locations: List<Location>): List<CityCardUi> {
        return when (val res = dailyRepo.getDailyWeather(loc.latitude, loc.longitude)) {
            is Result.Success -> {
                val w = res.data?.current
                listOf(
                    CityCardUi(
                        location = loc,
                        tempC = w?.temperature?.roundToInt(),
                        weatherCode = w?.weatherCode,
                        isDay = (w?.isDay ?: 1) == 1,
                    )
                )
            }
            is Result.Error -> placeholders(locations)
        }
    }

    private fun placeholders(locations: List<Location>): List<CityCardUi> =
        locations.map { CityCardUi(it, null, null, true) }
}
