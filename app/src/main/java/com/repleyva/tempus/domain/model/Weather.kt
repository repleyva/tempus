package com.repleyva.tempus.domain.model

data class WeatherData(
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val timezone: Int,
)

data class Main(
    val temp: Double,
)

data class Weather(
    val icon: String,
)