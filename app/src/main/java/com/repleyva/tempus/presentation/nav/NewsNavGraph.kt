package com.repleyva.tempus.presentation.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.repleyva.tempus.presentation.screens.onboarding.OnboardingScreen
import com.repleyva.tempus.presentation.screens.onboarding.OnboardingViewModel

@Composable
fun NewsNavGraph(
    startDestination: NewsRouter,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        navigation<NewsRouter.AppStartNavigation>(
            startDestination = NewsRouter.OnboardingScreen
        ) {
            composable<NewsRouter.OnboardingScreen> {
                val viewModel: OnboardingViewModel = hiltViewModel()
                OnboardingScreen(event = viewModel::eventHandler)
            }
        }

        navigation<NewsRouter.NewsNavigation>(
            startDestination = NewsRouter.NewsNavigatorScreen
        ) {
            composable<NewsRouter.NewsNavigatorScreen> {
                NewsNavigator()
            }
        }
    }
}