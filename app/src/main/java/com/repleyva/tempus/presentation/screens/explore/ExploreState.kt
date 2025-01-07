package com.repleyva.tempus.presentation.screens.explore

import androidx.paging.PagingData
import com.repleyva.tempus.domain.constants.Constants
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.base.State
import kotlinx.coroutines.flow.Flow

data class ExploreState(
    val searchQuery: String = "",
    val isSearchBarVisible: Boolean = false,
    val articles: Flow<PagingData<Article>>? = null,
    val selectedArticle: Article? = null,
    val selectedCategory: String = Constants.CATEGORY_LIST.first(),
    val isLoading: Boolean = false,
    val error: String? = null,
) : State