package com.example.shoppinglist.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.shoppinglist.R

@Composable
fun BotanicBackground(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BotanicBackground)
    ) {
        // Beautiful botanical background image
        Image(
            painter = painterResource(id = R.drawable.botanic_background),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.15f), // Subtle opacity so text remains readable
            contentScale = ContentScale.Crop // Fills screen, maintains aspect ratio
        )
        
        // Optional subtle gradient overlay for enhanced readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            BotanicBackground.copy(alpha = 0.1f)
                        )
                    )
                )
        )
        
        content()
    }
}