package com.service.weather.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.service.feature_api.Home
import com.service.feature_api.register
import com.service.weather.dependency.DependencyProvider

@Composable
fun HomeGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    dependencyProvider: DependencyProvider,
) {

    NavHost(
        navController = navController,
        route = Home.getRout(),
        startDestination = dependencyProvider.dailyFeatureApi().getFeature().getRout()
    ) {

        val daily = dependencyProvider.dailyFeatureApi()
        val weekly = dependencyProvider.weeklyFeatureApi()
        val radar = dependencyProvider.radarFeatureApi()

        register(
            daily,
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            modifier = modifier,
        )
        register(
            weekly,
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            modifier = modifier,
        )
        register(
            radar,
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            modifier = modifier,
        )
    }
}
