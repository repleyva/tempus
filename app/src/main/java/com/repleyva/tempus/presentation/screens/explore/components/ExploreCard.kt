package com.repleyva.tempus.presentation.screens.explore.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.constants.Constants.dummyArticle
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.common.CardSourceTextSmall
import com.repleyva.tempus.presentation.common.CardTitleTextSmall
import com.repleyva.tempus.presentation.common.ExploreArticleImage
import com.repleyva.tempus.presentation.extensions.formatDate
import com.repleyva.tempus.presentation.theme.Dimensions.iconSmall
import com.repleyva.tempus.presentation.theme.Dimensions.paddingExtraSmall
import com.repleyva.tempus.presentation.theme.Dimensions.paddingMedium
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSmall
import com.repleyva.tempus.presentation.theme.TempusTheme

@Composable
fun ExploreCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (() -> Unit)? = null,
) {

    if (article.title.orEmpty().isEmpty() || article.url.isEmpty()) {
        return
    }

    val context = LocalContext.current

    Row(
        modifier = modifier
            .clickable { onClick?.invoke() }
            .padding(horizontal = paddingMedium),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(paddingSmall)
    ) {

        ArticleDescription(
            modifier = Modifier.weight(1f),
            article = article
        )

        ExploreArticleImage(
            articleUrl = article.urlToImage.orEmpty(),
            context = context
        )
    }
}

@Composable
private fun ArticleDescription(
    modifier: Modifier,
    article: Article,
) {
    Column(
        modifier = modifier.padding(end = paddingSmall),
        verticalArrangement = Arrangement.spacedBy(paddingExtraSmall)
    ) {

        CardTitleTextSmall(
            text = article.title.orEmpty(),
        )

        ArticleDetails(article)
    }
}

@Composable
private fun ArticleDetails(article: Article) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(paddingExtraSmall)
    ) {

        CardSourceTextSmall(
            modifier = Modifier.padding(end = paddingExtraSmall),
            text = article.source.name,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_time),
            contentDescription = null,
            modifier = Modifier.size(iconSmall),
            tint = MaterialTheme.colorScheme.secondary
        )

        CardSourceTextSmall(
            text = article.publishedAt.orEmpty().formatDate(),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ExploreCardPreview() {
    TempusTheme(
        dynamicColor = false
    ) {
        ExploreCard(
            article = dummyArticle
        )
    }
}