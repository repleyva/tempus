package com.repleyva.tempus.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.common.EmptyScreen

@Composable
fun LazyPagingItems<Article>.articlesPagingResultHandler(
    shimmerEffect: @Composable () -> Unit,
): Boolean {

    val loadState = loadState

    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            shimmerEffect()
            false
        }
        error != null -> {
            EmptyScreen(error = error)
            false
        }
        itemCount < 1 -> {
            EmptyScreen()
            false
        }
        else -> true
    }
}