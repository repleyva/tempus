package com.repleyva.tempus.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.repleyva.tempus.presentation.screens.onboarding.OnboardingEvent
import com.repleyva.tempus.presentation.screens.onboarding.OnboardingScreen
import com.repleyva.tempus.presentation.theme.TempusTheme
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingScreenTest {

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun verifyInitialPageIsDisplayed() {
        composeTestRule.setContent {
            TempusTheme {
                OnboardingScreen()
            }
        }

        composeTestRule.onNodeWithText("Explore").assertIsDisplayed()
    }

    @Test
    fun navigateToNextPage_whenNextButtonClicked() {
        composeTestRule.setContent {
            TempusTheme {
                OnboardingScreen()
            }
        }

        composeTestRule.onNodeWithText("Explore").performClick()

        composeTestRule.onNodeWithText("Next").assertIsDisplayed()
    }

    @Test
    fun navigateToPreviousPage_whenBackButtonClicked() {
        composeTestRule.setContent {
            TempusTheme {
                OnboardingScreen()
            }
        }

        composeTestRule.onNodeWithText("Explore").performClick()

        composeTestRule.onNodeWithText("Back").performClick()

        composeTestRule.onNodeWithText("Explore").assertIsDisplayed()
    }

    @Test
    fun verifyGetStartedTriggersEvent_onLastPage() = runBlocking {

        var saveAppEntryEventTriggered = false

        composeTestRule.setContent {
            TempusTheme {
                OnboardingScreen(
                    event = { event ->
                        if (event is OnboardingEvent.SaveAppEntry) {
                            saveAppEntryEventTriggered = true
                        }
                    }
                )
            }
        }

        composeTestRule.onNodeWithText("Explore").performClick()
        composeTestRule.onNodeWithText("Next").performClick()
        composeTestRule.onNodeWithText("Get Started").performClick()

        assert(saveAppEntryEventTriggered)
    }
}
