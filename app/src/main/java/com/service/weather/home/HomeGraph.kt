package com.service.weather.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.service.feature_api.Home
import com.service.feature_api.register
import com.service.weather.dependecy.DependencyProvider

@Composable
fun HomeGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomBarVisibility: MutableState<Boolean>,
    dependencyProvider: DependencyProvider,
) {

    val onShowTopAlertDialogCallBack: (isErrorAlert: Boolean, errorOrAlertMessage: String?) -> Unit =
        remember {
            { isErrorAlert: Boolean, errorOrAlertMessage: String? ->

            }
        }
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
            onShowTopAlertDialogCallBack = onShowTopAlertDialogCallBack
        )
        register(
            weekly,
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            modifier = modifier,
            onShowTopAlertDialogCallBack = onShowTopAlertDialogCallBack
        )
        register(
            radar,
            navController = navController,
            bottomBarVisibility = bottomBarVisibility,
            modifier = modifier,
            onShowTopAlertDialogCallBack = onShowTopAlertDialogCallBack
        )
    }
}
