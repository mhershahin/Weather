package com.service.weather.home

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.service.base_ui.R
import com.service.base_ui.ScaffoldSnackFree
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

    ScaffoldSnackFree(
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
    BackHandler(enabled = false){

    }
}

private fun getFeatureList(): ImmutableList<FeaturesMain> {
    return listOf(
        FeaturesMain(
            titleId = R.string.daily,
            routName = Home.Daily.getRout(),
            iconResId = R.drawable.ic_daily
        ),
        FeaturesMain(
            titleId = R.string.weekly,
            routName = Home.Weekly.getRout(),
            iconResId = R.drawable.ic_weekly
        ),
        FeaturesMain(
            titleId = R.string.radar,
            routName = Home.Radar.getRout(),
            iconResId = R.drawable.ic_radar
        )
    ).toImmutableList()
}
