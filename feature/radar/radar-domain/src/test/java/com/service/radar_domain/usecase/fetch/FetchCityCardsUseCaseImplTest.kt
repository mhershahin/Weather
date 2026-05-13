package com.service.radar_domain.usecase.fetch

import com.service.api.repository.daily.DailyWeatherRepository
import com.service.api.repository.multi.MultiLocationRepository
import com.service.entity.Result
import com.service.entity.domain.Location
import com.service.entity.response.weather.WeatherResponse
import com.service.utils.dispatcher.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class FetchCityCardsUseCaseImplTest {

    private val testDispatchers = object : DispatcherProvider {
        override val io = Dispatchers.IO
        override val main = Dispatchers.Main
        override val default = Dispatchers.Default
        override val unconfined = Dispatchers.Unconfined
    }

    @Test
    fun `empty locations returns empty list without hitting network`() = runBlocking {
        val multi = FakeMultiRepo()
        val daily = FakeDailyRepo()
        val useCase = FetchCityCardsUseCaseImpl(multi, daily, testDispatchers)

        val result = useCase(locations = emptyList())

        assertTrue(result.isEmpty())
        assertEquals(0, multi.callCount)
        assertEquals(0, daily.callCount)
    }

    @Test
    fun `single location uses daily endpoint and returns one card`() = runBlocking {
        val daily = FakeDailyRepo(response = Result.Success(null))
        val useCase = FetchCityCardsUseCaseImpl(FakeMultiRepo(), daily, testDispatchers)

        val result = useCase(locations = listOf(location(id = 1)))

        assertEquals(1, daily.callCount)
        assertEquals(1, result.size)
        assertEquals(1, result.first().location.id)
        assertNull(result.first().tempC) // body was null -> placeholders
    }

    @Test
    fun `repository error degrades gracefully to placeholders`() = runBlocking {
        val daily = FakeDailyRepo(response = Result.Error(code = 500, message = "boom"))
        val useCase = FetchCityCardsUseCaseImpl(FakeMultiRepo(), daily, testDispatchers)

        val result = useCase(locations = listOf(location(id = 1)))

        assertEquals(1, result.size)
        assertNull(result.first().tempC)
        assertNull(result.first().weatherCode)
    }

    private fun location(id: Int) = Location(
        id = id,
        name = "Test",
        country = "TC",
        region = "",
        latitude = 0.0,
        longitude = 0.0,
    )

    private class FakeMultiRepo(
        private val response: Result<List<WeatherResponse>> = Result.Success(emptyList()),
    ) : MultiLocationRepository {
        var callCount = 0
            private set

        override suspend fun getMultiLocationCurrentWeather(
            latitudes: String,
            longitudes: String,
        ): Result<List<WeatherResponse>> {
            callCount++
            return response
        }
    }

    private class FakeDailyRepo(
        private val response: Result<WeatherResponse> = Result.Success(null),
    ) : DailyWeatherRepository {
        var callCount = 0
            private set

        override suspend fun getDailyWeather(
            latitude: Double,
            longitude: Double,
        ): Result<WeatherResponse> {
            callCount++
            return response
        }
    }
}
