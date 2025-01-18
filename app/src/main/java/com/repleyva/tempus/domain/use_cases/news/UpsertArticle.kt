package com.repleyva.tempus.domain.use_cases.news

import android.util.Log
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.domain.repository.NewsCachedRepository

class UpsertArticle(
    private val newsCachedRepository: NewsCachedRepository,
) {

    suspend operator fun invoke(article: Article) {
        if (article.source.name.isNotEmpty()) {
            newsCachedRepository.upsertArticle(article)
        } else {
            Log.e("UpsertArticle", "Source name is null.")
        }
    }
}