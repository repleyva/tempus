package com.repleyva.tempus.presentation.screens.detail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.constants.Constants.dummyArticle
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.common.ArticleDetails
import com.repleyva.tempus.presentation.common.ContentTitleText
import com.repleyva.tempus.presentation.common.DetailsArticleImage
import com.repleyva.tempus.presentation.extensions.cleanContent
import com.repleyva.tempus.presentation.extensions.openUrl
import com.repleyva.tempus.presentation.extensions.shareUrl
import com.repleyva.tempus.presentation.theme.Dimensions.paddingExtraLarge
import com.repleyva.tempus.presentation.theme.Dimensions.paddingMedium
import com.repleyva.tempus.presentation.theme.Dimensions.paddingNormal
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSmall
import com.repleyva.tempus.presentation.theme.TempusTheme

@Composable
fun DetailsScreen(
    article: Article,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit,
) {

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        topBar = {
            DetailsBar(
                onBrowsingClick = { context.openUrl(article.url) },
                onShareClick = { context.shareUrl(article.url) },
                onBookmarkClick = { event(DetailsEvent.UpsertDeleteArticle(article)) },
                onBackClick = navigateUp
            )
        }
    ) { innerPadding ->
        DetailsBody(
            innerPadding = innerPadding,
            article = article,
            context = context
        )
    }
}

@Composable
private fun DetailsBody(
    innerPadding: PaddingValues,
    article: Article,
    context: Context,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {

        DetailsArticleImage(
            articleUrl = article.urlToImage.orEmpty(),
            context = context
        )

        ContentValidator(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f)
                .align(Alignment.BottomCenter),
            article = article
        )
    }
}

@Composable
private fun ContentValidator(
    modifier: Modifier,
    article: Article,
) {
    if (article.content.isNullOrEmpty()) {
        ArticleWebView(article = article)
    } else {
        DetailsWithContent(
            modifier = modifier,
            article = article
        )
    }
}

@Composable
private fun DetailsWithContent(
    modifier: Modifier,
    article: Article,
) {
    LazyColumn(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    topStart = paddingExtraLarge,
                    topEnd = paddingExtraLarge
                )
            )
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(
            start = paddingMedium,
            end = paddingMedium,
            top = paddingMedium
        ),
        verticalArrangement = Arrangement.spacedBy(paddingSmall),
    ) {

        item {

            ContentTitleText(
                text = article.title.orEmpty(),
                textAlign = TextAlign.Left
            )

            ArticleDetails(article)

            val articleContent = article.content.cleanContent()

            val briefContent = buildAnnotatedString {
                append(articleContent)
                withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                    append(stringResource(R.string.read_more))
                }
            }

            Text(
                modifier = Modifier.padding(top = paddingNormal),
                text = briefContent,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
fun ArticleWebView(article: Article) {

    val context = LocalContext.current

    AndroidView(
        factory = {
            android.webkit.WebView(context).apply {
                webViewClient = android.webkit.WebViewClient()
                loadUrl(article.url)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    TempusTheme {
        DetailsScreen(
            article = dummyArticle,
            event = {}
        ) {}
    }
}