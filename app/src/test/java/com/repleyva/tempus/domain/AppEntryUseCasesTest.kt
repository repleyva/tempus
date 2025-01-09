package com.repleyva.tempus.domain

import com.google.common.truth.Truth.assertThat
import com.repleyva.tempus.domain.manager.LocalUserManager
import com.repleyva.tempus.domain.use_cases.app_entry.ReadAppEntry
import com.repleyva.tempus.domain.use_cases.app_entry.SaveAppEntry
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AppEntryUseCasesTest {

    @RelaxedMockK
    private lateinit var localUserManager: LocalUserManager

    private lateinit var readAppEntry: ReadAppEntry

    private lateinit var saveAppEntry: SaveAppEntry

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        readAppEntry = ReadAppEntry(localUserManager)
        saveAppEntry = SaveAppEntry(localUserManager)
    }

    @Test
    fun `readAppEntry should return correct value from LocalUserManager`() = runTest {
        // Given
        coEvery { localUserManager.readAppEntry() } returns flowOf(true)

        // When
        val result = readAppEntry().first()

        // Then
        assertThat(result).isTrue()
        coVerify { localUserManager.readAppEntry() }
    }

    @Test
    fun `saveAppEntry calls LocalUserManager to save entry`() = runTest {
        // When
        saveAppEntry()

        // Then
        coVerify { localUserManager.saveAppEntry() }
    }
}
