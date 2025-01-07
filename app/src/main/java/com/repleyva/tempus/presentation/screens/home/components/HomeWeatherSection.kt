package com.repleyva.tempus.presentation.screens.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.constants.Timezones.REGIONS
import com.repleyva.tempus.domain.constants.Timezones.timezoneToCityMap
import com.repleyva.tempus.domain.model.WeatherData
import com.repleyva.tempus.presentation.common.WeatherText
import com.repleyva.tempus.presentation.extensions.formatDay
import com.repleyva.tempus.presentation.extensions.weatherDate
import com.repleyva.tempus.presentation.theme.Dimensions.iconWeatherSize
import com.repleyva.tempus.presentation.theme.Dimensions.paddingExtraSmall
import com.repleyva.tempus.presentation.theme.Dimensions.paddingNormal
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSmall
import com.repleyva.tempus.presentation.theme.Dimensions.topBarHeight
import com.repleyva.tempus.presentation.theme.TempusTheme

const val bogotaTimezoneIndex = 7

@Composable
fun HomeWeatherBar(
    weatherData: WeatherData?,
    timezoneOffset: Int,
    timezone: String,
    nickname: String,
    selectedEmoji: String,
) {

    val timezoneIndex = if (timezone == "GMT") {
        bogotaTimezoneIndex
    } else {
        REGIONS.indexOfFirst { it.contains(timezone) }
    }

    val city = timezoneToCityMap[timezoneIndex] ?: "Bogota"

    println("Timezone: $timezone, Timezone Offset: $timezoneOffset")
    println("Index: $timezoneIndex, City: $city")

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        weatherData?.let { data ->
            val iconUrl = "https://openweathermap.org/img/w/${data.weather[0].icon}.png"
            val temperatureInCelsius = (data.main.temp - 273.15).toInt()

            HomeWeatherSection(
                city = city,
                timezoneOffset = timezoneOffset,
                temperature = "$temperatureInCelsius°C",
                weatherIcon = rememberAsyncImagePainter(model = iconUrl),
                nickname = nickname,
                selectedEmoji = selectedEmoji
            )
        }
    }
}

@Composable
fun HomeWeatherSection(
    city: String,
    timezoneOffset: Int,
    temperature: String,
    weatherIcon: Painter,
    nickname: String,
    selectedEmoji: String,
) {

    val greeting = LocalContext.current.formatDay(timezoneOffset)

    val personalizedGreeting = if (nickname.isEmpty()) {
        "$greeting!"
    } else {
        "$greeting,"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(topBarHeight)
            .background(MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Bottom
    ) {

        GreetingContent(
            personalizedGreeting = personalizedGreeting,
            nickname = nickname,
            selectedEmoji = selectedEmoji,
            weatherIcon = weatherIcon
        )

        Spacer(modifier = Modifier.height(paddingExtraSmall))

        WeatherDetails(
            timezoneOffset = timezoneOffset,
            temperature = temperature,
            city = city
        )
    }
}

@Composable
private fun GreetingContent(
    personalizedGreeting: String,
    nickname: String,
    selectedEmoji: String,
    weatherIcon: Painter,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = paddingSmall,
                start = paddingNormal,
                end = paddingNormal
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        GreetingContent(
            personalizedGreeting = personalizedGreeting,
            nickname = nickname,
            selectedEmoji = selectedEmoji
        )

        Image(
            painter = weatherIcon,
            contentDescription = null,
            modifier = Modifier.size(iconWeatherSize)
        )
    }
}

@Composable
private fun GreetingContent(
    personalizedGreeting: String,
    nickname: String,
    selectedEmoji: String,
) {
    Column {

        WeatherText(text = personalizedGreeting)

        Spacer(modifier = Modifier.height(paddingExtraSmall))

        if (nickname.isNotEmpty()) {
            WeatherText(text = "$selectedEmoji $nickname")
        }
    }
}

@Composable
private fun WeatherDetails(
    timezoneOffset: Int,
    temperature: String,
    city: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = paddingSmall,
                start = paddingNormal,
                end = paddingNormal
            ),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = timezoneOffset.weatherDate(),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.tertiary
        )

        WeatherText(text = "$temperature $city")
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeWeatherSectionPreview() {
    TempusTheme(
        dynamicColor = false
    ) {
        HomeWeatherSection(
            city = "Manila",
            timezoneOffset = -18000,
            temperature = "25°C",
            weatherIcon = painterResource(id = R.drawable.ic_launcher_background),
            nickname = "Repleyva",
            selectedEmoji = "\uD83D\uDE36"
        )
    }
}