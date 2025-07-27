package com.example.shoppinglist.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.MenuAnchorType
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.shoppinglist.data.database.entities.PredefinedItem
import com.example.shoppinglist.data.database.entities.ShoppingItem

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddItemDialog(
    onDismiss: () -> Unit,
    onSave: (ShoppingItem) -> Unit,
    listId: String,
    predefinedItems: List<PredefinedItem> = emptyList(),
    onAddCustomItem: (String, String) -> Unit = { _, _ -> },
    onDeleteCustomItem: (PredefinedItem) -> Unit = { _ -> },
    modifier: Modifier = Modifier
) {
    var itemName by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("1") }
    var unit by remember { mutableStateOf("nos") }
    var category by remember { mutableStateOf("Pantry") }
    var notes by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var unitExpanded by remember { mutableStateOf(false) }
    var showCustomForm by remember { mutableStateOf(false) }
    var selectedPredefinedItem by remember { mutableStateOf<PredefinedItem?>(null) }
    var itemToDelete by remember { mutableStateOf<PredefinedItem?>(null) }

    val categories = listOf(
        "Produce", "Dairy & Eggs", "Bakery", "Meat & Seafood", 
        "Pantry", "Frozen", "Personal Care", "Household", "Beverages"
    )
    
    val units = listOf("nos", "kg", "gm", "liters", "ml", "lbs", "oz", "cups")

    val filteredItems = remember(itemName, predefinedItems) {
        if (itemName.length < 2) emptyList()
        else predefinedItems.filter { 
            it.name.contains(itemName, ignoreCase = true) ||
            it.searchKeywords.contains(itemName, ignoreCase = true)
        }.take(10)
    }

    val hasExactMatch = remember(itemName, predefinedItems) {
        predefinedItems.any { it.name.equals(itemName, ignoreCase = true) }
    }

    LaunchedEffect(selectedPredefinedItem) {
        selectedPredefinedItem?.let { item ->
            itemName = item.name
            category = item.category
            unit = item.defaultUnit
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = if (showCustomForm) "Add Custom Item" else "Add Item",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (!showCustomForm) {
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { 
                            itemName = it
                            selectedPredefinedItem = null
                        },
                        label = { Text("Search items...") },
                        placeholder = { Text("e.g., milk, cheese, bread") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    if (itemName.length >= 2) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 200.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            LazyColumn {
                                if (filteredItems.isNotEmpty()) {
                                    items(filteredItems) { item ->
                                        ListItem(
                                            headlineContent = { Text(item.name) },
                                            supportingContent = { 
                                                Text("${item.category} • ${item.defaultUnit}${if (item.id >= 1000) " • Custom" else ""}") 
                                            },
                                            trailingContent = {
                                                if (item.id >= 1000) { // Custom item
                                                    IconButton(
                                                        onClick = { itemToDelete = item }
                                                    ) {
                                                        Icon(
                                                            Icons.Default.Delete,
                                                            contentDescription = "Delete custom item",
                                                            tint = MaterialTheme.colorScheme.error
                                                        )
                                                    }
                                                }
                                            },
                                            modifier = Modifier.clickable {
                                                selectedPredefinedItem = item
                                            }
                                        )
                                        if (item != filteredItems.last()) {
                                            HorizontalDivider()
                                        }
                                    }
                                }
                                
                                // Always show "Add custom item" if it's not an exact match
                                if (!hasExactMatch) {
                                    if (filteredItems.isNotEmpty()) {
                                        item {
                                            HorizontalDivider()
                                        }
                                    }
                                    item {
                                        ListItem(
                                            headlineContent = { Text("Add \"$itemName\" as custom item") },
                                            leadingContent = { 
                                                Icon(
                                                    Icons.Default.Add, 
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary
                                                ) 
                                            },
                                            modifier = Modifier.clickable {
                                                showCustomForm = true
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        label = { Text("Item name") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { 
                            if (it.isEmpty() || it.toFloatOrNull() != null) {
                                quantity = it
                            }
                        },
                        label = { Text("Quantity") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    ExposedDropdownMenuBox(
                        expanded = unitExpanded,
                        onExpandedChange = { unitExpanded = it },
                        modifier = Modifier.weight(1f)
                    ) {
                        OutlinedTextField(
                            value = unit,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = unitExpanded) },
                            label = { Text("Unit") },
                            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        )
                        ExposedDropdownMenu(
                            expanded = unitExpanded,
                            onDismissRequest = { unitExpanded = false }
                        ) {
                            units.forEach { unitOption ->
                                DropdownMenuItem(
                                    text = { Text(unitOption) },
                                    onClick = {
                                        unit = unitOption
                                        unitExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                if (showCustomForm) {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = it }
                    ) {
                        OutlinedTextField(
                            value = category,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            label = { Text("Category") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categories.forEach { categoryOption ->
                                DropdownMenuItem(
                                    text = { Text(categoryOption) },
                                    onClick = {
                                        category = categoryOption
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showCustomForm) {
                        TextButton(onClick = { showCustomForm = false }) {
                            Text("Back")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                    
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Button(
                        onClick = {
                            if (itemName.isNotBlank()) {
                                if (showCustomForm) {
                                    onAddCustomItem(itemName.trim(), category)
                                }
                                
                                val item = ShoppingItem(
                                    listId = listId,
                                    name = itemName.trim(),
                                    quantity = quantity.toFloatOrNull() ?: 1f,
                                    unit = unit,
                                    category = category,
                                    notes = notes.takeIf { it.isNotBlank() }
                                )
                                onSave(item)
                            }
                        },
                        enabled = itemName.isNotBlank() && (selectedPredefinedItem != null || showCustomForm)
                    ) {
                        Text("Add Item")
                    }
                }
            }
        }
    }

    // Delete confirmation dialog
    itemToDelete?.let { item ->
        AlertDialog(
            onDismissRequest = { itemToDelete = null },
            title = { Text("Delete Custom Item") },
            text = { Text("Are you sure you want to delete \"${item.name}\"? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteCustomItem(item)
                        itemToDelete = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { itemToDelete = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}