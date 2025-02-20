package com.repleyva.tempus.data.remote.api

import com.repleyva.tempus.domain.constants.Constants.WEATHER_KEY
import com.repleyva.tempus.domain.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String = WEATHER_KEY,
    ): WeatherData
}