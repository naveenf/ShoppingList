package com.example.shoppinglist.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shoppinglist.data.database.entities.ShoppingItem
import com.example.shoppinglist.ui.components.AddItemDialog
import com.example.shoppinglist.ui.components.CategoryHeader
import com.example.shoppinglist.ui.components.EditItemDialog
import com.example.shoppinglist.ui.components.EmptyState
import com.example.shoppinglist.ui.components.ShoppingItemCard
import com.example.shoppinglist.utils.ShareUtils
import com.example.shoppinglist.viewmodel.ShoppingViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainListScreen(
    viewModel: ShoppingViewModel,
    listId: String,
    listName: String,
    onBackToLists: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val items by viewModel.getItemsForList(listId).collectAsStateWithLifecycle(initialValue = emptyList())
    val predefinedItems by viewModel.allPredefinedItems.collectAsStateWithLifecycle(initialValue = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    var showShareMenu by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<ShoppingItem?>(null) }

    // Group items by category
    val groupedItems = items.groupBy { it.category }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(listName) },
                navigationIcon = {
                    IconButton(onClick = onBackToLists) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back to lists"
                        )
                    }
                },
                actions = {
                    if (items.isNotEmpty()) {
                        Box {
                            IconButton(onClick = { showShareMenu = true }) {
                                Icon(
                                    Icons.Default.MoreVert,
                                    contentDescription = "More options"
                                )
                            }
                            DropdownMenu(
                                expanded = showShareMenu,
                                onDismissRequest = { showShareMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Share List") },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Share,
                                            contentDescription = null
                                        )
                                    },
                                    onClick = {
                                        ShareUtils.shareShoppingList(context, listName, items)
                                        showShareMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Share via WhatsApp") },
                                    onClick = {
                                        ShareUtils.shareToWhatsApp(context, listName, items)
                                        showShareMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Share via SMS") },
                                    onClick = {
                                        ShareUtils.shareViaSMS(context, listName, items)
                                        showShareMenu = false
                                    }
                                )
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    Icons.Default.Add, 
                    contentDescription = "Add item", 
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->
        if (items.isEmpty()) {
            EmptyState(modifier = Modifier.padding(paddingValues))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                groupedItems.forEach { (category, categoryItems) ->
                    item(key = "header_$category") {
                        CategoryHeader(
                            categoryName = category,
                            itemCount = categoryItems.size
                        )
                    }
                    
                    items(
                        items = categoryItems,
                        key = { it.id }
                    ) { item ->
                        ShoppingItemCard(
                            item = item,
                            onCheckedChange = { checked ->
                                viewModel.update(item.copy(isChecked = checked))
                            },
                            onDelete = {
                                viewModel.delete(item)
                            },
                            onEdit = {
                                editingItem = item
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 2.dp)
                        )
                    }
                    
                    item(key = "spacer_$category") {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddItemDialog(
            onDismiss = { showAddDialog = false },
            onSave = { item ->
                viewModel.insert(item)
                showAddDialog = false
            },
            listId = listId,
            predefinedItems = predefinedItems,
            onAddCustomItem = { name, category ->
                viewModel.addCustomPredefinedItem(name, category)
            },
            onDeleteCustomItem = { item ->
                viewModel.deletePredefinedItem(item)
            }
        )
    }

    editingItem?.let { item ->
        EditItemDialog(
            item = item,
            onDismiss = { editingItem = null },
            onSave = { updatedItem ->
                viewModel.update(updatedItem)
                editingItem = null
            }
        )
    }
}
