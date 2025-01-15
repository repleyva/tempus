package com.repleyva.tempus.presentation.screens.main

import android.Manifest
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.repleyva.tempus.presentation.nav.NewsNavGraph
import com.repleyva.tempus.presentation.screens.settings.SettingsEvent.UpdateDarkMode
import com.repleyva.tempus.presentation.screens.settings.SettingsViewModel
import com.repleyva.tempus.presentation.theme.TempusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    private val settingsViewModel by viewModels<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        viewModel.eventHandler(MainEvent.Init)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.uiState.value.splashCondition
            }
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )

        setContent {

            val isDarkMode = isSystemInDarkTheme()
            val settingsState by settingsViewModel.uiState.collectAsState()
            val isDarkModeEnabled by settingsState.theme.collectAsState(initial = isDarkMode)

            LaunchedEffect(isDarkMode) {
                if (isDarkMode != isDarkModeEnabled) {
                    settingsViewModel.eventHandler(UpdateDarkMode(isDarkMode))
                }
            }

            // RequestNotificationPermission()

            TempusTheme(
                darkTheme = isDarkModeEnabled,
                dynamicColor = false
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .testTag("MainActivityBox")
                ) {

                    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                    NewsNavGraph(startDestination = uiState.startDestination)
                }
            }
        }
    }
}

@Composable
fun RequestNotificationPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        var permissionGranted by remember { mutableStateOf(false) }

        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            permissionGranted = isGranted
        }

        LaunchedEffect(Unit) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }
}