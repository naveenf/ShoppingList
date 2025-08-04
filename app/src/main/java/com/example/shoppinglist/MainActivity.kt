package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinglist.data.DatabaseInitializer
import com.example.shoppinglist.data.PremiumManager
import com.example.shoppinglist.data.ThemeManager
import com.example.shoppinglist.data.database.ShoppingDatabase
import com.example.shoppinglist.data.repository.ShoppingRepository
import com.example.shoppinglist.data.database.entities.ShoppingList
import com.example.shoppinglist.ui.screens.ListSelectionScreen
import com.example.shoppinglist.ui.screens.MainListScreen
import com.example.shoppinglist.ui.screens.SettingsScreen
import com.example.shoppinglist.ui.theme.AppTheme
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import com.example.shoppinglist.viewmodel.ShoppingViewModel
import com.example.shoppinglist.viewmodel.ShoppingViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    
    private val applicationScope = CoroutineScope(SupervisorJob())
    
    private val database by lazy {
        ShoppingDatabase.getDatabase(applicationContext, applicationScope)
    }
    
    private val premiumManager by lazy {
        PremiumManager(applicationContext)
    }
    
    private val themeManager by lazy {
        ThemeManager(applicationContext)
    }
    
    private val repository by lazy {
        ShoppingRepository(database.itemDao(), database.predefinedItemDao(), database.templateDao(), premiumManager)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            val selectedTheme by themeManager.selectedTheme.collectAsStateWithLifecycle(initialValue = AppTheme.MODERN_LIGHT)
            
            ShoppingListTheme(theme = selectedTheme) {
                val viewModel: ShoppingViewModel = viewModel(factory = ShoppingViewModelFactory(repository))
                var selectedList by remember { mutableStateOf<ShoppingList?>(null) }
                var showSettings by remember { mutableStateOf(false) }
                
                val shoppingLists by viewModel.allLists.collectAsStateWithLifecycle(initialValue = emptyList())
                val templates by viewModel.allTemplates.collectAsStateWithLifecycle(initialValue = emptyList())
                val isPremium by premiumManager.isPremium.collectAsStateWithLifecycle(initialValue = false)
                
                // Initialize predefined items after UI is set up
                LaunchedEffect(Unit) {
                    try {
                        DatabaseInitializer.populatePredefinedItems(database.predefinedItemDao())
                        DatabaseInitializer.populateTemplates(database.templateDao())
                        
                        // Create default list if no lists exist
                        if (shoppingLists.isEmpty()) {
                            val defaultList = ShoppingList(name = "Shopping List")
                            viewModel.insertList(defaultList)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                
                // Navigation logic
                when {
                    showSettings -> {
                        SettingsScreen(
                            isPremium = isPremium,
                            onPremiumToggle = { premium ->
                                applicationScope.launch {
                                    premiumManager.setPremiumStatus(premium)
                                }
                            },
                            selectedTheme = selectedTheme,
                            onThemeChange = { theme ->
                                applicationScope.launch {
                                    themeManager.setTheme(theme)
                                }
                            },
                            onBackClick = { showSettings = false }
                        )
                    }
                    selectedList != null -> {
                        MainListScreen(
                            viewModel = viewModel, 
                            listId = selectedList!!.id,
                            listName = selectedList!!.name,
                            onBackToLists = { selectedList = null }
                        )
                    }
                    else -> {
                    ListSelectionScreen(
                        shoppingLists = shoppingLists,
                        itemCounts = emptyMap(), // TODO: Calculate actual counts
                        checkedCounts = emptyMap(), // TODO: Calculate actual counts
                        templates = templates,
                        isPremium = isPremium,
                        onListSelected = { selectedList = it },
                        onCreateNewList = { listName ->
                            val newList = ShoppingList(name = listName)
                            viewModel.insertList(newList)
                            selectedList = newList
                        },
                        onCreateFromTemplate = { template, listName ->
                            viewModel.createListFromTemplate(template, listName)
                        },
                        onDeleteList = { list ->
                            viewModel.deleteList(list)
                        },
                        onUpgradeToPremium = {
                            // For demo purposes, just enable premium
                            // In a real app, this would open a payment flow
                            applicationScope.launch {
                                premiumManager.setPremiumStatus(true)
                            }
                        },
                        onSettingsClick = { showSettings = true }
                    )
                    }
                }
            }
        }
    }
}
