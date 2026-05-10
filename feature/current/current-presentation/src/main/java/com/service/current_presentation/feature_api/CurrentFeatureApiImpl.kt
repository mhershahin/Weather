package com.service.current_presentation.feature_api

import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.service.current_presentation.ui.CurrentDestination
import com.service.feature_api.Feature
import com.service.feature_api.Home
import javax.inject.Inject

class CurrentFeatureApiImpl @Inject constructor() :CurrentFeatureApi{
private val feature = Home.Current

    override fun getFeature(): Feature = feature
    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier,
        bottomBarVisibility: MutableState<Boolean>,
        onShowTopAlertDialogCallBack: (isErrorAlert: Boolean, errorOrAlertMessage: String?) -> Unit
    ) {
        navGraphBuilder.composable(route = feature.getRout()) {
            CurrentDestination(
                navController=navController,
                bottomBarVisibility = bottomBarVisibility,
                onShowTopAlertDialogCallBack = onShowTopAlertDialogCallBack,
            )
        }
    }



}