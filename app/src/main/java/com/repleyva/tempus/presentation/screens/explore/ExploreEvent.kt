package com.repleyva.tempus.presentation.screens.explore

import com.repleyva.tempus.presentation.base.Event

sealed interface ExploreEvent : Event {

    data class OnSearchQueryChanged(val searchQuery: String) : ExploreEvent

    data object OnSearch : ExploreEvent

    data class OnCategorySelected(val category: String) : ExploreEvent

    data object OnRefreshArticles : ExploreEvent
}