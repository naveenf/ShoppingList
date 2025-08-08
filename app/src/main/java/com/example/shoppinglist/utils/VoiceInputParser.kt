package com.example.shoppinglist.utils

import java.util.*

data class ParsedVoiceInput(
    val itemName: String,
    val quantity: Float = 1f,
    val unit: String = "nos",
    val notes: String? = null
)

object VoiceInputParser {
    
    // Common quantity patterns
    private val quantityPatterns = listOf(
        // Numbers followed by units
        Regex("(\\d+(?:\\.\\d+)?)\\s*(kilograms?|kgs?|kg|kilogram)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(grams?|gms?|gm|gram)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(liters?|litres?|l|ltr)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(milliliters?|millilitres?|ml)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(pounds?|lbs?|lb|pound)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(ounces?|ozs?|oz|ounce)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(cups?|cup)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(pieces?|pcs?|pc|piece)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(packs?|pack|packets?|packet)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(bottles?|bottle)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(jars?|jar)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(bags?|bag)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(boxes?|box)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(containers?|container)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(cartons?|carton)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(loaves?|loaf)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(bunches?|bunch)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(heads?|head)", RegexOption.IGNORE_CASE),
        Regex("(\\d+(?:\\.\\d+)?)\\s*(bulbs?|bulb)", RegexOption.IGNORE_CASE)
    )
    
    // Simple number patterns (standalone numbers)
    private val simpleNumberPattern = Regex("(\\d+(?:\\.\\d+)?)(?:\\s+|$)", RegexOption.IGNORE_CASE)
    
    // Unit mapping for normalization
    private val unitMappings = mapOf(
        "kilogram" to "kg", "kilograms" to "kg", "kgs" to "kg",
        "gram" to "gm", "grams" to "gm", "gms" to "gm",
        "liter" to "liters", "litre" to "liters", "litres" to "liters", "l" to "liters", "ltr" to "liters",
        "milliliter" to "ml", "millilitre" to "ml", "millilitres" to "ml",
        "pound" to "lbs", "pounds" to "lbs", "lb" to "lbs",
        "ounce" to "oz", "ounces" to "oz", "ozs" to "oz",
        "cup" to "cups",
        "piece" to "pcs", "pieces" to "pcs", "pc" to "pcs",
        "pack" to "pack", "packet" to "pack", "packets" to "pack",
        "bottle" to "bottle", "bottles" to "bottle",
        "jar" to "jar", "jars" to "jar",
        "bag" to "bag", "bags" to "bag",
        "box" to "box", "boxes" to "box",
        "container" to "container", "containers" to "container",
        "carton" to "cartons", "cartons" to "cartons",
        "loaf" to "loaves", "loaves" to "loaves",
        "bunch" to "bunch", "bunches" to "bunch",
        "head" to "head", "heads" to "head",
        "bulb" to "bulb", "bulbs" to "bulb"
    )
    
    // Common words to remove when parsing
    private val stopWords = setOf(
        "add", "get", "buy", "purchase", "need", "want", "some", "a", "an", "the", "of", "and"
    )
    
    fun parseVoiceInput(input: String): List<ParsedVoiceInput> {
        val cleanInput = input.lowercase(Locale.getDefault()).trim()
        
        // Handle multiple items separated by "and" or commas
        val itemSeparators = Regex("\\s+and\\s+|,\\s*")
        val items = cleanInput.split(itemSeparators)
        
        return items.mapNotNull { item ->
            parseItemString(item.trim())
        }.filter { it.itemName.isNotBlank() }
    }
    
    private fun parseItemString(itemString: String): ParsedVoiceInput? {
        if (itemString.isBlank()) return null
        
        var quantity = 1f
        var unit = "nos"
        var remainingText = itemString
        
        // Try to match quantity and unit patterns
        for (pattern in quantityPatterns) {
            val match = pattern.find(itemString)
            if (match != null) {
                val quantityStr = match.groupValues[1]
                val unitStr = match.groupValues[2]
                
                quantity = quantityStr.toFloatOrNull() ?: 1f
                unit = unitMappings[unitStr.lowercase()] ?: unitStr.lowercase()
                
                // Remove the matched quantity and unit from the text
                remainingText = itemString.replace(match.value, " ").trim()
                break
            }
        }
        
        // If no unit pattern matched, try to find standalone numbers
        if (quantity == 1f && unit == "nos") {
            val numberMatch = simpleNumberPattern.find(itemString)
            if (numberMatch != null) {
                val quantityStr = numberMatch.groupValues[1]
                quantity = quantityStr.toFloatOrNull() ?: 1f
                remainingText = itemString.replace(numberMatch.value, " ").trim()
            }
        }
        
        // Clean up the item name
        val itemName = cleanItemName(remainingText)
        
        if (itemName.isBlank()) return null
        
        return ParsedVoiceInput(
            itemName = TextUtils.formatItemName(itemName),
            quantity = quantity,
            unit = unit
        )
    }
    
    private fun cleanItemName(text: String): String {
        // Remove stop words and clean up
        val words = text.split("\\s+".toRegex())
        val cleanedWords = words.filter { word ->
            word.isNotBlank() && !stopWords.contains(word.lowercase())
        }
        
        return cleanedWords.joinToString(" ").trim()
    }
}