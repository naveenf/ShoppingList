package com.example.shoppinglist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
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
            .height(40.dp) // Match the paper line spacing exactly
            .padding(start = 72.dp, end = 16.dp), // Align with margin line (60dp + 12dp padding)
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Checkbox styled like a handwritten checkmark
        IconButton(
            onClick = { onCheckedChange(!item.isChecked) },
            modifier = Modifier.size(28.dp)
        ) {
            if (item.isChecked) {
                Icon(
                    Icons.Default.Check,
                    contentDescription = "Checked",
                    tint = PaperInkBlue,
                    modifier = Modifier.size(22.dp)
                )
            } else {
                // Empty checkbox - just a small square outline
                Box(
                    modifier = Modifier
                        .size(18.dp)
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
        
        // Item content - name with optional notes
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.titleMedium,
                color = if (item.isChecked) PaperInkBlack.copy(alpha = 0.6f) else PaperInkBlack,
                textDecoration = if (item.isChecked) TextDecoration.LineThrough else TextDecoration.None,
                modifier = Modifier.alpha(if (item.isChecked) 0.7f else 1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            if (item.notes?.isNotBlank() == true) {
                Text(
                    text = item.notes,
                    style = MaterialTheme.typography.bodySmall,
                    color = PaperInkBlack.copy(alpha = 0.5f),
                    modifier = Modifier.alpha(if (item.isChecked) 0.5f else 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        
        // Quantity display - badge-like style similar to Botanic theme
        if (item.quantity > 1 || item.unit != "pcs") {
            Box(
                modifier = Modifier
                    .background(
                        PaperInkBlue.copy(alpha = 0.2f),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = "${if (item.quantity % 1 == 0f) item.quantity.toInt().toString() else item.quantity} ${item.unit}",
                    style = MaterialTheme.typography.bodySmall,
                    color = PaperInkBlue,
                    maxLines = 1
                )
            }
        }
        
        // Edit button - small and subtle
        IconButton(
            onClick = onItemClick,
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                Icons.Default.Edit,
                contentDescription = "Edit",
                tint = PaperInkBlack.copy(alpha = 0.5f),
                modifier = Modifier.size(18.dp)
            )
        }
        
        // Delete button - small and subtle
        IconButton(
            onClick = onDelete,
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                tint = PaperInkBlack.copy(alpha = 0.5f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
fun PaperCategoryHeader(
    category: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp) // Match the paper line spacing
            .padding(start = 72.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category,
                style = MaterialTheme.typography.titleLarge,
                color = PaperInkBlue,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        // Handwritten-style underline 
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5.dp)
                .background(PaperInkBlue.copy(alpha = 0.6f))
        )
    }
}