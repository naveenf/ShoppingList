package com.example.shoppinglist.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Enhanced handwriting font family using system cursive with better styling
val HandwritingFontFamily = FontFamily.Cursive

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

// Paper/Handwritten Typography - Enhanced handwriting style
val PaperTypography = Typography(
    headlineMedium = TextStyle(
        fontFamily = HandwritingFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        letterSpacing = 1.2.sp // Wider spacing for handwriting
    ),
    titleLarge = TextStyle(
        fontFamily = HandwritingFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        letterSpacing = 1.0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = HandwritingFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        letterSpacing = 0.8.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = HandwritingFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.6.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = HandwritingFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        letterSpacing = 0.5.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = HandwritingFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        letterSpacing = 0.4.sp,
        lineHeight = 18.sp
    ),
    labelLarge = TextStyle(
        fontFamily = HandwritingFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.8.sp
    ),
    labelMedium = TextStyle(
        fontFamily = HandwritingFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 0.6.sp
    ),
    labelSmall = TextStyle(
        fontFamily = HandwritingFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        letterSpacing = 0.5.sp
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
