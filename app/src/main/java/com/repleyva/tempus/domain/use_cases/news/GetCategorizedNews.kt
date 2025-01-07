package com.repleyva.tempus.domain.use_cases.news

import com.repleyva.tempus.domain.repository.NewsRepository

class GetCategorizedNews(
    private val newsRepository: NewsRepository,
) {
    operator fun invoke(
        category: String,
    ) = newsRepository.getCategorizedNews(category)
}