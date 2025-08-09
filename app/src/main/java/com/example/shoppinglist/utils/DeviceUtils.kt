package com.example.shoppinglist.utils

import android.content.Context
import java.util.*

object DeviceUtils {
    
    private const val DEVICE_ID_PREFS = "device_prefs"
    private const val DEVICE_ID_KEY = "device_id"
    
    /**
     * Gets a persistent anonymous device ID for sync purposes.
     * Creates a new UUID if one doesn't exist.
     */
    fun getDeviceId(context: Context): String {
        val prefs = context.getSharedPreferences(DEVICE_ID_PREFS, Context.MODE_PRIVATE)
        
        return prefs.getString(DEVICE_ID_KEY, null) ?: run {
            val newDeviceId = "device_${UUID.randomUUID().toString().replace("-", "").take(12)}"
            prefs.edit().putString(DEVICE_ID_KEY, newDeviceId).apply()
            newDeviceId
        }
    }
    
    /**
     * Generates a family share code in format like "GARDEN123" or "SUNNY87"
     * - 6 characters total
     * - Letters + numbers for memorability
     */
    fun generateFamilyShareCode(): String {
        val words = listOf(
            "GARDEN", "SUNNY", "OCEAN", "FOREST", "HAPPY", "BRIGHT", "CLOUD", "RIVER",
            "PEACE", "SMILE", "HEART", "DREAM", "MAGIC", "SWEET", "LIGHT", "FRESH"
        )
        val randomWord = words.random()
        val randomNumbers = (10..99).random()
        return "$randomWord$randomNumbers"
    }
    
    /**
     * Validates a family share code format
     */
    fun isValidFamilyCode(code: String): Boolean {
        return code.matches(Regex("^[A-Z]{4,8}[0-9]{2}$"))
    }
}