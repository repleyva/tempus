package com.repleyva.tempus.presentation.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.extensions.articlesPagingResultHandler
import com.repleyva.tempus.presentation.theme.Dimensions.paddingLarge
import com.repleyva.tempus.presentation.theme.Dimensions.paddingMedium
import com.repleyva.tempus.presentation.theme.Dimensions.paddingZero

@Composable
fun HomeList(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onClick: (Article) -> Unit,
) {

    val handlePagingResult = articles.articlesPagingResultHandler { ShimmerEffect() }

    if (handlePagingResult) {

        if (articles.itemCount > 0) {
            LazyRow(
                modifier = modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(paddingMedium)
            ) {
                items(count = minOf(articles.itemCount, 5)) { index ->
                    articles[index]?.let {
                        HomeCard(
                            modifier = Modifier.padding(
                                start = if (index == 0) paddingLarge else paddingZero,
                                end = if (index == 4) paddingLarge else paddingZero
                            ),
                            article = it,
                            onClick = { onClick(it) }
                        )
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.no_news_available),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
private fun ShimmerEffect() {
    Row(horizontalArrangement = Arrangement.spacedBy(paddingMedium)) {
        repeat(5) { index ->
            BreakingNewsCardEffect(
                modifier = Modifier.padding(
                    start = if (index == 0) paddingLarge else paddingZero,
                    end = if (index == 4) paddingLarge else paddingZero
                )
            )
        }
    }
}




