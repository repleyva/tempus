package com.repleyva.tempus.domain.repository

import androidx.paging.PagingData
import com.repleyva.tempus.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getBreakingNews(country: String): Flow<PagingData<Article>>

    fun getNewsEverything(sources: List<String>): Flow<PagingData<Article>>

    fun getCategorizedNews(category: String): Flow<PagingData<Article>>

    fun getNewsCategories(): List<String>

    fun searchNews(
        searchQuery: String,
        sources: List<String>,
    ): Flow<PagingData<Article>>

}