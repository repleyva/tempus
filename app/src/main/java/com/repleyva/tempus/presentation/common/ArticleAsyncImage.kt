package com.repleyva.tempus.presentation.common

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.repleyva.tempus.R
import com.repleyva.tempus.presentation.theme.Dimensions.articleCardSizeHeight
import com.repleyva.tempus.presentation.theme.Dimensions.articleCardSizeWidth
import com.repleyva.tempus.presentation.theme.Dimensions.homeBreakingNewsWidth

@Composable
fun ArticleAsyncImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier,
    context: Context,
) {

    var imageLoaded by remember { mutableStateOf(false) }

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .placeholder(R.drawable.ic_image_error)
            .error(R.drawable.ic_image_error)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .border(
                if (!imageLoaded) 2.dp else 0.dp,
                MaterialTheme.colorScheme.surface,
                MaterialTheme.shapes.medium
            ),
        onState = { state ->
            imageLoaded = state is AsyncImagePainter.State.Success
        }
    )
}

@Composable
fun HomeArticleImage(
    articleUrl: String,
    context: Context,
) {
    ArticleAsyncImage(
        imageUrl = articleUrl,
        contentDescription = null,
        modifier = Modifier
            .size(homeBreakingNewsWidth)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.background),
        context = context
    )
}

@Composable
fun ExploreArticleImage(
    articleUrl: String,
    context: Context,
) {
    ArticleAsyncImage(
        imageUrl = articleUrl,
        contentDescription = null,
        modifier = Modifier
            .width(articleCardSizeWidth)
            .height(articleCardSizeHeight)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.background),
        context = context
    )
}


@Composable
fun DetailsArticleImage(
    articleUrl: String,
    context: Context,
) {
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(articleUrl)
            .placeholder(R.drawable.ic_image_error)
            .error(R.drawable.ic_image_error)
            .crossfade(true)
            .build(),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .background(MaterialTheme.colorScheme.surface),
    )
}