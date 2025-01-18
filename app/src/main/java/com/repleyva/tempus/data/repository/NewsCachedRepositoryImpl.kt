package com.repleyva.tempus.data.repository

import com.repleyva.tempus.data.local.NewsDao
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.domain.repository.NewsCachedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsCachedRepositoryImpl @Inject constructor(
    private val newsDao: NewsDao,
) : NewsCachedRepository {

    override suspend fun upsertArticle(article: Article) = newsDao.upsert(article)

    override suspend fun deleteArticle(article: Article) = newsDao.delete(article)

    override fun getArticles(): Flow<List<Article>> = newsDao.getArticles()

    override suspend fun getArticle(url: String): Article? = newsDao.getArticle(url)

}