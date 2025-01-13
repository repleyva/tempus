package com.repleyva.tempus.data.repository

import com.repleyva.tempus.data.remote.api.WeatherApi
import com.repleyva.tempus.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
) : WeatherRepository {

    override suspend fun getWeather(city: String) = weatherApi.getWeather(city)

}