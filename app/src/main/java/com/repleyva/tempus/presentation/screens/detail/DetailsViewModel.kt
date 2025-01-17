package com.repleyva.tempus.presentation.screens.detail

import androidx.lifecycle.viewModelScope
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.domain.use_cases.news.NewsUseCases
import com.repleyva.tempus.presentation.base.SimpleMVIBaseViewModel
import com.repleyva.tempus.presentation.screens.detail.DetailsEvent.RemoveSideEffect
import com.repleyva.tempus.presentation.screens.detail.DetailsEvent.UpsertDeleteArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
) : SimpleMVIBaseViewModel<DetailsState, DetailsEvent>() {

    override fun initState(): DetailsState = DetailsState()

    override fun eventHandler(event: DetailsEvent) {
        when (event) {
            is UpsertDeleteArticle -> upsertDeleteArticle(event)
            is RemoveSideEffect -> updateUi { copy(sideEffect = null) }
        }
    }

    private fun upsertDeleteArticle(event: UpsertDeleteArticle) {
        viewModelScope.launch {
            val article = newsUseCases.getArticle(event.article.url)
            if (article == null) {
                upsertArticle(event.article)
            } else {
                deleteArticle(event.article)
            }
        }
    }

    private suspend fun upsertArticle(article: Article) {
        newsUseCases.upsertArticle(article = article)
        updateUi { copy(sideEffect = "Article Saved") }
    }

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article = article)
        updateUi { copy(sideEffect = "Article Deleted") }
    }
}