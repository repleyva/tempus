package com.repleyva.tempus.domain.use_cases.news

import com.repleyva.tempus.domain.repository.NewsCachedRepository

class GetArticles(
    private val newsCachedRepository: NewsCachedRepository,
) {
    operator fun invoke() = newsCachedRepository.getArticles()
}