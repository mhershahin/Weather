package com.service.feature_api

const val AppRout = "AppRout"
const val SplashRoute = "SplashScreen"

object Home : Feature(){
    private const val ROUT = "HomeScreen"
    override fun getRout(): String = ROUT
    object Daily : Feature() {
        private const val ROUT = "DailyScreen"
        override fun getRout(): String = ROUT
    }
    object Weekly : Feature() {
        private const val ROUT = "WeeklyScreen"
        override fun getRout(): String = ROUT
    }
    object Radar : Feature() {
        private const val ROUT = "RadarScreen"
        override fun getRout(): String = ROUT
    }
}
