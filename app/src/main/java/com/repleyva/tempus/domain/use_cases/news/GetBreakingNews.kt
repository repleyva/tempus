package com.repleyva.tempus.domain.use_cases.news

import com.repleyva.tempus.domain.repository.NewsRepository

class GetBreakingNews(
    private val newsRepository: NewsRepository,
) {
    operator fun invoke(
        country: String,
    ) = newsRepository.getBreakingNews(country)
}