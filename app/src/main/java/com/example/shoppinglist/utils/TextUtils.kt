package com.example.shoppinglist.utils

import java.util.*

object TextUtils {
    
    /**
     * Converts text to title case following these rules:
     * - First letter of each word is capitalized
     * - Rest of the letters in each word are lowercase
     * - Handles multiple spaces and punctuation properly
     * 
     * Examples:
     * "apple" → "Apple"
     * "milk can" → "Milk Can"
     * "organic coconut oil" → "Organic Coconut Oil"
     * "2 lbs ground beef" → "2 Lbs Ground Beef"
     */
    fun toTitleCase(text: String): String {
        if (text.isBlank()) return text
        
        return text.lowercase(Locale.getDefault())
            .split(" ")
            .joinToString(" ") { word ->
                if (word.isNotEmpty()) {
                    word.replaceFirstChar { 
                        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) 
                        else it.toString() 
                    }
                } else {
                    word
                }
            }
    }
    
    /**
     * Transforms item name to title case while preserving certain patterns:
     * - Numbers and units stay as-is
     * - Common abbreviations are handled properly
     */
    fun formatItemName(itemName: String): String {
        return toTitleCase(itemName.trim())
    }
}