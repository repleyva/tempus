package com.repleyva.tempus.presentation.screens.explore

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.repleyva.tempus.domain.constants.Constants.CATEGORY_LIST
import com.repleyva.tempus.domain.constants.Constants.FIVE_MINUTES_MILLIS
import com.repleyva.tempus.domain.constants.Constants.SOURCES
import com.repleyva.tempus.domain.extensions.filterArticles
import com.repleyva.tempus.domain.manager.ArticleCacheManager
import com.repleyva.tempus.domain.use_cases.news.NewsUseCases
import com.repleyva.tempus.presentation.base.SimpleMVIBaseViewModel
import com.repleyva.tempus.presentation.screens.explore.ExploreEvent.OnCategorySelected
import com.repleyva.tempus.presentation.screens.explore.ExploreEvent.OnRefreshArticles
import com.repleyva.tempus.presentation.screens.explore.ExploreEvent.OnSearch
import com.repleyva.tempus.presentation.screens.explore.ExploreEvent.OnSearchQueryChanged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
    private val articleCacheManager: ArticleCacheManager,
) : SimpleMVIBaseViewModel<ExploreState, ExploreEvent>() {

    override fun initState(): ExploreState = ExploreState()

    override fun eventHandler(event: ExploreEvent) {
        when (event) {
            is OnSearchQueryChanged -> onSearchQueryChanged(event)
            is OnSearch -> searchNews()
            is OnCategorySelected -> onCategorySelected(event)
            is OnRefreshArticles -> refreshArticles()
        }
    }

    init {
        fetchInitialDataForAllCategories()
        startAutoRefresh()
    }

    private fun fetchInitialDataForAllCategories() {
        CATEGORY_LIST.forEach { category ->
            getCategorizedNews(category)
        }
    }

    private fun getCategorizedNews(category: String) {
        viewModelScope.launch {
            val cachedArticles = uiState.value.categoryArticlesMap[category]
            if (cachedArticles != null) {
                if (category == uiState.value.selectedCategory) {
                    updateUi { copy(articles = flowOf(cachedArticles.filterArticles())) }
                }
            } else {
                fetchCategorizedNews(category)
            }
        }
    }

    private suspend fun fetchCategorizedNews(category: String) {
        newsUseCases.getCategorizedNews(category)
            .cachedIn(viewModelScope)
            .collectLatest { articles ->
                val validArticles = articles.filterArticles()
                articleCacheManager.cacheArticles(category, validArticles)
                uiState.value.categoryArticlesMap[category] = validArticles
                if (category == uiState.value.selectedCategory) {
                    updateUi { copy(articles = flowOf(validArticles)) }
                }
            }
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (isActive) {
                delay(FIVE_MINUTES_MILLIS)
                refreshAllCategories()
            }
        }
    }

    private fun refreshAllCategories() {
        CATEGORY_LIST.forEach { category ->
            refreshCategorizedNews(category)
        }
    }

    private fun refreshCategorizedNews(category: String) {
        viewModelScope.launch {
            fetchCategorizedNews(category)
        }
    }

    private fun onSearchQueryChanged(event: OnSearchQueryChanged) {
        updateUi { copy(searchQuery = event.searchQuery) }
    }

    private fun searchNews() {
        viewModelScope.launch {
            newsUseCases.searchNews(
                searchQuery = uiState.value.searchQuery,
                sources = SOURCES
            ).cachedIn(viewModelScope)
                .collectLatest { articles ->
                    updateUi { copy(searchResults = flowOf(articles.filterArticles())) }
                }
        }
    }

    private fun onCategorySelected(event: OnCategorySelected) {
        updateUi {
            copy(
                selectedCategory = event.category,
                articles = uiState.value.categoryArticlesMap[event.category]?.let { flowOf(it) } ?: flowOf()
            )
        }
        getCategorizedNews(event.category)
    }

    private fun refreshArticles() {
        updateUi { copy(isRefreshing = true) }
        viewModelScope.launch {
            refreshAllCategories()
            updateUi { copy(isRefreshing = false) }
        }
    }

}
