package com.example.shoppinglist.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PaperBackground(
    modifier: Modifier = Modifier,
    lineSpacing: Dp = 40.dp,
    marginLinePosition: Dp = 60.dp,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val lineSpacingPx = with(density) { lineSpacing.toPx() }
    val marginLinePx = with(density) { marginLinePosition.toPx() }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PaperBackground)
    ) {
        // Draw the paper lines
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawPaperLines(
                size = size,
                lineSpacing = lineSpacingPx,
                marginLinePosition = marginLinePx
            )
        }
        
        // Content goes on top of the paper
        content()
    }
}

private fun DrawScope.drawPaperLines(
    size: androidx.compose.ui.geometry.Size,
    lineSpacing: Float,
    marginLinePosition: Float
) {
    val lineColor = PaperLineColor
    val marginColor = PaperMarginColor
    
    // Draw horizontal dotted lines (notebook lines)
    val numLines = (size.height / lineSpacing).toInt()
    for (i in 1..numLines) {
        val y = i * lineSpacing
        drawLine(
            color = lineColor,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = 1.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
    }
    
    // Draw left margin line (solid pink line)
    drawLine(
        color = marginColor,
        start = Offset(marginLinePosition, 0f),
        end = Offset(marginLinePosition, size.height),
        strokeWidth = 2.dp.toPx()
    )
    
    // Add some subtle paper texture dots (optional aging effect)
    for (i in 0..20) {
        val x = (0..size.width.toInt()).random().toFloat()
        val y = (0..size.height.toInt()).random().toFloat()
        drawCircle(
            color = PaperStainColor,
            radius = (1..3).random().toFloat(),
            center = Offset(x, y)
        )
    }
}

@Composable
fun PaperItem(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp) // Match the line spacing
            .padding(horizontal = 16.dp, vertical = 4.dp),
        content = content
    )
}