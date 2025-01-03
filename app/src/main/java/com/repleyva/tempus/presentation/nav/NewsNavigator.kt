package com.repleyva.tempus.presentation.nav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.extensions.navigationToTop
import com.repleyva.tempus.presentation.screens.bookmark.BookmarkScreen
import com.repleyva.tempus.presentation.screens.detail.DetailScreen
import com.repleyva.tempus.presentation.screens.explore.ExploreScreen
import com.repleyva.tempus.presentation.screens.home.HomeScreen
import com.repleyva.tempus.presentation.screens.home.HomeViewModel
import com.repleyva.tempus.presentation.screens.settings.SettingsScreen
import com.repleyva.tempus.presentation.screens.settings.SettingsViewModel
import com.repleyva.tempus.presentation.theme.Dimensions.paddingZero

@Composable
fun NewsNavigator() {

    val home = stringResource(R.string.home)
    val explore = stringResource(R.string.explore)
    val bookmark = stringResource(R.string.bookmark)
    val settings = stringResource(R.string.settings)

    val bottomNavigationItem = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = home),
            BottomNavigationItem(icon = R.drawable.ic_explore, text = explore),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = bookmark),
            BottomNavigationItem(icon = R.drawable.ic_settings, text = settings)
        )
    }

    val navController = rememberNavController()

    val backStackState = navController.currentBackStackEntryAsState()

    val selectedItem = remember(key1 = backStackState.value) {
        when (backStackState.value?.destination?.route) {
            NewsRouter.HomeScreen.route -> 0
            NewsRouter.ExploreScreen.route -> 1
            NewsRouter.BookmarkScreen.route -> 2
            NewsRouter.SettingsScreen.route -> 3
            else -> 0
        }
    }

    val isBottomBarVisible = remember(key1 = backStackState.value) {
        (backStackState.value?.destination?.route == NewsRouter.HomeScreen.route)
            .or(backStackState.value?.destination?.route == NewsRouter.ExploreScreen.route)
            .or(backStackState.value?.destination?.route == NewsRouter.BookmarkScreen.route)
            .or(backStackState.value?.destination?.route == NewsRouter.SettingsScreen.route)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomBarVisible,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
            ) {
                BottomNavigation(
                    items = bottomNavigationItem,
                    selected = selectedItem,
                    onItemClick = { index ->
                        val route = when (index) {
                            0 -> NewsRouter.HomeScreen.route
                            1 -> NewsRouter.ExploreScreen.route
                            2 -> NewsRouter.BookmarkScreen.route
                            3 -> NewsRouter.SettingsScreen.route
                            else -> null
                        }
                        route?.let { navController.navigationToTop(it) }
                    }
                )
            }
        }
    ) { innerPadding ->

        val bottomPadding = if (isBottomBarVisible) innerPadding.calculateBottomPadding() else paddingZero

        NavHost(
            navController = navController,
            startDestination = NewsRouter.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = NewsRouter.HomeScreen.route) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                val settingsViewModel: SettingsViewModel = hiltViewModel()
                val everythingNews = homeViewModel.everythingNews.collectAsLazyPagingItems()
                val breakingNews = homeViewModel.breakingNews.collectAsLazyPagingItems()
                val weatherData by homeViewModel.weatherData.collectAsState(initial = null)
                val nickname by settingsViewModel.nickname.collectAsState(initial = "")
                val selectedEmoji by settingsViewModel.selectedEmoji.collectAsState(initial = "\uD83D\uDE36")
                val selectedCity by settingsViewModel.selectedTimezone.collectAsState()

                LaunchedEffect(Unit) {
                    homeViewModel.fetchWeatherData()
                }

                HomeScreen(
                    weatherData = weatherData,
                    selectedCity = selectedCity,
                    nickname = nickname,
                    selectedEmoji = selectedEmoji,
                    everythingNews = everythingNews,
                    breakingNews = breakingNews,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }

            composable(route = NewsRouter.ExploreScreen.route) {
                ExploreScreen()
            }

            composable(route = NewsRouter.DetailsScreen.route) {
                DetailScreen()
            }

            composable(route = NewsRouter.BookmarkScreen.route) {
                BookmarkScreen()
            }

            composable(route = NewsRouter.SettingsScreen.route) {
                SettingsScreen()
            }
        }
    }

}

private fun navigateToDetails(
    navController: NavController,
    article: Article,
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(NewsRouter.DetailsScreen.route)
}