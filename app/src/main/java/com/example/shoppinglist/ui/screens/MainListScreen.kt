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
import com.example.shoppinglist.ui.components.FamilySharingDialog
import com.example.shoppinglist.ui.components.PaperCategoryHeader
import com.example.shoppinglist.ui.components.PaperShoppingItem
import com.example.shoppinglist.ui.components.ShoppingItemCard
import com.example.shoppinglist.ui.theme.AppTheme
import com.example.shoppinglist.utils.ShareUtils
import com.example.shoppinglist.utils.DeviceUtils
import com.example.shoppinglist.viewmodel.ShoppingViewModel
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainListScreen(
    viewModel: ShoppingViewModel,
    listId: String,
    listName: String,
    currentTheme: AppTheme,
    onBackToLists: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val items by viewModel.getItemsForList(listId).collectAsStateWithLifecycle(initialValue = emptyList())
    val predefinedItems by viewModel.allPredefinedItems.collectAsStateWithLifecycle(initialValue = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    var showShareMenu by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<ShoppingItem?>(null) }
    
    // Family sharing state
    var showFamilySharingDialog by remember { mutableStateOf(false) }
    var familyShareCode by remember { mutableStateOf<String?>(null) }
    
    // Check if this list is already a family list
    val isFamilyList by remember { 
        mutableStateOf(false) // This will be connected to viewModel in Phase 2
    }

    // Group items by category
    val groupedItems = items.groupBy { it.category }

    Scaffold(
        containerColor = if (currentTheme == AppTheme.PAPER || currentTheme == AppTheme.BOTANIC) {
            androidx.compose.ui.graphics.Color.Transparent
        } else {
            MaterialTheme.colorScheme.background
        },
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
                                // Family sharing option
                                if (!isFamilyList) {
                                    DropdownMenuItem(
                                        text = { Text("👥 Share with Family") },
                                        onClick = {
                                            // Generate family share code and show dialog
                                            familyShareCode = DeviceUtils.generateFamilyShareCode()
                                            showFamilySharingDialog = true
                                            showShareMenu = false
                                        }
                                    )
                                    HorizontalDivider()
                                }
                                
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
                contentPadding = if (currentTheme == AppTheme.PAPER) {
                    PaddingValues(vertical = 40.dp) // Start at first line
                } else {
                    PaddingValues(vertical = 8.dp)
                },
                verticalArrangement = if (currentTheme == AppTheme.PAPER) {
                    Arrangement.spacedBy(0.dp) // No extra spacing - items align exactly with lines
                } else {
                    Arrangement.spacedBy(1.dp)
                }
            ) {
                groupedItems.forEach { (category, categoryItems) ->
                    item(key = "header_$category") {
                        if (currentTheme == AppTheme.PAPER) {
                            PaperCategoryHeader(
                                category = category,
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
                            CategoryHeader(
                                categoryName = category,
                                itemCount = categoryItems.size,
                                useSemiTransparentBackground = currentTheme == AppTheme.BOTANIC
                            )
                        }
                    }
                    
                    items(
                        items = categoryItems,
                        key = { it.id }
                    ) { item ->
                        if (currentTheme == AppTheme.PAPER) {
                            PaperShoppingItem(
                                item = item,
                                onItemClick = {
                                    editingItem = item
                                },
                                onCheckedChange = { checked ->
                                    viewModel.update(item.copy(isChecked = checked))
                                },
                                onDelete = {
                                    viewModel.delete(item)
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        } else {
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
                                useSemiTransparentBackground = currentTheme == AppTheme.BOTANIC,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 2.dp)
                            )
                        }
                    }
                    
                    item(key = "spacer_$category") {
                        if (currentTheme == AppTheme.PAPER) {
                            Spacer(modifier = Modifier.height(16.dp))
                        } else {
                            Spacer(modifier = Modifier.height(8.dp))
                        }
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
            },
            onSuggestItemDetails = { itemName ->
                runBlocking { viewModel.suggestItemDetails(itemName) }
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
    
    // Family sharing dialog
    if (showFamilySharingDialog && familyShareCode != null) {
        FamilySharingDialog(
            shareCode = familyShareCode!!,
            onDismiss = { 
                showFamilySharingDialog = false
                familyShareCode = null
            },
            onShareCode = { code ->
                ShareUtils.shareText(context, "Join our family shopping list! Use code: $code")
            }
        )
    }
}
