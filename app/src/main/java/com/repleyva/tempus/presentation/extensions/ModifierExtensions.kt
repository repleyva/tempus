package com.repleyva.tempus.presentation.extensions

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.breakingNewsEffect() = composed {
    val transition = rememberInfiniteTransition(label = "")
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    ).value
    background(color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha))
}

