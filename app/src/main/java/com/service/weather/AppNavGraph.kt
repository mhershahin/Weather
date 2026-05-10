package com.service.weather

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.service.feature_api.AppRout
import com.service.feature_api.Home
import com.service.weather.home.HomeScreenDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Home.getRout(),
        route = AppRout
    ) {
        composable(route = Home.getRout()) {
            HomeScreenDestination()
        }
    }
}