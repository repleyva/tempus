package com.repleyva.tempus.domain.use_cases.news

import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.domain.repository.NewsCachedRepository

class DeleteArticle(
    private val newsCachedRepository: NewsCachedRepository,
) {

    suspend operator fun invoke(
        article: Article,
    ) {
        newsCachedRepository.deleteArticle(article)
    }
}