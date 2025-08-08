package com.example.shoppinglist.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.data.database.entities.ShoppingItem
import com.example.shoppinglist.utils.TextUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditItemDialog(
    item: ShoppingItem,
    onDismiss: () -> Unit,
    onSave: (ShoppingItem) -> Unit,
    availableCategories: List<String> = listOf(
        "Produce", "Dairy & Eggs", "Bakery", "Meat & Seafood",
        "Pantry", "Frozen", "Personal Care", "Household"
    )
) {
    var itemName by remember { mutableStateOf(item.name) }
    var quantity by remember { mutableStateOf(item.quantity.toString()) }
    var unit by remember { mutableStateOf(item.unit) }
    var category by remember { mutableStateOf(item.category) }
    var notes by remember { mutableStateOf(item.notes ?: "") }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Item") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = itemName,
                    onValueChange = { itemName = it },
                    label = { Text("Item Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = quantity,
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || newValue.toFloatOrNull() != null) {
                                quantity = newValue
                            }
                        },
                        label = { Text("Quantity") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedTextField(
                        value = unit,
                        onValueChange = { unit = it },
                        label = { Text("Unit") },
                        placeholder = { Text("nos, kg, g, l") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = category,
                        onValueChange = { category = it },
                        label = { Text("Category") },
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        availableCategories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = {
                                    category = cat
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes (optional)") },
                    maxLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val updatedItem = item.copy(
                        name = TextUtils.formatItemName(itemName.trim()),
                        quantity = quantity.toFloatOrNull() ?: 1f,
                        unit = unit.trim().ifEmpty { "nos" },
                        category = category.trim(),
                        notes = notes.trim().ifEmpty { null },
                        lastModified = System.currentTimeMillis()
                    )
                    onSave(updatedItem)
                },
                enabled = itemName.isNotBlank() && quantity.isNotBlank() && category.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}