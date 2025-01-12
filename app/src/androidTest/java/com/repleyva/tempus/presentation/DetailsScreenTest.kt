package com.repleyva.tempus.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.repleyva.tempus.domain.constants.Constants.dummyArticle
import com.repleyva.tempus.presentation.extensions.formatDate
import com.repleyva.tempus.presentation.screens.detail.DetailsScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showDetailsScreen_whenArticleIsProvided() {
        composeTestRule.setContent {
            DetailsScreen(
                article = dummyArticle,
                event = { },
                navigateUp = { }
            )
        }

        composeTestRule.onNodeWithText(dummyArticle.title.orEmpty()).assertExists()
        composeTestRule.onNodeWithText(dummyArticle.publishedAt.orEmpty().formatDate()).assertExists()

        val partialContent = dummyArticle.content.orEmpty().take(30)
        composeTestRule.onNodeWithText(partialContent, substring = true).performScrollTo().assertExists()
    }

    @Test
    fun backButton_navigateUp() {
        var isNavigatedUp = false

        composeTestRule.setContent {
            DetailsScreen(
                article = dummyArticle,
                event = { },
                navigateUp = { isNavigatedUp = true }
            )
        }

        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(isNavigatedUp)
    }

    @Test
    fun bookmarkIconToggles_WhenBookmarkClicked() {
        var isBookmarked = false

        composeTestRule.setContent {
            DetailsScreen(
                article = dummyArticle,
                event = {
                    isBookmarked = !isBookmarked
                },
                navigateUp = { }
            )
        }

        composeTestRule.onNodeWithContentDescription("Bookmark").performClick()
        assert(isBookmarked)

        composeTestRule.onNodeWithContentDescription("Bookmark").performClick()
        assert(!isBookmarked)
    }


    @Test
    fun shareButton_triggersShareIntent() {
        composeTestRule.setContent {
            DetailsScreen(
                article = dummyArticle,
                event = { },
                navigateUp = { }
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("Share").performClick()
    }

    @Test
    fun browseButton_triggersBrowsingIntent() {
        composeTestRule.setContent {
            DetailsScreen(
                article = dummyArticle,
                event = { },
                navigateUp = { }
            )
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("Browser").performClick()
    }
}

