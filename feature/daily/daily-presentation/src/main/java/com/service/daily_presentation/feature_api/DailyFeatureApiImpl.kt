package com.service.daily_presentation.feature_api

import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.service.daily_presentation.ui.DailyDestination
import com.service.feature_api.Feature
import com.service.feature_api.Home
import javax.inject.Inject

class DailyFeatureApiImpl @Inject constructor() : DailyFeatureApi {
    private val feature = Home.Daily

    override fun getFeature(): Feature = feature
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier,
        bottomBarVisibility: MutableState<Boolean>,
    ) {
        navGraphBuilder.composable(route = feature.getRout()) {
            DailyDestination()
        }
    }
}
