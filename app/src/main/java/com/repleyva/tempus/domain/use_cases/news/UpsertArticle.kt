package com.repleyva.tempus.domain.use_cases.news

import android.util.Log
import com.repleyva.tempus.data.local.NewsDao
import com.repleyva.tempus.domain.model.Article

class UpsertArticle(
    private val newsDao: NewsDao,
) {
    suspend operator fun invoke(article: Article) {
        if (article.source.name.isNotEmpty()) {
            newsDao.upsert(article)
        } else {
            Log.e("UpsertArticle", "Source name is null.")
        }
    }
}