package com.repleyva.tempus.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.repleyva.tempus.R
import com.repleyva.tempus.domain.model.Article
import com.repleyva.tempus.presentation.extensions.formatDate
import com.repleyva.tempus.presentation.theme.Dimensions.iconSmall
import com.repleyva.tempus.presentation.theme.Dimensions.paddingExtraSmall

@Composable
fun ArticleDetails(article: Article) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(paddingExtraSmall)
    ) {

        CardSourceTextSmall(
            modifier = Modifier.padding(end = paddingExtraSmall),
            text = article.source.name,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold)
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_time),
            contentDescription = null,
            modifier = Modifier.size(iconSmall),
            tint = MaterialTheme.colorScheme.secondary
        )

        CardSourceTextSmall(
            text = article.publishedAt.orEmpty().formatDate(),
            style = MaterialTheme.typography.labelSmall
        )
    }
}