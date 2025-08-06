package com.example.shoppinglist.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Modern Typography (Default)
val Typography = Typography(
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    )
)

// Paper/Handwritten Typography - Using Cursive font
val PaperTypography = Typography(
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Medium,
        fontSize = 26.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        letterSpacing = 0.3.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.2.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    )
)

// Botanic Typography - Natural, organic feel with rounded fonts
val BotanicTypography = Typography(
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Serif, // Changed to Serif for more distinct difference
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp, // Increased size
        letterSpacing = 1.2.sp // Much wider spacing for organic, airy feel
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        letterSpacing = 0.8.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp, // Increased
        letterSpacing = 0.6.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp, // Increased
        letterSpacing = 0.5.sp, // More spacing for natural readability
        lineHeight = 26.sp // More breathing room between lines
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp, // Increased
        letterSpacing = 0.4.sp,
        lineHeight = 24.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp, // Increased
        letterSpacing = 0.7.sp // More spacing for labels/buttons
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp, // Increased
        letterSpacing = 0.6.sp
    )
)
