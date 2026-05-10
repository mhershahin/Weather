package com.service.feature_api

const val AppRout = "AppRout"
object Home : Feature(){
    private const val ROUT = "HomeScreen"
    override fun getRout(): String = ROUT
    object Current : Feature() {
        private const val ROUT = "CurrentScreen"
        override fun getRout(): String = ROUT
    }
    object Forecast : Feature() {
        private const val ROUT = "ForecastScreen"
        override fun getRout(): String = ROUT
    }
    object Radar : Feature() {
        private const val ROUT = "RadarScreen"
        override fun getRout(): String = ROUT
    }
}