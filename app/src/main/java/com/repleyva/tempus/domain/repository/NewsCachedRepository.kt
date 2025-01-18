package com.repleyva.tempus.domain.repository

import com.repleyva.tempus.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsCachedRepository {

    suspend fun upsertArticle(article: Article)

    suspend fun deleteArticle(article: Article)

    fun getArticles(): Flow<List<Article>>

    suspend fun getArticle(url: String): Article?

}