package com.repleyva.tempus.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.repleyva.tempus.domain.constants.Constants.DARK_MODE
import com.repleyva.tempus.domain.constants.Constants.NICKNAME
import com.repleyva.tempus.domain.constants.Constants.SELECTED_EMOJI
import com.repleyva.tempus.domain.constants.Timezones.REGIONS
import com.repleyva.tempus.domain.constants.Timezones.TIMEZONE_KEY
import com.repleyva.tempus.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>
): SettingsRepository {

    override fun getMode(): Flow<Boolean> = dataStore.data.catch {  exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences ->
        preferences[DARK_MODE] ?: false
    }

    override suspend fun updateDarkMode(isDarkModeEnabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[DARK_MODE] = isDarkModeEnabled
        }
    }

    override fun getNickname(): Flow<String> = dataStore.data.map { preferences ->
        preferences[NICKNAME].orEmpty()
    }

    override suspend fun updateNickname(nickname: String) {
        dataStore.edit { preferences ->
            preferences[NICKNAME] = nickname
        }
    }

    override fun getSelectedEmoji(): Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[SELECTED_EMOJI] ?: "\uD83D\uDE36"
        }

    override suspend fun updateSelectedEmoji(emoji: String) {
        dataStore.edit { preferences ->
            preferences[SELECTED_EMOJI] = emoji
        }
    }

    override fun getTimezone(): Flow<String> {
        return dataStore.data
            .map { preferences ->
                preferences[TIMEZONE_KEY] ?: REGIONS[7]
            }
    }

    override suspend fun updateTimezone(newTimezone: String) {
        dataStore.edit { preferences ->
            preferences[TIMEZONE_KEY] = newTimezone
        }
    }

    override suspend fun clearPreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}