package com.repleyva.tempus.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.repleyva.tempus.R


val Poppins = FontFamily(
    fonts = listOf(
        Font(R.font.poppins_regular, FontWeight.Normal),
        Font(R.font.poppins_semibold, FontWeight.SemiBold),
        Font(R.font.poppins_bold, FontWeight.Bold)
    )
)

val Typography = Typography(
    headlineLarge = TextStyle(
        fontSize = 32.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 40.sp,
    ),
    headlineMedium = TextStyle(
        fontSize = 24.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
    ),
    headlineSmall = TextStyle(
        fontSize = 18.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
    ),
    titleLarge = TextStyle(
        fontSize = 24.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
    ),
    titleSmall = TextStyle(
        fontSize = 18.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 24.sp,
    ),
    bodyLarge = TextStyle(
        fontSize = 24.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 30.sp,
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 20.sp,
    ),
    labelLarge = TextStyle(
        fontSize = 20.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 28.sp,
    ),
    labelMedium = TextStyle(
        fontSize = 14.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
    ),

    labelSmall = TextStyle(
        fontSize = 12.sp,
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        lineHeight = 18.sp,
    )
)