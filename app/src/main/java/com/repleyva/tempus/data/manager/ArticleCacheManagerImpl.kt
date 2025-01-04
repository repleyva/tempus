package com.repleyva.tempus.data.manager

import androidx.paging.PagingData
import com.repleyva.tempus.domain.constants.Constants.FIVE_MINUTES_MILLIS
import com.repleyva.tempus.domain.manager.ArticleCacheManager
import com.repleyva.tempus.domain.model.Article

class ArticleCacheManagerImpl() : ArticleCacheManager {

    private val articleCache = mutableMapOf<String, Pair<Long, PagingData<Article>>>()
    private val cacheDuration = FIVE_MINUTES_MILLIS

    override fun getCachedArticles(key: String): PagingData<Article>? {
        val cacheEntry = articleCache[key]
        if (cacheEntry != null) {
            val (timestamp, articles) = cacheEntry
            if (System.currentTimeMillis() - timestamp <= cacheDuration) {
                return articles
            }
        }
        return null
    }

    override fun cacheArticles(
        key: String,
        articles: PagingData<Article>,
    ) {
        articleCache[key] = Pair(System.currentTimeMillis(), articles)
    }

    override fun clearCache() {
        articleCache.clear()
    }
}