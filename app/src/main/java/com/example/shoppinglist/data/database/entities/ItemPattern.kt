package com.example.shoppinglist.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_patterns")
data class ItemPattern(
    @PrimaryKey val itemName: String,
    val suggestedCategory: String,
    val suggestedQuantity: Float = 1f,
    val suggestedUnit: String = "nos",
    val usageCount: Int = 1,
    val lastUsed: Long = System.currentTimeMillis()
)