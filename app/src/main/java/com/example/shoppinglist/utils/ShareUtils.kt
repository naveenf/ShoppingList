package com.example.shoppinglist.utils

import android.content.Context
import android.content.Intent
import com.example.shoppinglist.data.database.entities.ShoppingItem

object ShareUtils {
    
    fun shareText(context: Context, text: String) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        
        val chooserIntent = Intent.createChooser(shareIntent, "Share")
        context.startActivity(chooserIntent)
    }
    
    fun shareShoppingList(
        context: Context, 
        listName: String, 
        items: List<ShoppingItem>
    ) {
        val shareText = buildShoppingListText(listName, items)
        
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_SUBJECT, "Shopping List: $listName")
        }
        
        val chooserIntent = Intent.createChooser(shareIntent, "Share Shopping List")
        context.startActivity(chooserIntent)
    }
    
    fun shareToWhatsApp(
        context: Context, 
        listName: String, 
        items: List<ShoppingItem>
    ) {
        val shareText = buildShoppingListText(listName, items)
        
        val whatsappIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            setPackage("com.whatsapp")
        }
        
        try {
            context.startActivity(whatsappIntent)
        } catch (e: Exception) {
            // WhatsApp not installed, fall back to general share
            shareShoppingList(context, listName, items)
        }
    }
    
    fun shareViaSMS(
        context: Context, 
        listName: String, 
        items: List<ShoppingItem>
    ) {
        val shareText = buildShoppingListText(listName, items)
        
        val smsIntent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
            setPackage("com.google.android.apps.messaging") // Default to Google Messages
        }
        
        try {
            context.startActivity(smsIntent)
        } catch (e: Exception) {
            // Fall back to general SMS intent
            val fallbackIntent = Intent(Intent.ACTION_VIEW).apply {
                data = android.net.Uri.parse("sms:")
                putExtra("sms_body", shareText)
            }
            context.startActivity(fallbackIntent)
        }
    }
    
    private fun buildShoppingListText(listName: String, items: List<ShoppingItem>): String {
        val uncheckedItems = items.filter { !it.isChecked }
        val checkedItems = items.filter { it.isChecked }
        
        // Find the longest item name across ALL items (both checked and unchecked) for perfect global alignment
        val maxItemNameLength = items.maxOfOrNull { it.name.length } ?: 0
        
        val builder = StringBuilder().apply {
            // Clean header
            appendLine("🛒 $listName")
            appendLine()
            
            if (uncheckedItems.isNotEmpty()) {
                appendLine("NEED TO BUY:")
                appendLine()
                
                uncheckedItems.groupBy { it.category }.forEach { (category, categoryItems) ->
                    appendLine("$category")
                    appendLine("─".repeat(15)) // Fixed length dashes for consistency
                    
                    categoryItems.forEachIndexed { index, item ->
                        val quantityUnit = formatQuantityUnitAligned(item.quantity, item.unit)
                        val number = "${index + 1}.".padEnd(3) // "1. " or "10."
                        
                        // Position colon based ONLY on item name length, not including numbering
                        val paddedName = String.format("%-${maxItemNameLength}s", item.name) // Pad item name to max length
                        appendLine("$number$paddedName : $quantityUnit") // Number + padded name + colon + quantity
                    }
                    appendLine()
                }
            }
            
            if (checkedItems.isNotEmpty()) {
                appendLine("ALREADY HAVE:")
                appendLine("─".repeat(15)) // Fixed length dashes for consistency
                
                checkedItems.forEachIndexed { index, item ->
                    val quantityUnit = formatQuantityUnitAligned(item.quantity, item.unit)
                    val number = "${index + 1}.".padEnd(3)
                    
                    // Position colon based ONLY on item name length, not including numbering
                    val paddedName = String.format("%-${maxItemNameLength}s", item.name) // Pad item name to max length
                    appendLine("$number$paddedName : $quantityUnit ✓") // Number + padded name + colon + quantity
                }
                appendLine()
            }
            
            // Clean summary
            val totalItems = items.size
            appendLine("Total Items: $totalItems")
        }
        
        return builder.toString().trim()
    }
    
    private fun formatQuantityUnitAligned(quantity: Float, unit: String): String {
        return if (unit == "nos" && quantity == 1f) {
            "1 nos"
        } else {
            val quantityStr = if (quantity % 1 == 0f) {
                quantity.toInt().toString()
            } else {
                quantity.toString()
            }
            "$quantityStr $unit"
        }
    }
    
    private fun formatQuantityUnit(quantity: Float, unit: String): String {
        return if (unit == "nos" && quantity == 1f) {
            ""
        } else {
            val quantityStr = if (quantity % 1 == 0f) {
                quantity.toInt().toString()
            } else {
                quantity.toString()
            }
            " ($quantityStr $unit)"
        }
    }
    
    private operator fun String.times(count: Int): String = repeat(count)
}