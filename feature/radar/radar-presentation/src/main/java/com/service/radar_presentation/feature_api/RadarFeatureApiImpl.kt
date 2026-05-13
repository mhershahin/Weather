package com.service.radar_presentation.feature_api

import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.service.feature_api.Feature
import com.service.feature_api.Home
import com.service.radar_presentation.ui.RadarDestination
import javax.inject.Inject

class RadarFeatureApiImpl @Inject constructor() : RadarFeatureApi {
    private val feature = Home.Radar

    override fun getFeature(): Feature = feature
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier,
        bottomBarVisibility: MutableState<Boolean>
    ) {
        navGraphBuilder.composable(route = feature.getRout()) {
            RadarDestination(navController = navController)
        }
    }
}