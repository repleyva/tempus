package com.repleyva.tempus.presentation

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.repleyva.tempus.domain.repository.SettingsRepository
import com.repleyva.tempus.presentation.screens.settings.SettingsEvent.UpdateDarkMode
import com.repleyva.tempus.presentation.screens.settings.SettingsEvent.UpdateNickname
import com.repleyva.tempus.presentation.screens.settings.SettingsEvent.UpdateSelectedEmoji
import com.repleyva.tempus.presentation.screens.settings.SettingsEvent.UpdateTimezone
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
        viewModel.eventHandler(UpdateDarkMode(true))

        // Then
        coVerify { settingsRepository.updateDarkMode(true) }
    }

    @Test
    fun `update nickname calls repository with correct nickname`() = runTest {
        // When
        val newNickname = sampleNickname
        viewModel.eventHandler(UpdateNickname(newNickname))

        // Then
        coVerify { settingsRepository.updateNickname(newNickname) }
    }

    @Test
    fun `update selected emoji calls repository with correct emoji`() = runTest {
        // When
        val newEmoji = sampleEmoji
        viewModel.eventHandler(UpdateSelectedEmoji(newEmoji))

        // Then
        coVerify { settingsRepository.updateSelectedEmoji(newEmoji) }
    }

    @Test
    fun `update timezone calls repository with correct timezone`() = runTest {
        // When
        val newTimezone = sampleTimezone
        viewModel.eventHandler(UpdateTimezone(newTimezone))

        // Then
        coVerify { settingsRepository.updateTimezone(newTimezone) }
    }

    @Test
    fun `theme should be correct based on repository value`() = runTest {
        // Given
        val expectedTheme = false
        coEvery { settingsRepository.getMode() } returns flowOf(expectedTheme)

        // When
        viewModel.uiState.test {
            val state = awaitItem()

            state.theme.test {
                val themeValue = awaitItem()
                // Then
                assertThat(themeValue).isEqualTo(expectedTheme)
                cancelAndIgnoreRemainingEvents()
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `nickname should be correct based on repository value`() = runTest {
        // Given
        val expectedNickname = sampleNickname
        coEvery { settingsRepository.getNickname() } returns flowOf(expectedNickname)

        // When
        viewModel.uiState.test {
            val state = awaitItem()

            state.nickname.test {
                val nicknameValue = awaitItem()
                // Then
                assertThat(nicknameValue).isEqualTo(expectedNickname)
                cancelAndIgnoreRemainingEvents()
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `selectedEmoji should be correct based on repository value`() = runTest {
        // Given
        val expectedEmoji = sampleEmoji
        coEvery { settingsRepository.getSelectedEmoji() } returns flowOf(expectedEmoji)

        // When
        viewModel.uiState.test {
            val state = awaitItem()

            state.selectedEmoji.test {
                val emojiValue = awaitItem()
                // Then
                assertThat(emojiValue).isEqualTo(expectedEmoji)
                cancelAndIgnoreRemainingEvents()
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `selectedTimezone should be correct based on repository value`() = runTest {
        // Given
        val expectedTimezone = sampleTimezone
        coEvery { settingsRepository.getTimezone() } returns flowOf(expectedTimezone)

        // When
        viewModel.uiState.test {
            val state = awaitItem()

            state.selectedTimezone.test {
                val timezoneValue = awaitItem()
                // Then
                assertThat(timezoneValue).isEqualTo(expectedTimezone)
                cancelAndIgnoreRemainingEvents()
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    private val sampleTimezone = "America/New York"
    private val sampleNickname = "John Doe"
    private val sampleEmoji = "\uD83D\uDE36"
}
