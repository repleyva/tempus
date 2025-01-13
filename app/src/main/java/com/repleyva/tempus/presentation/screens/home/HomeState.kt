package com.repleyva.tempus.presentation.screens.home

import androidx.paging.PagingData
import com.repleyva.tempus.domain.errors.ErrorDomain
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.domain.model.WeatherData
import com.repleyva.tempus.presentation.base.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class HomeState(
    val breakingNews: Flow<PagingData<Article>> = emptyFlow(),
    val everythingNews: Flow<PagingData<Article>> = emptyFlow(),
    val weatherData: WeatherData? = null,
    val isWeatherLoading: Boolean = false,
    val weatherError: ErrorDomain? = null,
) : State