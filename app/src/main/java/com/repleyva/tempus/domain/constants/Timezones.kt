package com.repleyva.tempus.domain.constants

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

object Timezones {

    val TIMEZONE_KEY: Preferences.Key<String> = stringPreferencesKey("timezone_key")

    val REGIONS = listOf(
        "GMT -11:00 Pago Pago, US (Pacific)",
        "GMT -10:00 Honolulu, US (Pacific)",
        "GMT -09:00 Anchorage, US (America)",
        "GMT -08:00 Los Angeles, US (America)",
        "GMT -07:00 Denver, US (America)",
        "GMT -06:00 Chicago, US (America)",
        "GMT -05:00 New York, US (America)",
        "GMT -05:00 Bogotá, CO (America)",
        "GMT -04:00 Halifax, CA (America)",
        "GMT -03:30 St. John's, CA (America)",
        "GMT -03:00 Buenos Aires, AR (America)",
        "GMT -01:00 Azores, PT (Atlantic)",
        "GMT +00:00 London, UK (Europe)",
        "GMT +01:00 Berlin, DE (Europe)",
        "GMT +02:00 Athens, GR (Europe)",
        "GMT +03:00 Riyadh, SA (Asia)",
        "GMT +03:30 Tehran, IR (Asia)",
        "GMT +04:00 Dubai, AE (Asia)",
        "GMT +04:30 Kabul, AF (Asia)",
        "GMT +05:00 Karachi, PK (Asia)",
        "GMT +05:30 Kolkata, IN (Asia)",
        "GMT +06:00 Dhaka, BD (Asia)",
        "GMT +07:00 Bangkok, TH (Asia)",
        "GMT +08:00 Manila, PH (Asia)",
        "GMT +09:00 Tokyo, JP (Asia)",
        "GMT +09:30 Darwin, AU (Australia)",
        "GMT +10:00 Sydney, AU (Australia)",
        "GMT +11:00 Noumea, NC (Pacific)",
        "GMT +12:00 Auckland, NZ (Pacific)",
    )

    val timezoneToCityMap = mapOf(
        0 to "Pago Pago",
        1 to "Honolulu",
        2 to "Anchorage",
        3 to "Los Angeles",
        4 to "Denver",
        5 to "Chicago",
        6 to "New York",
        7 to "Bogotá",
        8 to "Halifax",
        9 to "St. John's",
        10 to "Buenos Aires",
        11 to "Azores",
        12 to "London",
        13 to "Berlin",
        14 to "Athens",
        15 to "Riyadh",
        16 to "Tehran",
        17 to "Dubai",
        18 to "Kabul",
        19 to "Karachi",
        20 to "Kolkata",
        21 to "Dhaka",
        22 to "Bangkok",
        23 to "Manila",
        24 to "Tokyo",
        25 to "Darwin",
        26 to "Sydney",
        27 to "Noumea",
        28 to "Auckland"
    )
}