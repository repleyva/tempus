package com.repleyva.tempus.presentation.screens.explore

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.constants.Constants
import com.repleyva.tempus.domain.constants.Constants.dummyArticle
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.common.RefreshArticles
import com.repleyva.tempus.presentation.common.ScreenTitleTextLarge
import com.repleyva.tempus.presentation.common.SearchBar
import com.repleyva.tempus.presentation.screens.explore.components.ExploreCategory
import com.repleyva.tempus.presentation.screens.explore.components.ExploreList
import com.repleyva.tempus.presentation.theme.Dimensions.paddingMedium
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSemiMedium
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSmall
import com.repleyva.tempus.presentation.theme.TempusTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun ExploreScreen(
    exploreState: ExploreState,
    event: (ExploreEvent) -> Unit,
    articles: LazyPagingItems<Article>,
    searchResults: LazyPagingItems<Article>,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    navigateToDetails: (Article) -> Unit,
) {

    val isSearchActive = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        ExploreHeader(
            isSearchActive = isSearchActive,
            exploreState = exploreState,
            event = event
        )

        ExploreBody(
            isSearchActive = isSearchActive,
            exploreState = exploreState,
            event = event,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            articles = articles,
            navigateToDetails = navigateToDetails,
            searchResults = searchResults
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ExploreHeader(
    isSearchActive: MutableState<Boolean>,
    exploreState: ExploreState,
    event: (ExploreEvent) -> Unit,
) {
    if (!isSearchActive.value) {
        TopAppBar(
            title = { ScreenTitleTextLarge(textResId = R.string.explore) },
            actions = {
                IconButton(onClick = { isSearchActive.value = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = null
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            modifier = Modifier.padding(paddingSemiMedium)
        )
    } else {
        Box(
            modifier = Modifier
                .padding(
                    top = paddingMedium,
                    start = paddingSemiMedium,
                    end = paddingSemiMedium
                )
                .statusBarsPadding()
        ) {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                text = exploreState.searchQuery,
                readOnly = false,
                onValueChange = { event(ExploreEvent.OnSearchQueryChanged(it)) },
                onSearch = { event(ExploreEvent.OnSearch) },
                onSearchClosed = { isSearchActive.value = false }
            )
        }
    }
}

@Composable
private fun ExploreBody(
    isSearchActive: MutableState<Boolean>,
    exploreState: ExploreState,
    event: (ExploreEvent) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    articles: LazyPagingItems<Article>,
    navigateToDetails: (Article) -> Unit,
    searchResults: LazyPagingItems<Article>,
) {
    Column(modifier = Modifier.fillMaxSize()) {

        if (!isSearchActive.value) {
            ExploreCategory(
                categories = Constants.CATEGORY_LIST,
                selectedCategory = exploreState.selectedCategory,
                onCategorySelected = { category ->
                    event(ExploreEvent.OnCategorySelected(category))
                }
            )

            Spacer(modifier = Modifier.height(paddingMedium))

            RefreshArticles(
                isRefreshing = isRefreshing,
                onRefresh = onRefresh,
                modifier = Modifier
            ) {
                ExploreList(
                    modifier = Modifier.padding(horizontal = paddingSmall),
                    articles = articles,
                    onClick = { article -> navigateToDetails(article) }
                )
            }

        } else {

            Spacer(modifier = Modifier.height(paddingMedium))

            if (searchResults.itemCount > 0) {
                ExploreList(
                    modifier = Modifier.padding(horizontal = paddingSmall),
                    articles = searchResults,
                    onClick = { article -> navigateToDetails(article) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSearchScreen() {

    val dummyState = ExploreState(
        searchQuery = "Test Search",
        articles = null
    )
    val articlesFlow = flowOf(PagingData.from(listOf(dummyArticle, dummyArticle, dummyArticle, dummyArticle)))
    val lazyPagingArticles = articlesFlow.collectAsLazyPagingItems()

    TempusTheme {
        ExploreScreen(
            exploreState = dummyState,
            event = { /* Handle search event */ },
            navigateToDetails = { /* Handle navigation */ },
            articles = lazyPagingArticles,
            searchResults = lazyPagingArticles,
            isRefreshing = false,
            onRefresh = {}
        )
    }
}


