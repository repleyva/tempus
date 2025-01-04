package com.repleyva.tempus.presentation.screens.explore.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.repleyva.tempus.presentation.extensions.shimmerEffect
import com.repleyva.tempus.presentation.theme.Dimensions.articleCardSizeHeight
import com.repleyva.tempus.presentation.theme.Dimensions.articleCardSizeWidth
import com.repleyva.tempus.presentation.theme.Dimensions.paddingExtraSmall
import com.repleyva.tempus.presentation.theme.Dimensions.paddingMedium
import com.repleyva.tempus.presentation.theme.Dimensions.paddingSmall
import com.repleyva.tempus.presentation.theme.TempusTheme

@Composable
fun ExploreCardEffect(
    modifier: Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = paddingMedium),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = paddingSmall),
            verticalArrangement = Arrangement.spacedBy(paddingExtraSmall)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .padding(end = paddingMedium)
                    .shimmerEffect()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(15.dp)
                        .padding(end = paddingMedium)
                        .shimmerEffect()
                )
            }
        }

        Box(
            modifier = Modifier
                .width(articleCardSizeWidth)
                .height(articleCardSizeHeight)
                .clip(MaterialTheme.shapes.medium)
                .shimmerEffect()
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ArticleCardEffectPreview() {
    TempusTheme {
        ExploreCardEffect(modifier = Modifier)
    }
}