package com.repleyva.tempus.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.repleyva.tempus.domain.constants.Constants.dummyArticle
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.domain.use_cases.news.NewsUseCases
import com.repleyva.tempus.presentation.screens.bookmark.BookmarkEvent.GetArticles
import com.repleyva.tempus.presentation.screens.bookmark.BookmarkViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BookmarkViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: BookmarkViewModel

    @RelaxedMockK
    private lateinit var newsUseCases: NewsUseCases

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = BookmarkViewModel(newsUseCases)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getArticles fetches and updates state`() = runTest {
        // Given
        val sampleArticles = sampleNews()
        coEvery { newsUseCases.getArticles() } returns flowOf(sampleArticles)

        // When
        viewModel.eventHandler(GetArticles)

        // Then
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.articles).isEqualTo(sampleArticles)
            cancelAndIgnoreRemainingEvents()
        }
    }


    private fun sampleNews(): List<Article> {
        return listOf(
            dummyArticle.copy(title = "Article 1"),
            dummyArticle.copy(title = "Article 2"),
            dummyArticle.copy(title = "Article 3")
        )
    }
}
