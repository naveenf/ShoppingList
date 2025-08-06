package com.example.shoppinglist.data.repository

import com.example.shoppinglist.data.PremiumManager
import com.example.shoppinglist.data.database.ItemDao
import com.example.shoppinglist.data.database.ItemPatternDao
import com.example.shoppinglist.data.database.PredefinedItemDao
import com.example.shoppinglist.data.database.TemplateDao
import com.example.shoppinglist.data.database.entities.ItemPattern
import com.example.shoppinglist.data.database.entities.ListTemplate
import com.example.shoppinglist.data.database.entities.PredefinedItem
import com.example.shoppinglist.data.database.entities.ShoppingItem
import com.example.shoppinglist.data.database.entities.ShoppingList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first

class ShoppingRepository(
    private val itemDao: ItemDao,
    private val predefinedItemDao: PredefinedItemDao,
    private val templateDao: TemplateDao,
    private val itemPatternDao: ItemPatternDao,
    private val premiumManager: PremiumManager
) {
    // Shopping Items
    fun getItemsByList(listId: String): Flow<List<ShoppingItem>> = itemDao.getItemsByList(listId)
    fun getCategoriesByList(listId: String): Flow<List<String>> = itemDao.getCategoriesByList(listId)

    suspend fun insert(item: ShoppingItem) {
        itemDao.insert(item)
        // Learn from user's complete item details
        learnFromUser(item.name, item.category, item.quantity, item.unit)
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

    // Templates
    fun getAllTemplates(): Flow<List<ListTemplate>> {
        return combine(
            templateDao.getAllTemplates(),
            premiumManager.isPremium
        ) { templates, isPremium ->
            if (isPremium) {
                templates
            } else {
                templates.filter { !it.isPremium }
            }
        }
    }

    fun getFreeTemplates(): Flow<List<ListTemplate>> {
        return templateDao.getFreeTemplates()
    }

    fun getTemplatesByCategory(category: String): Flow<List<ListTemplate>> {
        return templateDao.getTemplatesByCategory(category)
    }

    suspend fun getTemplateById(id: String): ListTemplate? {
        return templateDao.getTemplateById(id)
    }

    suspend fun insertTemplates(templates: List<ListTemplate>) {
        templateDao.insertTemplates(templates)
    }

    suspend fun createListFromTemplate(template: ListTemplate, listName: String): String {
        // Create new shopping list
        val newList = ShoppingList(
            name = listName,
            createdDate = System.currentTimeMillis()
        )
        insertList(newList)

        // Add all template items to the new list
        template.items.forEach { templateItem ->
            val shoppingItem = ShoppingItem(
                listId = newList.id,
                name = templateItem.name,
                quantity = templateItem.quantity,
                unit = templateItem.unit,
                category = templateItem.category,
                notes = templateItem.notes
            )
            insert(shoppingItem)
        }

        return newList.id
    }

    // Item Pattern Learning
    suspend fun learnFromUser(itemName: String, category: String, quantity: Float = 1f, unit: String = "nos") {
        val normalizedName = itemName.lowercase().trim()
        val existing = itemPatternDao.getPattern(normalizedName)
        
        if (existing != null) {
            // Update existing pattern with increased usage count and latest preferences
            itemPatternDao.update(
                existing.copy(
                    usageCount = existing.usageCount + 1,
                    lastUsed = System.currentTimeMillis(),
                    suggestedCategory = category,
                    suggestedQuantity = quantity,
                    suggestedUnit = unit
                )
            )
        } else {
            // Create new pattern
            itemPatternDao.insert(
                ItemPattern(
                    itemName = normalizedName,
                    suggestedCategory = category,
                    suggestedQuantity = quantity,
                    suggestedUnit = unit,
                    usageCount = 1,
                    lastUsed = System.currentTimeMillis()
                )
            )
        }
    }

    suspend fun suggestCategory(itemName: String): String? {
        return suggestItemDetails(itemName)?.suggestedCategory
    }

    suspend fun suggestItemDetails(itemName: String): ItemPattern? {
        val normalizedName = itemName.lowercase().trim()
        
        // First try exact match
        itemPatternDao.getPattern(normalizedName)?.let { return it }
        
        // Try similar items (contains logic)
        val similarPatterns = itemPatternDao.findSimilarPatterns(normalizedName)
        return similarPatterns.firstOrNull()
    }

    suspend fun getSuggestedCategoryWithConfidence(itemName: String): Pair<String?, Int>? {
        val normalizedName = itemName.lowercase().trim()
        
        // Exact match has highest confidence
        itemPatternDao.getPattern(normalizedName)?.let { 
            return Pair(it.suggestedCategory, it.usageCount)
        }
        
        // Partial matches have lower confidence based on usage count
        val similarPatterns = itemPatternDao.findSimilarPatterns(normalizedName)
        val bestMatch = similarPatterns.maxByOrNull { it.usageCount }
        
        return bestMatch?.let { Pair(it.suggestedCategory, it.usageCount) }
    }

    suspend fun getSuggestedItemDetailsWithConfidence(itemName: String): Pair<ItemPattern?, Int>? {
        val normalizedName = itemName.lowercase().trim()
        
        // Exact match has highest confidence
        itemPatternDao.getPattern(normalizedName)?.let { 
            return Pair(it, it.usageCount)
        }
        
        // Partial matches have lower confidence based on usage count
        val similarPatterns = itemPatternDao.findSimilarPatterns(normalizedName)
        val bestMatch = similarPatterns.maxByOrNull { it.usageCount }
        
        return bestMatch?.let { Pair(it, it.usageCount) }
    }

    // Seed patterns from templates for immediate smart suggestions
    suspend fun seedPatternsFromTemplates() {
        // Only seed if no patterns exist yet (first launch)
        val existingPatterns = itemPatternDao.getAllPatterns()
        if (existingPatterns.isNotEmpty()) return

        // Get all templates and seed patterns from their items
        val templates = templateDao.getAllTemplates().first() // Get current value from Flow
        templates.forEach { template ->
            template.items.forEach { templateItem ->
                // Seed with usage count 1 so user patterns can override
                itemPatternDao.insert(
                    ItemPattern(
                        itemName = templateItem.name.lowercase().trim(),
                        suggestedCategory = templateItem.category,
                        suggestedQuantity = templateItem.quantity,
                        suggestedUnit = templateItem.unit,
                        usageCount = 1, // Template seeds start with count 1
                        lastUsed = System.currentTimeMillis()
                    )
                )
            }
        }
    }
}
