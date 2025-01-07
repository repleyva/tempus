package com.repleyva.tempus.domain.use_cases.news

import com.repleyva.tempus.domain.repository.NewsRepository

class SearchNews(
    private val newsRepository: NewsRepository,
) {
    operator fun invoke(
        searchQuery: String,
        sources: List<String>,
    ) = newsRepository.searchNews(searchQuery, sources)
}