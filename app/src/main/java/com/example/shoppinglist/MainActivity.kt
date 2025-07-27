package com.example.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinglist.data.DatabaseInitializer
import com.example.shoppinglist.data.database.ShoppingDatabase
import com.example.shoppinglist.data.repository.ShoppingRepository
import com.example.shoppinglist.ui.screens.MainListScreen
import com.example.shoppinglist.ui.theme.ShoppingListTheme
import com.example.shoppinglist.viewmodel.ShoppingViewModel
import com.example.shoppinglist.viewmodel.ShoppingViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MainActivity : ComponentActivity() {
    
    private val applicationScope = CoroutineScope(SupervisorJob())
    
    private val database by lazy {
        ShoppingDatabase.getDatabase(applicationContext, applicationScope)
    }
    
    private val repository by lazy {
        ShoppingRepository(database.itemDao(), database.predefinedItemDao())
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            ShoppingListTheme {
                val viewModel: ShoppingViewModel = viewModel(factory = ShoppingViewModelFactory(repository))
                
                // Initialize predefined items after UI is set up
                LaunchedEffect(Unit) {
                    try {
                        DatabaseInitializer.populatePredefinedItems(database.predefinedItemDao())
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                
                MainListScreen(viewModel = viewModel, listId = "default-list")
            }
        }
    }
}
