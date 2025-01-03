package com.repleyva.tempus.presentation.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.repleyva.tempus.data.remote.api.WeatherApi
import com.repleyva.tempus.domain.constants.CacheConstants.BREAKING_NEWS
import com.repleyva.tempus.domain.constants.CacheConstants.EVERYTHING_NEWS
import com.repleyva.tempus.domain.constants.Constants.COUNTRY_PREFIX_DEFAULT
import com.repleyva.tempus.domain.constants.Constants.FIVE_MINUTES_MILLIS
import com.repleyva.tempus.domain.constants.Constants.SOURCES
import com.repleyva.tempus.domain.constants.Constants.WEATHER_KEY
import com.repleyva.tempus.domain.constants.Constants.WEATHER_URL
import com.repleyva.tempus.domain.extensions.filterArticles
import com.repleyva.tempus.domain.extensions.timezoneToCity
import com.repleyva.tempus.domain.manager.ArticleCacheManager
import com.repleyva.tempus.domain.model.WeatherData
import com.repleyva.tempus.domain.use_cases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

/**
 * Todo: Refactor this
 */

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
    private val articleCacheManager: ArticleCacheManager,
    private val dataStore: DataStore<Preferences>,
) : ViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    private val _weatherData = MutableStateFlow<WeatherData?>(null)
    val weatherData: StateFlow<WeatherData?> = _weatherData

    val breakingNews = channelFlow {
        val cachedData = articleCacheManager.getCachedArticles(BREAKING_NEWS)
        if (cachedData != null) {
            send(cachedData.filterArticles())
        } else {
            newsUseCases.getBreakingNews(COUNTRY_PREFIX_DEFAULT)
                .cachedIn(viewModelScope)
                .collectLatest { articles ->
                    val validArticles = articles.filterArticles()
                    articleCacheManager.cacheArticles(BREAKING_NEWS, validArticles)
                    send(validArticles)
                }
        }
    }

    val everythingNews = channelFlow {
        val cachedData = articleCacheManager.getCachedArticles(EVERYTHING_NEWS)
        if (cachedData != null) {
            send(cachedData.filterArticles())
        } else {
            newsUseCases.getNewsEverything(SOURCES)
                .cachedIn(viewModelScope)
                .collectLatest { articles ->
                    val validArticles = articles.filterArticles()
                    articleCacheManager.cacheArticles(EVERYTHING_NEWS, validArticles)
                    send(validArticles)
                }
        }
    }

    init {
        fetchWeatherData()
        startAutoRefresh()
    }

    /**
     * Todo: Refactor to use case
     */

    fun fetchWeatherData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val city = dataStore.timezoneToCity()
                println("Fetching weather data for city: $city")
                val apiKey = WEATHER_KEY
                val weatherApi: WeatherApi = Retrofit.Builder()
                    .baseUrl(WEATHER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherApi::class.java)

                val fetchedWeatherData = weatherApi.getWeather(city, apiKey)
                _weatherData.value = fetchedWeatherData
            } catch (e: HttpException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (isActive) {
                delay(FIVE_MINUTES_MILLIS)
                refreshBreakingNews()
                refreshEverythingNews()
            }
        }
    }

    fun refreshArticles() {
        _isRefreshing.value = true
        viewModelScope.launch {
            refreshBreakingNews()
            refreshEverythingNews()
            _isRefreshing.value = false
        }
    }

    private fun refreshBreakingNews() {
        viewModelScope.launch {
            val cachedData = articleCacheManager.getCachedArticles(BREAKING_NEWS)
            if (cachedData == null) {
                newsUseCases.getBreakingNews(COUNTRY_PREFIX_DEFAULT)
                    .cachedIn(viewModelScope)
                    .collectLatest { articles ->
                        val validArticles = articles.filterArticles()
                        articleCacheManager.cacheArticles(BREAKING_NEWS, validArticles)
                    }
            }
        }
    }

    private fun refreshEverythingNews() {
        viewModelScope.launch {
            val cachedData = articleCacheManager.getCachedArticles(EVERYTHING_NEWS)
            if (cachedData == null) {
                newsUseCases.getNewsEverything(SOURCES)
                    .cachedIn(viewModelScope)
                    .collectLatest { articles ->
                        val validArticles = articles.filterArticles()
                        articleCacheManager.cacheArticles(EVERYTHING_NEWS, validArticles)
                    }
            }
        }
    }

    fun updateCity(newTimezone: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val city = dataStore.timezoneToCity()
                println("Updating weather data for new city: $city")
                fetchWeatherData()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}