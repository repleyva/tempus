package com.repleyva.tempus.presentation.screens.home

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.repleyva.tempus.domain.common.DataState
import com.repleyva.tempus.domain.constants.CacheConstants.BREAKING_NEWS
import com.repleyva.tempus.domain.constants.CacheConstants.EVERYTHING_NEWS
import com.repleyva.tempus.domain.constants.Constants.COUNTRY_PREFIX_DEFAULT
import com.repleyva.tempus.domain.constants.Constants.FIVE_MINUTES_MILLIS
import com.repleyva.tempus.domain.constants.Constants.SOURCES
import com.repleyva.tempus.domain.errors.orGenericError
import com.repleyva.tempus.domain.extensions.filterArticles
import com.repleyva.tempus.domain.extensions.timezoneToCity
import com.repleyva.tempus.domain.manager.ArticleCacheManager
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.domain.use_cases.news.NewsUseCases
import com.repleyva.tempus.domain.use_cases.weather.GetWeather
import com.repleyva.tempus.presentation.base.SimpleMVIBaseViewModel
import com.repleyva.tempus.presentation.extensions.timezoneToCity
import com.repleyva.tempus.presentation.screens.home.HomeEvent.FetchWeatherData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
    private val articleCacheManager: ArticleCacheManager,
    private val getWeather: GetWeather,
    private val dataStore: DataStore<Preferences>,
) : SimpleMVIBaseViewModel<HomeState, HomeEvent>() {

    override fun initState(): HomeState = HomeState(
        breakingNews = channelFlow { getBreakingNews() },
        everythingNews = channelFlow { getEverythingNews() }
    )

    override fun eventHandler(event: HomeEvent) {
        when (event) {
            is FetchWeatherData -> fetchWeatherData(event.city)
        }
    }

    init {
        startAutoRefresh()
    }

    private suspend fun ProducerScope<PagingData<Article>>.getBreakingNews() {
        val cachedData = articleCacheManager.getCachedArticles(BREAKING_NEWS)
        if (cachedData != null) {
            send(cachedData.filterArticles())
        } else {
            getBreakingNewsUseCase { send(it) }
        }
    }

    private suspend fun getBreakingNewsUseCase(getValidArticles: suspend (PagingData<Article>) -> Unit = {}) {
        newsUseCases.getBreakingNews(COUNTRY_PREFIX_DEFAULT)
            .cachedIn(viewModelScope)
            .collectLatest { articles ->
                val validArticles = articles.filterArticles()
                articleCacheManager.cacheArticles(BREAKING_NEWS, validArticles)
                getValidArticles(validArticles)
            }
    }

    private suspend fun ProducerScope<PagingData<Article>>.getEverythingNews() {
        val cachedData = articleCacheManager.getCachedArticles(EVERYTHING_NEWS)
        if (cachedData != null) {
            send(cachedData.filterArticles())
        } else {
            getEverythingUseCase { send(it) }
        }
    }

    private suspend fun getEverythingUseCase(getValidArticles: suspend (PagingData<Article>) -> Unit = {}) {
        newsUseCases.getNewsEverything(SOURCES)
            .cachedIn(viewModelScope)
            .collectLatest { articles ->
                val validArticles = articles.filterArticles()
                articleCacheManager.cacheArticles(EVERYTHING_NEWS, validArticles)
                getValidArticles(validArticles)
            }
    }

    private fun fetchWeatherData(city: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val cityCached = if (city.isNullOrEmpty()) dataStore.timezoneToCity() else city.timezoneToCity()
            getWeather(cityCached).collectCommon(viewModelScope) { dataState ->
                updateUi {
                    when (dataState) {
                        is DataState.Data -> copy(weatherData = dataState.data, isWeatherLoading = false)
                        is DataState.Loading -> copy(isWeatherLoading = dataState.isLoading)
                        is DataState.Error -> copy(weatherError = dataState.error.orGenericError(), isWeatherLoading = false)
                    }
                }
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

    private fun refreshBreakingNews() {
        viewModelScope.launch {
            val cachedData = articleCacheManager.getCachedArticles(BREAKING_NEWS)
            if (cachedData == null) getBreakingNewsUseCase()
        }
    }

    private fun refreshEverythingNews() {
        viewModelScope.launch {
            val cachedData = articleCacheManager.getCachedArticles(EVERYTHING_NEWS)
            if (cachedData == null) getEverythingUseCase()
        }
    }
}