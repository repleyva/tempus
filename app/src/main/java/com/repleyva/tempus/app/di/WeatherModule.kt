package com.repleyva.tempus.app.di

import com.repleyva.tempus.app.di.data.LoggerInterceptors
import com.repleyva.tempus.data.remote.api.WeatherApi
import com.repleyva.tempus.data.repository.WeatherRepositoryImpl
import com.repleyva.tempus.domain.constants.Constants.WEATHER_URL
import com.repleyva.tempus.domain.repository.WeatherRepository
import com.repleyva.tempus.domain.use_cases.weather.GetWeather
import com.repleyva.tempus.domain.use_cases.weather.WeathersUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideWeatherApi(
        @LoggerInterceptors loggerInterceptors: Interceptor,
    ): WeatherApi {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(loggerInterceptors)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(WEATHER_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherApi: WeatherApi,
    ): WeatherRepository = WeatherRepositoryImpl(weatherApi)

    @Provides
    @Singleton
    fun provideWeatherUseCases(
        weatherRepository: WeatherRepository,
    ): WeathersUseCases = WeathersUseCases(
        getWeather = GetWeather(weatherRepository),
    )

}