package com.service.forecast_presentation.feature_api

import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.service.feature_api.Feature
import com.service.feature_api.Home
import com.service.forecast_presentation.ui.ForecastDestination
import javax.inject.Inject

class ForecastFeatureApiImpl @Inject constructor() :ForecastFeatureApi{
private val feature = Home.Forecast

    override fun getFeature(): Feature = feature
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier,
        bottomBarVisibility: MutableState<Boolean>,
        onShowTopAlertDialogCallBack: (isErrorAlert: Boolean, errorOrAlertMessage: String?) -> Unit
    ) {
        navGraphBuilder.composable(route = feature.getRout()) {
            ForecastDestination(
                navController=navController,
                bottomBarVisibility = bottomBarVisibility,
                onShowTopAlertDialogCallBack = onShowTopAlertDialogCallBack,
            )
        }
    }
}