package com.repleyva.tempus.presentation.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.constants.Constants.dummyArticle
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.domain.model.WeatherData
import com.repleyva.tempus.presentation.common.ScreenTitleTextSmall
import com.repleyva.tempus.presentation.screens.home.components.HomeList
import com.repleyva.tempus.presentation.screens.home.components.HomeWeatherBar
import com.repleyva.tempus.presentation.theme.Dimensions.paddingLarge
import com.repleyva.tempus.presentation.theme.Dimensions.paddingMedium
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSemiMedium
import com.repleyva.tempus.presentation.theme.TempusTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    weatherData: WeatherData?,
    selectedCity: String,
    nickname: String,
    selectedEmoji: String,
    everythingNews: LazyPagingItems<Article>,
    breakingNews: LazyPagingItems<Article>,
    navigateToDetails: (Article) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("HomeScreen"),
        topBar = {
            HomeWeatherBar(
                weatherData = weatherData,
                timezone = selectedCity,
                nickname = nickname,
                selectedEmoji = selectedEmoji,
                timezoneOffset = weatherData?.timezone ?: -18000
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        HomeBody(
            paddingValues = paddingValues,
            breakingNews = breakingNews,
            navigateToDetails = navigateToDetails,
            everythingNews = everythingNews
        )
    }
}

@Composable
private fun HomeBody(
    paddingValues: PaddingValues,
    breakingNews: LazyPagingItems<Article>,
    navigateToDetails: (Article) -> Unit,
    everythingNews: LazyPagingItems<Article>,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
    ) {
        LazyColumn(Modifier.fillMaxSize()) {

            item {

                Spacer(modifier = Modifier.height(paddingMedium))

                ScreenTitleTextSmall(
                    modifier = Modifier.padding(start = paddingLarge),
                    textResId = R.string.breaking_news
                )

                Spacer(modifier = Modifier.height(paddingSemiMedium))

                HomeList(
                    modifier = Modifier.fillMaxSize(),
                    articles = breakingNews,
                    onClick = { article -> navigateToDetails(article) }
                ).also {
                    println("Breaking News - Articles count: ${breakingNews.itemCount}")
                }

            }

            item {

                Spacer(modifier = Modifier.height(paddingMedium))

                ScreenTitleTextSmall(
                    modifier = Modifier.padding(start = paddingLarge),
                    textResId = R.string.worldwide
                )

                Spacer(modifier = Modifier.height(paddingSemiMedium))

                HomeList(
                    modifier = Modifier.fillMaxSize(),
                    articles = everythingNews,
                    onClick = { article -> navigateToDetails(article) }
                ).also {
                    println("Everything News - Articles count: ${everythingNews.itemCount}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    val articlesFlow = flowOf(PagingData.from(listOf(dummyArticle, dummyArticle, dummyArticle, dummyArticle)))
    val lazyPagingArticles = articlesFlow.collectAsLazyPagingItems()

    TempusTheme(
        dynamicColor = false
    ) {
        HomeScreen(
            weatherData = null,
            nickname = "repleyva",
            selectedEmoji = "\uD83D\uDE36",
            everythingNews = lazyPagingArticles,
            breakingNews = lazyPagingArticles,
            navigateToDetails = {},
            selectedCity = "Bogotá"
        )
    }
}