package com.repleyva.tempus.presentation.screens.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repleyva.tempus.domain.use_cases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Todo: refactor
 */

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(BookmarkState())
    val state: State<BookmarkState> = _state

    init {
        getArticles()
    }

    fun getArticles() {
        newsUseCases.getArticles().onEach {
            _state.value = _state.value.copy(articles = it)
        }.launchIn(viewModelScope)
    }
}