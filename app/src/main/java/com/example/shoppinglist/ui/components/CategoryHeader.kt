package com.example.shoppinglist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun CategoryHeader(
    categoryName: String,
    itemCount: Int,
    modifier: Modifier = Modifier
) {
    val categoryIcon = when (categoryName.lowercase()) {
        "produce" -> "🥬"
        "dairy & eggs", "dairy" -> "🥛"
        "bakery" -> "🥖"
        "meat & seafood", "meat" -> "🥩"
        "pantry" -> "🥫"
        "frozen" -> "🧊"
        "personal care" -> "🧴"
        "household" -> "🧹"
        else -> "📦"
    }
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = categoryIcon,
            style = MaterialTheme.typography.titleMedium
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = categoryName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.weight(1f)
        )
        
        Text(
            text = "$itemCount items",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}