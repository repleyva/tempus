package com.repleyva.tempus.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.repleyva.tempus.domain.constants.Constants.dummyArticle
import com.repleyva.tempus.domain.use_cases.news.NewsUseCases
import com.repleyva.tempus.presentation.screens.detail.DetailsEvent.UpsertDeleteArticle
import com.repleyva.tempus.presentation.screens.detail.DetailsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.just
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: DetailsViewModel

    @RelaxedMockK
    private lateinit var newsUseCases: NewsUseCases

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailsViewModel(newsUseCases)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `upsert DeleteArticle and adds article if not present`() = runTest {
        // Given
        val article = sampleNews()

        coEvery { newsUseCases.getArticle(article.url) } returns null
        // Mock the upsertArticle action
        coEvery { newsUseCases.upsertArticle(article) } just runs

        // When
        viewModel.eventHandler(UpsertDeleteArticle(article))

        // Then
        coVerify { newsUseCases.upsertArticle(article) }
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.sideEffect).isEqualTo("Article Saved")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should delete article when article is present`() = runTest {
        // Given
        val article = sampleNews()

        coEvery { newsUseCases.getArticle(article.url) } returns article
        // Mock the deleteArticle action
        coEvery { newsUseCases.deleteArticle(article) } just runs

        // When
        viewModel.eventHandler(UpsertDeleteArticle(article))

        // Then
        coVerify { newsUseCases.deleteArticle(article) }
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.sideEffect).isEqualTo("Article Deleted")
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun sampleNews() = dummyArticle
}
