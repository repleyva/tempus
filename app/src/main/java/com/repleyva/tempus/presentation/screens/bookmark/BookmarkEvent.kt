package com.repleyva.tempus.presentation.screens.bookmark

import com.repleyva.tempus.presentation.base.Event

sealed interface BookmarkEvent : Event {

    data object GetArticles : BookmarkEvent

}