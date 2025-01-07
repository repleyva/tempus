package com.repleyva.tempus.presentation.screens.bookmark

import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.base.State

data class BookmarkState(
    val articles: List<Article> = emptyList(),
) : State
