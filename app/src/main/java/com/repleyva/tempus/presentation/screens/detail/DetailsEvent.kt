package com.repleyva.tempus.presentation.screens.detail

import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.base.Event

sealed interface DetailsEvent : Event {

    data class UpsertDeleteArticle(val article: Article) : DetailsEvent

    data object RemoveSideEffect : DetailsEvent

}