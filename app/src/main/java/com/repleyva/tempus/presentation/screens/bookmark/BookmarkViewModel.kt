package com.repleyva.tempus.presentation.screens.bookmark

import androidx.lifecycle.viewModelScope
import com.repleyva.tempus.domain.use_cases.news.NewsUseCases
import com.repleyva.tempus.presentation.base.SimpleMVIBaseViewModel
import com.repleyva.tempus.presentation.screens.bookmark.BookmarkEvent.GetArticles
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
) : SimpleMVIBaseViewModel<BookmarkState, BookmarkEvent>() {

    override fun initState() = BookmarkState()

    override fun eventHandler(event: BookmarkEvent) {
        when (event) {
            GetArticles -> getArticles()
        }
    }

    init {
        getArticles()
    }

    private fun getArticles() {
        newsUseCases.getArticles().onEach {
            updateUi { copy(articles = it) }
        }.launchIn(viewModelScope)
    }
}