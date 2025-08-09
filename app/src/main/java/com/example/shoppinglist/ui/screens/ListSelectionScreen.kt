package com.example.shoppinglist.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import com.example.shoppinglist.ui.components.JoinFamilyDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.shoppinglist.data.database.entities.ListTemplate
import com.example.shoppinglist.data.database.entities.ShoppingList
import com.example.shoppinglist.ui.components.FamilySharingDialog
import com.example.shoppinglist.utils.DeviceUtils
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListSelectionScreen(
    shoppingLists: List<ShoppingList>,
    itemCounts: Map<String, Int>,
    checkedCounts: Map<String, Int>,
    templates: List<ListTemplate>,
    isPremium: Boolean = false,
    onListSelected: (ShoppingList) -> Unit,
    onCreateNewList: (String) -> Unit,
    onCreateFromTemplate: (ListTemplate, String) -> Unit,
    onDeleteList: (ShoppingList) -> Unit,
    onUpgradeToPremium: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onJoinFamilyList: (String, String) -> Unit = { _, _ -> },
    onShareList: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showCreateDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf<ShoppingList?>(null) }
    var showJoinFamilyDialog by remember { mutableStateOf(false) }
    var joinFamilyError by remember { mutableStateOf<String?>(null) }
    var isJoiningFamily by remember { mutableStateOf(false) }
    var showFamilySharingDialog by remember { mutableStateOf(false) }
    var familyShareCode by remember { mutableStateOf<String?>(null) }
    var sharingList by remember { mutableStateOf<ShoppingList?>(null) }
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            // Dual FABs for creating and joining lists
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Join Family List FAB
                FloatingActionButton(
                    onClick = { 
                        println("Join Family FAB clicked!")
                        showJoinFamilyDialog = true 
                    },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ) {
                    Text(
                        text = "👥",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                
                // Create New List FAB (Primary)
                FloatingActionButton(
                    onClick = { 
                        println("Create New FAB clicked!")
                        showCreateDialog = true 
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Create new list")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
        TopAppBar(
            title = { Text("My Shopping Lists") },
            actions = {
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface,
                titleContentColor = MaterialTheme.colorScheme.onSurface
            )
        )
        
        if (shoppingLists.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "🛒 Welcome to Shopping Lists!",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Create your own list or join a family member's list using their share code",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    // Action hint
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "👥",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "Join Family",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            
                            Spacer(modifier = Modifier.width(8.dp))
                            
                            Text(
                                text = "➕",
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                text = "Create New",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(shoppingLists) { list ->
                    ShoppingListCard(
                        list = list,
                        itemCount = itemCounts[list.id] ?: 0,
                        checkedCount = checkedCounts[list.id] ?: 0,
                        onClick = { onListSelected(list) },
                        onDeleteClick = { showDeleteDialog = list },
                        onShareClick = { 
                            familyShareCode = DeviceUtils.generateFamilyShareCode()
                            sharingList = list
                            showFamilySharingDialog = true 
                        }
                    )
                }
            }
        }
    }
    } // Close Scaffold content block
    
    // All dialogs should be outside the Scaffold
    if (showCreateDialog) {
        CreateListDialog(
            templates = templates,
            isPremium = isPremium,
            onDismiss = { showCreateDialog = false },
            onConfirm = { listName ->
                onCreateNewList(listName)
                showCreateDialog = false
            },
            onCreateFromTemplate = { template, listName ->
                onCreateFromTemplate(template, listName)
                showCreateDialog = false
            },
            onUpgradeToPremium = onUpgradeToPremium
        )
    }
    
    showDeleteDialog?.let { list ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("Delete List") },
            text = { Text("Are you sure you want to delete \"${list.name}\"? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteList(list)
                        showDeleteDialog = null
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = null }) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // Join Family Dialog
    if (showJoinFamilyDialog) {
        JoinFamilyDialog(
            onDismiss = { 
                showJoinFamilyDialog = false
                joinFamilyError = null
                isJoiningFamily = false
            },
            onJoinFamily = { familyCode ->
                isJoiningFamily = true
                joinFamilyError = null
                
                // Phase 1: Create a local family list with the share code
                val listName = "Family List ($familyCode)"
                onJoinFamilyList(familyCode, listName)
                
                isJoiningFamily = false
                showJoinFamilyDialog = false
            },
            isLoading = isJoiningFamily,
            errorMessage = joinFamilyError
        )
    }
    
    // Family Sharing Dialog
    if (showFamilySharingDialog && familyShareCode != null) {
        FamilySharingDialog(
            shareCode = familyShareCode!!,
            onDismiss = { 
                showFamilySharingDialog = false
                familyShareCode = null
                sharingList = null
            },
            onShareCode = { code ->
                sharingList?.let { list ->
                    onShareList(code)
                }
            }
        )
    }
}

@Composable
private fun ShoppingListCard(
    list: ShoppingList,
    itemCount: Int,
    checkedCount: Int,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onShareClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
    
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = list.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Created ${dateFormat.format(Date(list.createdDate))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (itemCount > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$checkedCount/$itemCount items completed",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "More options")
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("👥 Share with Family") },
                        onClick = {
                            showMenu = false
                            onShareClick()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = {
                            showMenu = false
                            onDeleteClick()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CreateListDialog(
    templates: List<ListTemplate>,
    isPremium: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    onCreateFromTemplate: (ListTemplate, String) -> Unit,
    onUpgradeToPremium: () -> Unit
) {
    var listName by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(0) }
    var selectedTemplate by remember { mutableStateOf<ListTemplate?>(null) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New List") },
        text = {
            Column {
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0; selectedTemplate = null },
                        text = { Text("Empty List") }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("From Template") }
                    )
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = listName,
                    onValueChange = { listName = it },
                    label = { Text("List name") },
                    placeholder = { 
                        Text(if (selectedTemplate != null) selectedTemplate!!.name else "e.g., Weekly Groceries") 
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (selectedTab == 1) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Choose a template:",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    LazyColumn(
                        modifier = Modifier.height(200.dp)
                    ) {
                        items(templates) { template ->
                            TemplateCard(
                                template = template,
                                isSelected = selectedTemplate == template,
                                isPremium = isPremium,
                                onClick = { 
                                    if (template.isPremium && !isPremium) {
                                        onUpgradeToPremium()
                                    } else {
                                        selectedTemplate = template
                                        if (listName.isBlank()) {
                                            listName = template.name
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = { 
                    if (selectedTab == 0) {
                        onConfirm(listName)
                    } else {
                        selectedTemplate?.let { template ->
                            onCreateFromTemplate(template, listName)
                        }
                    }
                },
                enabled = listName.isNotBlank() && (selectedTab == 0 || selectedTemplate != null)
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun TemplateCard(
    template: ListTemplate,
    isSelected: Boolean,
    isPremium: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                isSelected -> MaterialTheme.colorScheme.primaryContainer
                template.isPremium && !isPremium -> MaterialTheme.colorScheme.surfaceVariant
                else -> MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = template.icon ?: "📋",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(end = 12.dp)
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = template.name,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    if (template.isPremium) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "Premium",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .size(16.dp)
                                .padding(start = 4.dp)
                        )
                    }
                }
                Text(
                    text = template.description + if (template.estimatedPeople != null) " (${template.estimatedPeople} people)" else "",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${template.items.size} items",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}