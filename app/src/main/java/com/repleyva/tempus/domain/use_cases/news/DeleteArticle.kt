package com.repleyva.tempus.domain.use_cases.news

import com.repleyva.tempus.data.local.NewsDao
import com.repleyva.tempus.domain.model.Article

class DeleteArticle(
    private val newsDao: NewsDao,
) {
    suspend operator fun invoke(
        article: Article,
    ) {
        newsDao.delete(article)
    }
}