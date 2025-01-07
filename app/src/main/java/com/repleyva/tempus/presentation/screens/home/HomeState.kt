package com.repleyva.tempus.presentation.screens.home

import androidx.paging.PagingData
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.base.State
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val articles: Flow<PagingData<Article>>? = null,
    var isLoading: Boolean = false,
    val breakingNews: List<Article> = emptyList(),
    val everythingNews: List<Article> = emptyList(),
    val error: String? = null,
) : State