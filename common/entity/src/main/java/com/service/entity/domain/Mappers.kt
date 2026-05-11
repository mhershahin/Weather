package com.service.entity.domain

import com.service.entity.response.location.GeoLocation
import com.service.entity.response.weather.WeatherResponse
import kotlin.math.roundToInt

fun GeoLocation.toLocation(kind: LocationKind = LocationKind.SAVED): Location = Location(
    id = id,
    name = name,
    country = country.orEmpty(),
    region = listOfNotNull(admin1).joinToString(", "),
    latitude = latitude,
    longitude = longitude,
    isCurrent = false,
    kind = kind,
)

fun WeatherResponse.toWeather(locationId: Int, fetchedAtMillis: Long): Weather {
    val c = current
    val current = c?.let {
        CurrentSnapshot(
            tempC = it.temperature?.roundToInt() ?: 0,
            weatherCode = it.weatherCode ?: 0,
            isDay = (it.isDay ?: 1) == 1,
            humidityPct = it.relativeHumidity ?: 0,
            dewPointC = hourly?.dewPoint?.firstOrNull()?.roundToInt(),
            windKmh = ((it.windSpeed ?: 0.0) * 3.6).roundToInt(),
            windDirectionDeg = it.windDirection ?: 0,
            visibilityMeters = hourly?.visibility?.firstOrNull(),
            uvIndex = daily?.uvIndexMax?.firstOrNull()?.roundToInt(),
            apparentTempC = it.apparentTemperature?.roundToInt(),
        )
    }

    val hourSlots = buildList {
        val times = hourly?.time.orEmpty()
        val temps = hourly?.temperature.orEmpty()
        val codes = hourly?.weatherCode.orEmpty()
        for (i in times.indices) {
            add(
                HourSlot(
                    isoTime = times[i],
                    tempC = temps.getOrNull(i)?.roundToInt() ?: 0,
                    weatherCode = codes.getOrNull(i) ?: 0,
                    isDay = (c?.isDay ?: 1) == 1,
                )
            )
        }
    }

    val daySlots = buildList {
        val times = daily?.time.orEmpty()
        val maxes = daily?.temperatureMax.orEmpty()
        val mins = daily?.temperatureMin.orEmpty()
        val codes = daily?.weatherCode.orEmpty()
        val probs = daily?.precipitationProbabilityMax.orEmpty()
        for (i in times.indices) {
            add(
                DaySlot(
                    isoDate = times[i],
                    tempMaxC = maxes.getOrNull(i)?.roundToInt() ?: 0,
                    tempMinC = mins.getOrNull(i)?.roundToInt() ?: 0,
                    weatherCode = codes.getOrNull(i) ?: 0,
                    precipitationProbabilityPct = probs.getOrNull(i) ?: 0,
                )
            )
        }
    }

    val highC = daily?.temperatureMax?.firstOrNull()?.roundToInt()
    val lowC = daily?.temperatureMin?.firstOrNull()?.roundToInt()

    return Weather(
        locationId = locationId,
        fetchedAtMillis = fetchedAtMillis,
        current = current,
        hourly = hourSlots,
        daily = daySlots,
        highC = highC,
        lowC = lowC,
    )
}
