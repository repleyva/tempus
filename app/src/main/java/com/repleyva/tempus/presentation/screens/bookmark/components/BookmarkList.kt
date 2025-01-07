package com.repleyva.tempus.presentation.screens.bookmark.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.common.EmptyScreen
import com.repleyva.tempus.presentation.screens.explore.components.ExploreCard
import com.repleyva.tempus.presentation.theme.Dimensions.paddingExtraSmall
import com.repleyva.tempus.presentation.theme.Dimensions.paddingMedium

@Composable
fun BookmarkList(
    modifier: Modifier = Modifier,
    articles: List<Article>,
    onClick: (Article) -> Unit,
) {

    if (articles.isEmpty()) {
        EmptyScreen()
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(paddingMedium),
            contentPadding = PaddingValues(all = paddingExtraSmall)
        ) {
            items(articles) { article ->
                ExploreCard(
                    article = article,
                    onClick = { onClick(article) }
                )
            }
        }
    }
}