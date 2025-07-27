package com.example.shoppinglist.data.repository

import com.example.shoppinglist.data.database.ItemDao
import com.example.shoppinglist.data.database.PredefinedItemDao
import com.example.shoppinglist.data.database.entities.PredefinedItem
import com.example.shoppinglist.data.database.entities.ShoppingItem
import com.example.shoppinglist.data.database.entities.ShoppingList
import kotlinx.coroutines.flow.Flow

class ShoppingRepository(
    private val itemDao: ItemDao,
    private val predefinedItemDao: PredefinedItemDao
) {
    // Shopping Items
    fun getItemsByList(listId: String): Flow<List<ShoppingItem>> = itemDao.getItemsByList(listId)
    fun getCategoriesByList(listId: String): Flow<List<String>> = itemDao.getCategoriesByList(listId)

    suspend fun insert(item: ShoppingItem) {
        itemDao.insert(item)
    }

    suspend fun update(item: ShoppingItem) {
        itemDao.update(item)
    }

    suspend fun delete(item: ShoppingItem) {
        itemDao.delete(item)
    }

    suspend fun deleteById(itemId: String) {
        itemDao.deleteById(itemId)
    }

    suspend fun getItemById(itemId: String): ShoppingItem? {
        return itemDao.getItemById(itemId)
    }

    fun getItemsByListAndCategory(listId: String, category: String): Flow<List<ShoppingItem>> {
        return itemDao.getItemsByListAndCategory(listId, category)
    }

    // Shopping Lists
    val allLists: Flow<List<ShoppingList>> = itemDao.getAllLists()

    suspend fun insertList(list: ShoppingList) {
        itemDao.insertList(list)
    }

    suspend fun updateList(list: ShoppingList) {
        itemDao.updateList(list)
    }

    suspend fun deleteList(list: ShoppingList) {
        itemDao.deleteList(list)
    }

    suspend fun getListById(listId: String): ShoppingList? {
        return itemDao.getListById(listId)
    }

    suspend fun getItemCountForList(listId: String): Int {
        return itemDao.getItemCountForList(listId)
    }

    suspend fun getCheckedItemCountForList(listId: String): Int {
        return itemDao.getCheckedItemCountForList(listId)
    }

    // Predefined Items
    fun searchPredefinedItems(query: String): Flow<List<PredefinedItem>> {
        return predefinedItemDao.searchItems(query)
    }

    fun getAllPredefinedItems(): Flow<List<PredefinedItem>> {
        return predefinedItemDao.getAllItems()
    }

    suspend fun addCustomPredefinedItem(name: String, category: String) {
        val itemCount = predefinedItemDao.getItemCount()
        val newItem = PredefinedItem(
            id = itemCount + 1000, // Start custom items at 1000+
            name = name,
            category = category,
            defaultUnit = "nos",
            searchKeywords = name.lowercase()
        )
        predefinedItemDao.insertPredefinedItems(listOf(newItem))
    }

    suspend fun deletePredefinedItem(item: PredefinedItem) {
        predefinedItemDao.deletePredefinedItem(item)
    }
}
