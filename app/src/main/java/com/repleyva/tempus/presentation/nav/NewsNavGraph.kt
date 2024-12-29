package com.repleyva.tempus.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

@Composable
fun NewsNavGraph(
    startDestination: String,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = NewsRouter.AppStartNavigation.route,
            startDestination = NewsRouter.OnboardingScreen.route
        ) {
            composable(
                route = NewsRouter.OnboardingScreen.route
            ) {

            }
        }
        navigation(
            route = NewsRouter.NewsNavigation.route,
            startDestination = NewsRouter.NewsNavigatorScreen.route
        ) {
            composable(
                route = NewsRouter.NewsNavigatorScreen.route
            ) {

            }
        }
    }
}