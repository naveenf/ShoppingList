package com.example.shoppinglist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.data.database.entities.PredefinedItem
import com.example.shoppinglist.data.database.entities.ShoppingItem
import com.example.shoppinglist.data.database.entities.ShoppingList
import com.example.shoppinglist.data.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ShoppingViewModel(private val repository: ShoppingRepository) : ViewModel() {

    // Shopping Items
    fun getItemsForList(listId: String): Flow<List<ShoppingItem>> = repository.getItemsByList(listId)

    fun insert(item: ShoppingItem) = viewModelScope.launch {
        repository.insert(item)
    }

    fun update(item: ShoppingItem) = viewModelScope.launch {
        repository.update(item)
    }

    fun delete(item: ShoppingItem) = viewModelScope.launch {
        repository.delete(item)
    }

    // Shopping Lists
    val allLists: Flow<List<ShoppingList>> = repository.allLists

    fun insertList(list: ShoppingList) = viewModelScope.launch {
        repository.insertList(list)
    }

    fun updateList(list: ShoppingList) = viewModelScope.launch {
        repository.updateList(list)
    }

    fun deleteList(list: ShoppingList) = viewModelScope.launch {
        repository.deleteList(list)
    }

    // Predefined Items
    val allPredefinedItems: Flow<List<PredefinedItem>> = repository.getAllPredefinedItems()

    fun searchPredefinedItems(query: String): Flow<List<PredefinedItem>> {
        return repository.searchPredefinedItems(query)
    }

    fun addCustomPredefinedItem(name: String, category: String) = viewModelScope.launch {
        repository.addCustomPredefinedItem(name, category)
    }

    // Helper functions
    suspend fun getItemCountForList(listId: String): Int {
        return repository.getItemCountForList(listId)
    }

    suspend fun getCheckedItemCountForList(listId: String): Int {
        return repository.getCheckedItemCountForList(listId)
    }
}
