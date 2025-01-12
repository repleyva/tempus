package com.repleyva.tempus.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.repleyva.tempus.presentation.nav.NewsNavigator
import com.repleyva.tempus.presentation.screens.main.MainActivity
import com.repleyva.tempus.presentation.theme.TempusTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavHostTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupNewsNavHost() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            TempusTheme {
                NewsNavigator()
            }
        }
    }

    @Test
    fun verifyNewsNavigatorDisplayedAtStart() {
        composeTestRule.onNodeWithTag("NewsNavigator").assertExists()
    }

    @Test
    fun verifyBottomNavigationDisplayed() {
        composeTestRule.onNodeWithTag("BottomNavigation").assertExists()
    }

    @Test
    fun verifyExploreScreenIsDisplayed() {
        composeTestRule.onNodeWithText("Explore").performClick()
        composeTestRule.onNodeWithTag("ExploreScreen").assertIsDisplayed()
    }

    @Test
    fun verifyBookmarkScreenIsDisplayed() {
        composeTestRule.onNodeWithText("Bookmark").performClick()
        composeTestRule.onNodeWithTag("BookmarkScreen").assertIsDisplayed()
    }

    @Test
    fun verifySettingsScreenIsDisplayed() {
        composeTestRule.onNodeWithText("Settings").performClick()
        composeTestRule.onNodeWithTag("SettingsScreen").assertIsDisplayed()
    }

    @Test
    fun verifyTimezoneDialogDisplayed_onSettingsScreen() {
        composeTestRule.onNodeWithText("Settings").performClick()
        composeTestRule.onNodeWithTag("TimezoneOption").performClick()
        composeTestRule.onNodeWithTag("TimezoneDialog").assertExists()
    }

    @Test
    fun verifyHomeScreenIsDisplayed() {
        composeTestRule.onNodeWithText("Home").performClick()
        composeTestRule.onNodeWithTag("HomeScreen").assertIsDisplayed()
    }
}
