package com.repleyva.tempus.domain.repository

import com.repleyva.tempus.domain.model.WeatherData

interface WeatherRepository {

    suspend fun getWeather(city: String): WeatherData
}