package com.service.weather.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.service.base_ui.R
import com.service.base_ui.theme.WeatherTheme
import com.service.entity.ui.FeaturesMainUi
import com.service.feature_api.Home
import com.service.utils.ui.LocalSpacing
import com.service.utils.ui.LocalTextSize
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList


@Composable
fun BottomBar(
    navController: NavController,
    tabs: ImmutableList<FeaturesMainUi>?
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    val currentRoute = currentDestination?.route

    val routes = tabs?.map { it.routName }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalSpacing.current.sixtyFourDp)
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth(1f)
                .align(alignment = Alignment.TopCenter),
            color = Color.White.copy(alpha = 0.06f),
            thickness = LocalSpacing.current.oneDp
        )
        routes?.let {
            BottomNavigation(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = LocalSpacing.current.oneDp)
                    .height(LocalSpacing.current.sixtyFourDp),
                backgroundColor = MaterialTheme.colors.background
            ) {
                tabs.forEach { tab ->
                    BottomNavigationItem(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(top = LocalSpacing.current.oneDp)
                            .align(Alignment.Bottom),
                        icon = {
                            Icon(
                                painterResource(
                                    tab.iconResId,
                                ), contentDescription = null
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(tab.titleId),
                                fontSize = LocalTextSize.current.eight
                            )
                        },
                        selected = tab.routName == getCurrentRouteName(currentRoute, tabs),
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = LocalContentColor.current,
                        onClick = {
                            if (tab.routName != currentRoute) {
                                navController.navigate(tab.routName) {
                                    navController.graph.startDestinationRoute?.let { screenRoute ->
                                        popUpTo(screenRoute) {
                                            inclusive = screenRoute != tabs[0].routName
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

fun getCurrentRouteName(currentRoute: String?, tabs: ImmutableList<FeaturesMainUi>?): String {
    if (currentRoute != null && tabs != null) {
        val cleanRoute = currentRoute.replace("{", "").replace("}", "")
        tabs.forEach {
            if (cleanRoute == it.routName) {
                return cleanRoute
            } else if (it.routName.let { rout -> cleanRoute.contains("/$rout/") }) {
                return it.routName
            }
        }
    }
    return Home.Daily.getRout()
}

@Preview
@Composable
private fun BottomBarPreview() {
    val tabs = listOf(
        FeaturesMainUi(titleId = R.string.daily, routName = Home.Daily.getRout(), iconResId = R.drawable.ic_daily),
        FeaturesMainUi(titleId = R.string.weekly, routName = Home.Weekly.getRout(), iconResId = R.drawable.ic_weekly),
        FeaturesMainUi(titleId = R.string.radar, routName = Home.Radar.getRout(), iconResId = R.drawable.ic_radar),
    ).toImmutableList()
    WeatherTheme {
        BottomBar(navController = rememberNavController(), tabs = tabs)
    }
}