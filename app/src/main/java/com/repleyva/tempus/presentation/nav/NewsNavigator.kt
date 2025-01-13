package com.repleyva.tempus.presentation.nav

import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.common.LaunchEffectOnce
import com.repleyva.tempus.presentation.extensions.navigationToTop
import com.repleyva.tempus.presentation.screens.bookmark.BookmarkScreen
import com.repleyva.tempus.presentation.screens.bookmark.BookmarkViewModel
import com.repleyva.tempus.presentation.screens.detail.DetailsEvent
import com.repleyva.tempus.presentation.screens.detail.DetailsScreen
import com.repleyva.tempus.presentation.screens.detail.DetailsViewModel
import com.repleyva.tempus.presentation.screens.explore.ExploreScreen
import com.repleyva.tempus.presentation.screens.explore.ExploreViewModel
import com.repleyva.tempus.presentation.screens.home.HomeEvent
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    val routerToIndex = remember {
        mapOf(
            NewsRouter.HomeScreen::class to 0,
            NewsRouter.ExploreScreen::class to 1,
            NewsRouter.BookmarkScreen::class to 2,
            NewsRouter.SettingsScreen::class to 3
        )
    }

    val selectedItem = remember(key1 = currentDestination) {
        currentDestination?.hierarchy
            ?.firstNotNullOfOrNull { destination ->
                routerToIndex.entries.firstOrNull { (route, _) -> destination.hasRoute(route) }?.value
            } ?: 0
    }

    val isBottomBarVisible = remember(key1 = currentDestination) {
        currentDestination?.hierarchy
            ?.any { destination ->
                routerToIndex.keys.any { route -> destination.hasRoute(route) }
            } == true
    }

    Scaffold(
        modifier = Modifier
            .testTag("NewsNavigator")
            .fillMaxSize(),
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
                            0 -> NewsRouter.HomeScreen
                            1 -> NewsRouter.ExploreScreen
                            2 -> NewsRouter.BookmarkScreen
                            3 -> NewsRouter.SettingsScreen
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
            startDestination = NewsRouter.HomeScreen,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable<NewsRouter.HomeScreen> {
                val homeViewModel: HomeViewModel = hiltViewModel()
                val settingsViewModel: SettingsViewModel = hiltViewModel()
                val homeState by homeViewModel.uiState.collectAsStateWithLifecycle()
                val everythingNews = homeState.everythingNews.collectAsLazyPagingItems()
                val breakingNews = homeState.breakingNews.collectAsLazyPagingItems()
                val nickname by settingsViewModel.nickname.collectAsState(initial = "")
                val selectedEmoji by settingsViewModel.selectedEmoji.collectAsState(initial = "\uD83D\uDE36")
                val selectedCity by settingsViewModel.selectedTimezone.collectAsState()

                LaunchEffectOnce {
                    homeViewModel.eventHandler(HomeEvent.FetchWeatherData())
                }

                HomeScreen(
                    weatherData = homeState.weatherData,
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

            composable<NewsRouter.ExploreScreen> {
                val viewModel: ExploreViewModel = hiltViewModel()
                val state = viewModel.state.value
                val articles = viewModel.articles.collectAsLazyPagingItems()
                val searchResults = viewModel.searchResults.collectAsLazyPagingItems()

                ExploreScreen(
                    exploreState = state,
                    articles = articles,
                    searchResults = searchResults,
                    event = viewModel::eventHandler,
                    isRefreshing = viewModel.isRefreshing.value,
                    onRefresh = { viewModel.refreshArticles() },
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }

            composable<NewsRouter.DetailsScreen> {
                val viewModel: DetailsViewModel = hiltViewModel()
                if (viewModel.sideEffect != null) {
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT).show()
                    viewModel.eventHandler(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Article?>("article")?.let { article ->
                    DetailsScreen(
                        article = article,
                        event = viewModel::eventHandler,
                        navigateUp = {
                            navController.navigateUp()
                            navController.currentBackStackEntry?.savedStateHandle?.remove<Article>("article")
                        }
                    )
                }
            }

            composable<NewsRouter.BookmarkScreen> {
                val viewModel: BookmarkViewModel = hiltViewModel()
                val state = viewModel.state.value
                BookmarkScreen(
                    state = state,
                    navigateToDetails = { article ->
                        navigateToDetails(
                            navController = navController,
                            article = article
                        )
                    }
                )
            }

            composable<NewsRouter.SettingsScreen> {
                val homeViewModel: HomeViewModel = hiltViewModel()
                var showTimezoneDialog by remember { mutableStateOf(false) }

                SettingsScreen(
                    homeViewModel = homeViewModel,
                    showTimezoneDialog = showTimezoneDialog,
                    onShowTimezoneDialog = { showTimezoneDialog = true },
                    onDismissTimezoneDialog = { showTimezoneDialog = false }
                )
            }
        }
    }
}

private fun navigateToDetails(
    navController: NavController,
    article: Article,
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
    navController.navigate(NewsRouter.DetailsScreen)
}