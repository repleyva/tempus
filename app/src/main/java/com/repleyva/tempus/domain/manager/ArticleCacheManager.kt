package com.repleyva.tempus.domain.manager

import androidx.paging.PagingData
import com.repleyva.tempus.domain.model.Article

interface ArticleCacheManager {

    fun getCachedArticles(key: String): PagingData<Article>?

    fun cacheArticles(
        key: String,
        articles: PagingData<Article>,
    )

    fun clearCache()
}