package com.repleyva.tempus.presentation.screens.bookmark

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.common.ScreenTitleTextLarge
import com.repleyva.tempus.presentation.screens.bookmark.components.BookmarkList
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSemiMedium
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSmall
import com.repleyva.tempus.presentation.theme.TempusTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    state: BookmarkState,
    navigateToDetails: (Article) -> Unit,
) {
    Column(
        modifier = Modifier.testTag("BookmarkScreen")
    ) {
        TopAppBar(
            modifier = Modifier.padding(paddingSemiMedium),
            title = { ScreenTitleTextLarge(textResId = R.string.bookmark) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
        )
        BookmarkList(
            modifier = Modifier.padding(horizontal = paddingSmall),
            articles = state.articles,
            onClick = { navigateToDetails(it) }
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewSearchScreen() {
    val dummyState = BookmarkState()
    TempusTheme(
        dynamicColor = false
    ) {
        BookmarkScreen(
            state = dummyState,
            navigateToDetails = { /* Handle navigation */ }
        )
    }
}