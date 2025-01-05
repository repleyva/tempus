package com.repleyva.tempus.presentation.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.domain.use_cases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Todo: refactor
 */

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases,
) : ViewModel() {

    var sideEffect by mutableStateOf<String?>(null)

    fun eventHandler(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteArticle -> upsertDeleteArticle(event)
            is DetailsEvent.RemoveSideEffect -> sideEffect = null
        }
    }

    private fun upsertDeleteArticle(event: DetailsEvent.UpsertDeleteArticle) {
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
        sideEffect = "Article Saved"
    }

    private suspend fun deleteArticle(article: Article) {
        newsUseCases.deleteArticle(article = article)
        sideEffect = "Article Deleted"
    }
}