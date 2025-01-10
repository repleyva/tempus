package com.repleyva.tempus.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.repleyva.tempus.domain.repository.SettingsRepository
import com.repleyva.tempus.presentation.screens.settings.SettingsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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
class SettingsViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: SettingsViewModel

    @RelaxedMockK
    private lateinit var settingsRepository: SettingsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = SettingsViewModel(settingsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `update dark mode calls repository with correct value`() = runTest {
        // When
        viewModel.updateDarkMode(true)

        // Then
        coVerify { settingsRepository.updateDarkMode(true) }
    }

    @Test
    fun `update nickname calls repository with correct nickname`() = runTest {
        // When
        val newNickname = sampleNickname
        viewModel.updateNickname(newNickname)

        // Then
        coVerify { settingsRepository.updateNickname(newNickname) }
    }

    @Test
    fun `update selected emoji calls repository with correct emoji`() = runTest {
        // When
        val newEmoji = sampleEmoji
        viewModel.updateSelectedEmoji(newEmoji)

        // Then
        coVerify { settingsRepository.updateSelectedEmoji(newEmoji) }
    }

    @Test
    fun `update timezone calls repository with correct timezone`() = runTest {
        // When
        val newTimezone = sampleTimezone
        viewModel.updateTimezone(newTimezone)

        // Then
        coVerify { settingsRepository.updateTimezone(newTimezone) }
    }

    @Test
    fun `theme emits correct value from repository`() = runTest {
        // Given
        val isDarkModeEnabled = false
        coEvery { settingsRepository.getMode() } returns flowOf(isDarkModeEnabled)

        // When
        viewModel.theme.test {
            val themeValue = awaitItem()

            // Then
            assertThat(themeValue).isEqualTo(isDarkModeEnabled)
        }
    }

    @Test
    fun `nickname emits correct value from repository`() = runTest {
        // Given
        val nickname = sampleNickname
        coEvery { settingsRepository.getNickname() } returns flowOf(nickname)

        // When
        viewModel.nickname.test {
            val nicknameValue = awaitItem()

            // Then
            assertThat(nicknameValue).isEqualTo(nickname)
        }
    }

    @Test
    fun `selected emoji emits correct value from repository`() = runTest {
        // Given
        val emoji = sampleEmoji
        coEvery { settingsRepository.getSelectedEmoji() } returns flowOf(emoji)

        // When
        viewModel.selectedEmoji.test {
            val emojiValue = awaitItem()

            // Then
            assertThat(emojiValue).isEqualTo(emoji)
        }
    }

    @Test
    fun `selected timezone emits correct value from repository`() = runTest {
        // Given
        val timezone = sampleTimezone
        coEvery { settingsRepository.getTimezone() } returns flowOf(timezone)

        // When
        viewModel.selectedTimezone.test {
            val timezoneValue = awaitItem()

            // Then
            assertThat(timezoneValue).isEqualTo(timezone)
        }
    }

    private val sampleTimezone = "America/Bogota"
    private val sampleNickname = ""
    private val sampleEmoji = "\uD83D\uDE36"
}
