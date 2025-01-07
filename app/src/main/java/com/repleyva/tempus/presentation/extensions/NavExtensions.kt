package com.repleyva.tempus.presentation.extensions

import androidx.navigation.NavController

fun <T: Any>NavController.navigationToTop(
    newsRouter: T,
) {
    navigate(newsRouter) {
        graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
}