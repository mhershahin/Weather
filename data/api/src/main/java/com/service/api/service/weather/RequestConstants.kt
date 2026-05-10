package com.service.api.service.weather

class RequestConstants {
    companion object {
        const val BASE_URL = "https://api.open-meteo.com/"
         const val FORECAST = "v1/forecast"

         const val TEMP_UNIT = "celsius"
         const val WIND_SPEED_UNIT = "ms"
         const val TIME_ZONE = "auto"
         const val FORECAST_DAYS_DAILY = 1
         const val FORECAST_DAYS_WEEKLY = 7



         const val CURRENT_PARAMS =
            "temperature_2m," +
                    "relative_humidity_2m," +
                    "apparent_temperature," +
                    "is_day," +
                    "precipitation," +
                    "weather_code," +
                    "cloud_cover," +
                    "wind_speed_10m," +
                    "wind_direction_10m," +
                    "wind_gusts_10m"

         const val HOURLY_PARAMS =
            "temperature_2m," +
                    "weather_code," +
                    "uv_index," +
                    "dew_point_2m," +
                    "visibility"

         const val DAILY_PARAMS =
            "temperature_2m_max," +
                    "temperature_2m_min," +
                    "uv_index_max"

         const val WEEKLY_DAILY_PARAMS =
            "weather_code," +
                    "temperature_2m_max," +
                    "temperature_2m_min," +
                    "apparent_temperature_max," +
                    "apparent_temperature_min," +
                    "sunrise," +
                    "sunset," +
                    "daylight_duration," +
                    "uv_index_max," +
                    "precipitation_sum," +
                    "rain_sum," +
                    "snowfall_sum," +
                    "precipitation_probability_max," +
                    "wind_speed_10m_max," +
                    "wind_gusts_10m_max," +
                    "wind_direction_10m_dominant"

         const val WEEKLY_HOURLY_PARAMS =
            "temperature_2m," +
                    "weather_code," +
                    "precipitation_probability"

         const val MULTI_LOCATION_PARAMS =
            "temperature_2m," +
                    "weather_code," +
                    "wind_speed_10m," +
                    "is_day"
    }
}