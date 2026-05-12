package com.service.weekly_presentation.feature_api

import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.service.feature_api.Feature
import com.service.feature_api.Home
import com.service.weekly_presentation.ui.WeeklyDestination
import javax.inject.Inject

class WeeklyFeatureApiImpl @Inject constructor() : WeeklyFeatureApi {
    private val feature = Home.Weekly

    override fun getFeature(): Feature = feature
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier,
        bottomBarVisibility: MutableState<Boolean>,
        onShowTopAlertDialogCallBack: (isErrorAlert: Boolean, errorOrAlertMessage: String?) -> Unit
    ) {
        navGraphBuilder.composable(route = feature.getRout()) {
            WeeklyDestination(
                navController = navController,
                bottomBarVisibility = bottomBarVisibility,
                onShowTopAlertDialogCallBack = onShowTopAlertDialogCallBack,
            )
        }
    }
}
