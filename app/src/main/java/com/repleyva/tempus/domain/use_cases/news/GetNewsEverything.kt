package com.repleyva.tempus.domain.use_cases.news

import com.repleyva.tempus.domain.repository.NewsRepository

class GetNewsEverything (
    private val newsRepository: NewsRepository
) {
    operator fun invoke(
        sources: List<String>
    )= newsRepository.getNewsEverything(sources)
}