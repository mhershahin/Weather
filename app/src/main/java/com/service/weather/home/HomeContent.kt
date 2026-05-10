package com.service.weather.home
import com.service.base_ui.R

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.service.entity.ui.FeaturesMain
import com.service.feature_api.Home
import com.service.weather.ui.BottomBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun HomeScreenDestination(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HomeScreen(viewModel)
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val navController = rememberNavController()
    val bottomBarVisibility = mutableStateOf(true)

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = {
            BottomBar(
                navController = navController,
                tabs = getFeatureList(),
            )
        }
    ) { innerPaddingModifier ->
        HomeGraph(
            navController = navController,
            modifier = Modifier.padding(innerPaddingModifier),
            bottomBarVisibility = bottomBarVisibility,
            dependencyProvider = viewModel.getDependencyProvider()
        )
    }
}

private fun getFeatureList(): ImmutableList<FeaturesMain> {
    return listOf(
        FeaturesMain(
            titleId = R.string.current,
            routName = Home.Current.getRout(),
            iconResId = R.drawable.ic_current
        ),
        FeaturesMain(
            titleId = R.string.forecast,
            routName = Home.Forecast.getRout(),
            iconResId = R.drawable.ic_forecast
        ),
        FeaturesMain(
            titleId = R.string.radar,
            routName = Home.Radar.getRout(),
            iconResId = R.drawable.ic_radar
        )
    ).toImmutableList()
}



