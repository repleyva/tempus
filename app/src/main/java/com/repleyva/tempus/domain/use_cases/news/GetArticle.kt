package com.repleyva.tempus.domain.use_cases.news

import com.repleyva.tempus.data.local.NewsDao

class GetArticle(
    private val newsDao: NewsDao,
) {
    suspend operator fun invoke(
        url: String,
    ) = newsDao.getArticle(url = url)
}