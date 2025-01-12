package com.repleyva.tempus.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.domain.model.Source
import com.repleyva.tempus.presentation.screens.bookmark.BookmarkScreen
import com.repleyva.tempus.presentation.screens.bookmark.BookmarkState
import com.repleyva.tempus.presentation.theme.TempusTheme
import org.junit.Rule
import org.junit.Test

class BookmarkScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val noSavedNewsMessage = context.getString(R.string.no_saved_news)

    private val sampleArticle1 = Article(
        author = "Author 1",
        content = "Content 1",
        description = "Description 1",
        publishedAt = "2023-01-01T00:00:00Z",
        source = Source(id = "source1", name = "Source 1"),
        title = "Title 1",
        url = "https://example.com/news1",
        urlToImage = "https://example.com/image1.jpg"
    )

    private val sampleArticle2 = Article(
        author = "Author 2",
        content = "Content 2",
        description = "Description 2",
        publishedAt = "2023-02-01T00:00:00Z",
        source = Source(id = "source2", name = "Source 2"),
        title = "Title 2",
        url = "https://example.com/news2",
        urlToImage = "https://example.com/image2.jpg"
    )

    @Test
    fun showEmptyState_whenBookmarkIsEmpty() {
        composeTestRule.setContent {
            TempusTheme {
                BookmarkScreen(
                    state = BookmarkState(articles = emptyList()),
                    navigateToDetails = {}
                )
            }
        }

        composeTestRule.onNodeWithText(noSavedNewsMessage).assertIsDisplayed()
    }

    @Test
    fun showBookmarks_whenBookmarkIsNotEmpty() {
        val bookmarks = listOf(sampleArticle1, sampleArticle2)

        composeTestRule.setContent {
            TempusTheme {
                BookmarkScreen(
                    state = BookmarkState(articles = bookmarks),
                    navigateToDetails = {}
                )
            }
        }

        composeTestRule.onNodeWithText(sampleArticle1.title.orEmpty()).assertIsDisplayed()
        composeTestRule.onNodeWithText(sampleArticle2.title.orEmpty()).assertIsDisplayed()
    }

    @Test
    fun navigateToDetails_whenArticleIsClicked() {
        var selectedArticle: Article? = null
        val bookmarks = listOf(sampleArticle1, sampleArticle2)

        composeTestRule.setContent {
            TempusTheme {
                BookmarkScreen(
                    state = BookmarkState(articles = bookmarks),
                    navigateToDetails = { selectedArticle = it }
                )
            }
        }

        composeTestRule.onNodeWithText(sampleArticle1.title.orEmpty()).performClick()
        assert(selectedArticle == sampleArticle1)
    }
}

