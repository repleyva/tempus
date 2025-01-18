package com.repleyva.tempus.domain.use_cases.news

import com.repleyva.tempus.domain.repository.NewsCachedRepository

class GetArticle(
    private val newsCachedRepository: NewsCachedRepository,
) {

    suspend operator fun invoke(
        url: String,
    ) = newsCachedRepository.getArticle(url = url)
}