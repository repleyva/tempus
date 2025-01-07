package com.repleyva.tempus.domain.extensions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.repleyva.tempus.domain.constants.Timezones.REGIONS
import com.repleyva.tempus.domain.constants.Timezones.TIMEZONE_KEY
import com.repleyva.tempus.domain.constants.Timezones.timezoneToCityMap
import kotlinx.coroutines.flow.first

suspend fun DataStore<Preferences>.timezoneToCity(): String {
    val preferences = data.first()
    val timezoneString = preferences[TIMEZONE_KEY] ?: "GMT -05:00 Bogotá, CO (America)"
    val timezoneIndex = REGIONS.indexOfFirst { it.contains(timezoneString) }
    return timezoneToCityMap[timezoneIndex] ?: "Bogotá"
}