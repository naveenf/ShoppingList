package com.example.shoppinglist.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class VoiceInputState {
    IDLE, LISTENING, PROCESSING, ERROR
}

data class VoiceInputResult(
    val success: Boolean,
    val text: String = "",
    val error: String = ""
)

class VoiceRecognitionManager(private val context: Context) {
    
    private val _voiceInputState = MutableStateFlow(VoiceInputState.IDLE)
    val voiceInputState: StateFlow<VoiceInputState> = _voiceInputState.asStateFlow()
    
    private var speechRecognizer: SpeechRecognizer? = null
    private var onResultCallback: ((VoiceInputResult) -> Unit)? = null
    
    fun startListening(onResult: (VoiceInputResult) -> Unit) {
        if (!SpeechRecognizer.isRecognitionAvailable(context)) {
            onResult(VoiceInputResult(false, error = "Speech recognition not available"))
            return
        }
        
        onResultCallback = onResult
        _voiceInputState.value = VoiceInputState.LISTENING
        
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context).apply {
            setRecognitionListener(createRecognitionListener())
        }
        
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
        }
        
        speechRecognizer?.startListening(intent)
    }
    
    fun stopListening() {
        speechRecognizer?.stopListening()
        speechRecognizer?.destroy()
        speechRecognizer = null
        _voiceInputState.value = VoiceInputState.IDLE
    }
    
    private fun createRecognitionListener() = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {}
        
        override fun onBeginningOfSpeech() {
            _voiceInputState.value = VoiceInputState.LISTENING
        }
        
        override fun onRmsChanged(rmsdB: Float) {}
        
        override fun onBufferReceived(buffer: ByteArray?) {}
        
        override fun onEndOfSpeech() {
            _voiceInputState.value = VoiceInputState.PROCESSING
        }
        
        override fun onError(error: Int) {
            _voiceInputState.value = VoiceInputState.ERROR
            val errorMessage = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                SpeechRecognizer.ERROR_CLIENT -> "Client side error"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
                SpeechRecognizer.ERROR_NETWORK -> "Network error"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
                SpeechRecognizer.ERROR_NO_MATCH -> "No speech input detected"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Recognition service busy"
                SpeechRecognizer.ERROR_SERVER -> "Server error"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
                else -> "Unknown error occurred"
            }
            
            onResultCallback?.invoke(VoiceInputResult(false, error = errorMessage))
            cleanup()
        }
        
        override fun onResults(results: Bundle?) {
            _voiceInputState.value = VoiceInputState.IDLE
            
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            val recognizedText = matches?.firstOrNull() ?: ""
            
            if (recognizedText.isNotBlank()) {
                onResultCallback?.invoke(VoiceInputResult(true, text = recognizedText))
            } else {
                onResultCallback?.invoke(VoiceInputResult(false, error = "No speech detected"))
            }
            cleanup()
        }
        
        override fun onPartialResults(partialResults: Bundle?) {}
        
        override fun onEvent(eventType: Int, params: Bundle?) {}
    }
    
    private fun cleanup() {
        speechRecognizer?.destroy()
        speechRecognizer = null
        onResultCallback = null
        _voiceInputState.value = VoiceInputState.IDLE
    }
    
    fun destroy() {
        cleanup()
    }
}