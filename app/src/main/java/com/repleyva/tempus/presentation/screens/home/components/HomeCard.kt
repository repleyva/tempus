package com.repleyva.tempus.presentation.screens.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.repleyva.tempus.domain.constants.Constants.dummyArticle
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.common.CardSourceTextLarge
import com.repleyva.tempus.presentation.common.CardTitleTextLarge
import com.repleyva.tempus.presentation.common.HomeArticleImage
import com.repleyva.tempus.presentation.theme.Dimensions.homeBreakingNewsHeight
import com.repleyva.tempus.presentation.theme.Dimensions.homeBreakingNewsWidth
import com.repleyva.tempus.presentation.theme.Dimensions.paddingNormal
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSmall
import com.repleyva.tempus.presentation.theme.TempusTheme

@Composable
fun HomeCard(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: (() -> Unit)? = null,
) {

    if (article.title.orEmpty().isEmpty() || article.url.isEmpty()) {
        return
    }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .width(homeBreakingNewsWidth)
            .height(homeBreakingNewsHeight)
            .clickable { onClick?.invoke() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        HomeArticleImage(
            articleUrl = article.urlToImage.orEmpty(),
            context = context
        )

        CardTitleTextLarge(
            modifier = Modifier.padding(top = paddingSmall),
            text = article.title.orEmpty()
        )

        CardSourceTextLarge(
            modifier = Modifier.padding(bottom = paddingNormal),
            text = article.source.name
        )
    }
}


@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsSliderPreview() {
    TempusTheme(
        dynamicColor = false
    ) {
        HomeCard(
            article = dummyArticle
        ) {}
    }
}