package com.repleyva.tempus.presentation.extensions

import androidx.navigation.NavController

fun NavController.navigationToTop(
    route: String,
) {
    navigate(route) {
        graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}