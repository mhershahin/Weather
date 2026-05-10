package com.service.feature_api

import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

fun NavGraphBuilder.register(
    featureApi: FeatureApi,
    navController: NavHostController,
    modifier: Modifier = Modifier,
    bottomBarVisibility: MutableState<Boolean>,
    onShowTopAlertDialogCallBack: (isErrorAlert: Boolean, errorOrAlertMessage: String?) -> Unit,
) {
    featureApi.registerGraph(
        navGraphBuilder = this,
        navController = navController,
        modifier = modifier,
        bottomBarVisibility = bottomBarVisibility,
        onShowTopAlertDialogCallBack = onShowTopAlertDialogCallBack
    )
}