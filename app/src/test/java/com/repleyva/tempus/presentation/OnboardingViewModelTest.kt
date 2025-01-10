package com.repleyva.tempus.presentation

import com.repleyva.tempus.domain.use_cases.app_entry.AppEntryUseCases
import com.repleyva.tempus.presentation.screens.onboarding.OnboardingEvent
import com.repleyva.tempus.presentation.screens.onboarding.OnboardingViewModel
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
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
class OnboardingViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var viewModel: OnboardingViewModel

    @RelaxedMockK
    private lateinit var appEntryUseCases: AppEntryUseCases

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = OnboardingViewModel(appEntryUseCases)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `eventHandler SaveAppEntry calls saveAppEntry`() = runTest {
        // When
        viewModel.eventHandler(OnboardingEvent.SaveAppEntry)

        // Then
        coVerify { appEntryUseCases.saveAppEntry() }
    }
}
