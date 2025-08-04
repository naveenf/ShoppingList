package com.example.shoppinglist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.data.database.entities.ShoppingItem
import com.example.shoppinglist.ui.theme.PaperInkBlack
import com.example.shoppinglist.ui.theme.PaperInkBlue

@Composable
fun PaperShoppingItem(
    item: ShoppingItem,
    onItemClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp) // Match the paper line height
            .padding(horizontal = 80.dp, vertical = 4.dp), // Left margin to clear the red line
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Checkbox styled like a handwritten checkmark
        IconButton(
            onClick = { onCheckedChange(!item.isChecked) },
            modifier = Modifier.size(24.dp)
        ) {
            if (item.isChecked) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Checked",
                    tint = PaperInkBlue,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                // Empty checkbox - just a small square outline
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(
                            Color.Transparent,
                            RoundedCornerShape(2.dp)
                        )
                        .background(
                            PaperInkBlack.copy(alpha = 0.3f),
                            RoundedCornerShape(2.dp)
                        )
                )
            }
        }
        
        // Item text - looks handwritten
        Text(
            text = buildString {
                append(item.name)
                if (item.quantity > 1) {
                    append(" (${item.quantity}")
                    if (item.unit != "pcs") {
                        append(" ${item.unit}")
                    }
                    append(")")
                }
            },
            style = MaterialTheme.typography.bodyLarge,
            color = if (item.isChecked) PaperInkBlack.copy(alpha = 0.6f) else PaperInkBlack,
            textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None,
            modifier = Modifier
                .weight(1f)
                .alpha(if (item.isChecked) 0.7f else 1f)
        )
        
        // Delete button - small and subtle
        IconButton(
            onClick = onDelete,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                tint = PaperInkBlack.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun PaperCategoryHeader(
    category: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 80.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = category,
            style = MaterialTheme.typography.titleMedium,
            color = PaperInkBlue,
            modifier = Modifier.padding(vertical = 4.dp)
        )
        
        // Underline like handwritten category headers
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .height(1.dp)
                .weight(1f)
                .background(PaperInkBlue.copy(alpha = 0.5f))
        )
    }
}