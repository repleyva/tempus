package com.repleyva.tempus.presentation.screens.home.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.repleyva.tempus.presentation.extensions.breakingNewsEffect
import com.repleyva.tempus.presentation.theme.Dimensions.homeBreakingNewsHeight
import com.repleyva.tempus.presentation.theme.Dimensions.homeBreakingNewsWidth
import com.repleyva.tempus.presentation.theme.Dimensions.paddingExtraSmall
import com.repleyva.tempus.presentation.theme.Dimensions.paddingMedium
import com.repleyva.tempus.presentation.theme.TempusTheme

@Composable
fun BreakingNewsCardEffect(
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .width(homeBreakingNewsWidth)
            .height(homeBreakingNewsHeight),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Box(
            modifier = Modifier
                .size(homeBreakingNewsWidth)
                .clip(MaterialTheme.shapes.medium)
                .breakingNewsEffect()
        )

        Spacer(modifier = Modifier.height(paddingExtraSmall))

        Box(
            modifier = Modifier
                .height(paddingMedium)
                .fillMaxWidth(0.9f)
                .breakingNewsEffect()
        )

        Spacer(modifier = Modifier.height(paddingExtraSmall))
        
        Box(
            modifier = Modifier
                .height(paddingMedium)
                .fillMaxWidth(0.9f)
                .breakingNewsEffect()
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun BreakingNewsCardEffectPreview() {
    TempusTheme {
        BreakingNewsCardEffect(modifier = Modifier)
    }
}