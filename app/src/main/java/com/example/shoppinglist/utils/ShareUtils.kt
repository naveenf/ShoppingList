package com.example.shoppinglist.utils

import android.content.Context
import android.content.Intent
import com.example.shoppinglist.data.database.entities.ShoppingItem

object ShareUtils {
    
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
        
        val builder = StringBuilder().apply {
            // Clean header
            appendLine("🛒 $listName")
            appendLine()
            
            if (uncheckedItems.isNotEmpty()) {
                appendLine("NEED TO BUY:")
                appendLine()
                
                uncheckedItems.groupBy { it.category }.forEach { (category, categoryItems) ->
                    appendLine("$category")
                    categoryItems.forEach { item ->
                        val quantityUnit = formatQuantityUnit(item.quantity, item.unit)
                        appendLine("• ${item.name}$quantityUnit")
                    }
                    appendLine()
                }
            }
            
            if (checkedItems.isNotEmpty()) {
                appendLine("ALREADY HAVE:")
                appendLine()
                checkedItems.forEach { item ->
                    val quantityUnit = formatQuantityUnit(item.quantity, item.unit)
                    appendLine("✓ ${item.name}$quantityUnit")
                }
                appendLine()
            }
            
            // Clean summary
            if (uncheckedItems.isNotEmpty()) {
                appendLine("${uncheckedItems.size} items remaining")
            } else {
                appendLine("Shopping complete! ✨")
            }
        }
        
        return builder.toString().trim()
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