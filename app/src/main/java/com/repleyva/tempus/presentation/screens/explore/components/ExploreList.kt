package com.repleyva.tempus.presentation.screens.explore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.extensions.articlesPagingResultHandler
import com.repleyva.tempus.presentation.theme.Dimensions.paddingExtraSmall
import com.repleyva.tempus.presentation.theme.Dimensions.paddingMedium

@Composable
fun ExploreList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit,
) {

    val handlePagingResult = articles.articlesPagingResultHandler { ShimmerEffect() }

    if (handlePagingResult && articles.itemCount > 0) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(paddingMedium),
            contentPadding = PaddingValues(all = paddingExtraSmall)
        ) {
            items(count = articles.itemCount) { index ->
                articles[index]?.let { article ->
                    ExploreCard(
                        article = article,
                        onClick = { onClick(article) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(paddingMedium)) {
        repeat(10) {
            ExploreCardEffect(
                modifier = Modifier.padding(horizontal = paddingMedium)
            )
        }
    }
}




