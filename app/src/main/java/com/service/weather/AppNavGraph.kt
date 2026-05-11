package com.service.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.service.feature_api.AppRout
import com.service.feature_api.Home
import com.service.feature_api.SplashRoute
import com.service.splash_presentation.SplashDestination
import com.service.weather.home.HomeScreenDestination

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SplashRoute,
        route = AppRout,
    ) {
        composable(route = SplashRoute) {
            SplashDestination(navController = navController)
        }
        composable(route = Home.getRout()) {
            HomeScreenDestination()
        }
    }
}
