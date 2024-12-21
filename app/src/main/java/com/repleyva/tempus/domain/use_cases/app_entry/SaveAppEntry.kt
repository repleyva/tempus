package com.repleyva.tempus.domain.use_cases.app_entry

import com.repleyva.tempus.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager,
) {
    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}