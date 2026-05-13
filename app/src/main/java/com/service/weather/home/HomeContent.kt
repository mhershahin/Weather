package com.service.weather.home

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.service.base_ui.BaseTopAlertView
import com.service.base_ui.R
import com.service.base_ui.ScaffoldSnackFree
import com.service.base_ui.network.ConnectivityStatus
import com.service.entity.ui.FeaturesMainUi
import com.service.feature_api.Home
import com.service.weather.ui.BottomBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Composable
fun HomeScreenDestination(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HomeScreen(viewModel)
}

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val navController = rememberNavController()
    val bottomBarVisibility = mutableStateOf(true)
    var isConnected by remember { mutableStateOf(true) }
    val isConnectedCallback: (isConnected: Boolean) -> Unit = remember {
        {
            isConnected = it
        }
    }
    ScaffoldSnackFree(
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            BaseTopAlertView(
                isVisible = !isConnected,
                message = stringResource(R.string.error_no_internet),
                isErrorAlert = true,
                autoDismissMillis = null,
            )
        },
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
    ConnectivityStatus(isConnectedCallback)

    BackHandler(enabled = false) {

    }
}

private fun getFeatureList(): ImmutableList<FeaturesMainUi> {
    return listOf(
        FeaturesMainUi(
            titleId = R.string.daily,
            routName = Home.Daily.getRout(),
            iconResId = R.drawable.ic_daily
        ),
        FeaturesMainUi(
            titleId = R.string.weekly,
            routName = Home.Weekly.getRout(),
            iconResId = R.drawable.ic_weekly
        ),
        FeaturesMainUi(
            titleId = R.string.radar,
            routName = Home.Radar.getRout(),
            iconResId = R.drawable.ic_radar
        )
    ).toImmutableList()
}
