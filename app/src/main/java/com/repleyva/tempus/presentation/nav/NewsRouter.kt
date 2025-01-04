package com.repleyva.tempus.presentation.nav

import kotlinx.serialization.Serializable

@Serializable
sealed interface NewsRouter {

    @Serializable
    data object OnboardingScreen : NewsRouter

    @Serializable
    data object HomeScreen : NewsRouter

    @Serializable
    data object ExploreScreen : NewsRouter

    @Serializable
    data object BookmarkScreen : NewsRouter

    @Serializable
    data object DetailsScreen : NewsRouter

    @Serializable
    data object AppStartNavigation : NewsRouter

    @Serializable
    data object NewsNavigation : NewsRouter

    @Serializable
    data object NewsNavigatorScreen : NewsRouter

    @Serializable
    data object SettingsScreen : NewsRouter
}