package com.repleyva.tempus.domain

import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.repleyva.tempus.data.local.NewsDao
import com.repleyva.tempus.domain.constants.Constants.dummyArticle
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.domain.model.Source
import com.repleyva.tempus.domain.repository.NewsCachedRepository
import com.repleyva.tempus.domain.repository.NewsRepository
import com.repleyva.tempus.domain.use_cases.news.DeleteArticle
import com.repleyva.tempus.domain.use_cases.news.GetBreakingNews
import com.repleyva.tempus.domain.use_cases.news.GetCategorizedNews
import com.repleyva.tempus.domain.use_cases.news.GetNewsEverything
import com.repleyva.tempus.domain.use_cases.news.SearchNews
import com.repleyva.tempus.domain.use_cases.news.UpsertArticle
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class NewsUseCasesTest {

    @RelaxedMockK
    private lateinit var repository: NewsRepository

    @RelaxedMockK
    private lateinit var newsCachedRepository: NewsCachedRepository

    private lateinit var getBreakingNews: GetBreakingNews
    private lateinit var getNewsEverything: GetNewsEverything
    private lateinit var getCategorizedNews: GetCategorizedNews
    private lateinit var searchNews: SearchNews
    private lateinit var upsertArticle: UpsertArticle
    private lateinit var deleteArticle: DeleteArticle

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        getBreakingNews = GetBreakingNews(repository)
        getNewsEverything = GetNewsEverything(repository)
        getCategorizedNews = GetCategorizedNews(repository)
        searchNews = SearchNews(repository)
        upsertArticle = UpsertArticle(newsCachedRepository)
        deleteArticle = DeleteArticle(newsCachedRepository)
    }

    @Test
    fun `getBreakingNews fetches data from Repository`() = runTest {
        // Given
        val news = sampleNews()
        coEvery { repository.getBreakingNews(any()) } returns flowOf(news)

        // When
        val result = getBreakingNews("us").first()

        // Then
        assertThat(result).isEqualTo(news)
        coVerify { repository.getBreakingNews("us") }
    }

    @Test
    fun `getNewsEverything fetches data from Repository`() = runTest {
        // Given
        val news = sampleNews()
        coEvery { repository.getNewsEverything(any()) } returns flowOf(news)

        // When
        val result = getNewsEverything(listOf("source1", "source2")).first()

        // Then
        assertThat(result).isEqualTo(news)
        coVerify { repository.getNewsEverything(any()) }
    }

    @Test
    fun `getCategorizedNews fetches data from Repository`() = runTest {
        // Given
        val news = sampleNews()
        coEvery { repository.getCategorizedNews(any()) } returns flowOf(news)

        // When
        val result = getCategorizedNews("business").first()

        // Then
        assertThat(result).isEqualTo(news)
        coVerify { repository.getCategorizedNews("business") }
    }

    @Test
    fun `searchNews fetches data from Repository`() = runTest {
        // Given
        val news = sampleNews()
        coEvery { repository.searchNews(any(), any()) } returns flowOf(news)

        // When
        val result = searchNews("query", listOf("source1", "source2")).first()

        // Then
        assertThat(result).isEqualTo(news)
        coVerify { repository.searchNews("query", any()) }
    }

    @Test
    fun `upsertArticle calls Dao to insert article`() = runTest {
        // Given
        val article = sampleArticle()

        // When
        upsertArticle(article)

        // Then
        coVerify { newsCachedRepository.upsertArticle(article) }
    }

    @Test
    fun `deleteArticle calls Dao to delete article`() = runTest {
        // Given
        val article = sampleArticle()

        // When
        deleteArticle(article)

        // Then
        coVerify { newsCachedRepository.deleteArticle(article) }
    }

    private fun sampleNews(): PagingData<Article> = PagingData.from(
        listOf(
            Article(
                title = "title",
                description = "description",
                url = "url",
                urlToImage = "urlToImage",
                publishedAt = "publishedAt",
                content = "content",
                source = Source("id", "name"),
                author = "author"
            )
        )
    )

    private fun sampleArticle(): Article {
        return dummyArticle
    }
}
