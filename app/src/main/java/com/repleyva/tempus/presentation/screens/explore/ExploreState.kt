package com.repleyva.tempus.presentation.screens.explore

import androidx.paging.PagingData
import com.repleyva.tempus.domain.constants.Constants.CATEGORY_LIST
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.base.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class ExploreState(
    val searchQuery: String = "",
    val isRefreshing: Boolean = false,
    val articles: Flow<PagingData<Article>> = flowOf(PagingData.empty()),
    val searchResults: Flow<PagingData<Article>> = flowOf(PagingData.empty()),
    val selectedArticle: Article? = null,
    val selectedCategory: String = CATEGORY_LIST.first(),
    val categoryArticlesMap: MutableMap<String, PagingData<Article>> = mutableMapOf(),
) : State