package com.example.shoppinglist.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class ShoppingItem(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val listId: String,
    val name: String,
    val quantity: Float = 1f,
    val unit: String = "nos",
    val category: String,
    val isChecked: Boolean = false,
    val notes: String? = null,
    val addedDate: Long = System.currentTimeMillis(),
    val lastModified: Long = System.currentTimeMillis(),
    
    // Family sync fields
    val deviceId: String = "", // Will be populated by repository
    val syncStatus: SyncStatus = SyncStatus.LOCAL_ONLY,
    val familyCode: String? = null
)