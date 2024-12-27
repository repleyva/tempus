package com.repleyva.tempus.data.remote.dto

import com.repleyva.tempus.domain.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int,
)