package com.service.api.service.geocoding

class GeocodingConstants {
    companion object {
         const val GEOCODING_BASE_URL = "https://geocoding-api.open-meteo.com/"

         const val GEOCODING_SEARCH = "${GEOCODING_BASE_URL}v1/search"

         const val SEARCH_RESULT_COUNT = 10
         const val SEARCH_LANGUAGE = "en"
         const val SEARCH_FORMAT = "json"
    }
}