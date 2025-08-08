package com.example.shoppinglist.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.R
import com.example.shoppinglist.utils.VoiceInputState
import com.example.shoppinglist.utils.VoiceRecognitionManager

@Composable
fun VoiceInputButton(
    voiceInputState: VoiceInputState,
    onStartListening: () -> Unit,
    onStopListening: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val context = LocalContext.current
    
    // Animation for pulsing effect when listening
    val infiniteTransition = rememberInfiniteTransition(label = "voice_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )
    
    // Rotation animation for processing state
    val processingRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "processing_rotation"
    )
    
    val isListening = voiceInputState == VoiceInputState.LISTENING
    val isProcessing = voiceInputState == VoiceInputState.PROCESSING
    val isError = voiceInputState == VoiceInputState.ERROR
    
    val buttonColors = IconButtonDefaults.iconButtonColors(
        containerColor = when {
            isError -> MaterialTheme.colorScheme.error
            isListening || isProcessing -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.surfaceVariant
        },
        contentColor = when {
            isError -> MaterialTheme.colorScheme.onError
            isListening || isProcessing -> MaterialTheme.colorScheme.onPrimary
            else -> MaterialTheme.colorScheme.onSurfaceVariant
        }
    )
    
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        FilledIconButton(
            onClick = {
                when (voiceInputState) {
                    VoiceInputState.IDLE, VoiceInputState.ERROR -> onStartListening()
                    VoiceInputState.LISTENING -> onStopListening()
                    VoiceInputState.PROCESSING -> { /* Do nothing while processing */ }
                }
            },
            enabled = enabled && voiceInputState != VoiceInputState.PROCESSING,
            colors = buttonColors,
            modifier = Modifier
                .size(48.dp)
                .graphicsLayer {
                    scaleX = if (isListening) pulseScale else 1f
                    scaleY = if (isListening) pulseScale else 1f
                    rotationZ = if (isProcessing) processingRotation else 0f
                }
        ) {
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Voice input error - tap to retry",
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.mic),
                    contentDescription = when (voiceInputState) {
                        VoiceInputState.IDLE -> "Start voice input"
                        VoiceInputState.LISTENING -> "Stop voice input"
                        VoiceInputState.PROCESSING -> "Processing voice input"
                        else -> "Voice input"
                    },
                    modifier = Modifier.size(24.dp),
                    tint = LocalContentColor.current
                )
            }
        }
        
        // Show a subtle indicator for listening state
        if (isListening) {
            Surface(
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ) {}
        }
    }
}