package com.repleyva.tempus.domain.extensions

import androidx.paging.PagingData
import androidx.paging.filter
import com.repleyva.tempus.domain.model.Article

fun PagingData<Article>.filterArticles(): PagingData<Article> {
    return this.filter { article ->
        val hasValidTitle = article.title.orEmpty().isNotEmpty()
        val contentIsNotRemoved = !article.content.orEmpty().contains("[Removed]")
        val hasValidImage = article.urlToImage.orEmpty().isNotEmpty()

        hasValidTitle && contentIsNotRemoved && hasValidImage
    }
}