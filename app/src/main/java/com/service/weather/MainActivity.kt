package com.service.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.systemBars
import androidx.navigation.compose.rememberNavController
import com.service.base_ui.ScaffoldSnackFree
import com.service.base_ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            WeatherTheme {
                ScaffoldSnackFree(
                    backgroundColor = MaterialTheme.colors.background,
                ) { innerPaddingModifier ->
                    AppNavGraph(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.systemBars)
                            .padding(innerPaddingModifier),
                        navController = navController,
                    )
                }
            }
        }
    }
}