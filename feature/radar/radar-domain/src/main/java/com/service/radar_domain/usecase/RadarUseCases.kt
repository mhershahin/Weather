package com.service.radar_domain.usecase

import com.service.api.repository.multi.MultiLocationRepository
import com.service.api.repository.search.SearchCityRepository
import com.service.api.repository.weekly.WeeklyWeatherRepository
import com.service.db.repo.saved.SavedLocationsRepository
import com.service.db.repo.weather.CachedWeatherRepository
import com.service.entity.Result
import com.service.entity.domain.Location
import com.service.entity.domain.LocationKind
import com.service.entity.domain.toLocation
import com.service.entity.response.weather.WeatherResponse

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.math.roundToInt

data class CityCard(
    val location: Location,
    val tempC: Int?,
    val weatherCode: Int?,
    val isDay: Boolean,
)

interface ObserveSavedLocationsUseCase {
    operator fun invoke(): Flow<List<Location>>
}

class ObserveSavedLocationsUseCaseImpl @Inject constructor(
    private val repo: SavedLocationsRepository,
) : ObserveSavedLocationsUseCase {
    override fun invoke(): Flow<List<Location>> = repo.observeSaved()
}


interface SearchLocationsUseCase {
    suspend operator fun invoke(query: String): List<Location>
}

class SearchLocationsUseCaseImpl @Inject constructor(
    private val searchRepo: SearchCityRepository,
) : SearchLocationsUseCase {
    override suspend fun invoke(query: String): List<Location> {
        if (query.length < 2) return emptyList()
        return when (val res = searchRepo.searchCity(query)) {
            is Result.Success -> res.data?.results.orEmpty().map { it.toLocation(LocationKind.SAVED) }
            is Result.Error -> emptyList()
        }
    }
}

interface AddLocationUseCase {
    suspend operator fun invoke(location: Location)
}

class AddLocationUseCaseImpl @Inject constructor(
    private val repo: SavedLocationsRepository,
) : AddLocationUseCase {
    override suspend fun invoke(location: Location) = repo.save(location)
}

interface RemoveLocationUseCase {
    suspend operator fun invoke(id: Int)
}

class RemoveLocationUseCaseImpl @Inject constructor(
    private val repo: SavedLocationsRepository,
) : RemoveLocationUseCase {
    override suspend fun invoke(id: Int) = repo.remove(id)
}

interface SelectCurrentLocationUseCase {
    suspend operator fun invoke(location: Location)
}

class SelectCurrentLocationUseCaseImpl @Inject constructor(
    private val repo: SavedLocationsRepository,
    private val cachedRepo: CachedWeatherRepository,
    private val weeklyRepo: WeeklyWeatherRepository,
) : SelectCurrentLocationUseCase {
    override suspend fun invoke(location: Location) {
        repo.save(location)
        repo.setCurrent(location.id)
        val response = weeklyRepo.getWeeklyWeather(location.latitude, location.longitude)
        cachedRepo.refreshHourly(location, response)
        cachedRepo.refreshForecast(location, response)
    }
}

interface FetchCityCardsUseCase {
    suspend operator fun invoke(locations: List<Location>): List<CityCard>
}

class FetchCityCardsUseCaseImpl @Inject constructor(
    private val multiRepo: MultiLocationRepository,
) : FetchCityCardsUseCase {
    override suspend fun invoke(locations: List<Location>): List<CityCard> {
        if (locations.isEmpty()) return emptyList()
        val lats = locations.joinToString(",") { it.latitude.toString() }
        val lons = locations.joinToString(",") { it.longitude.toString() }
        return when (val res = multiRepo.getMultiLocationCurrentWeather(lats, lons)) {
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
