package com.repleyva.tempus.domain.use_cases.news

import com.repleyva.tempus.data.local.NewsDao

class GetArticles(
    private val newsDao: NewsDao,
) {
    operator fun invoke() = newsDao.getArticles()
}