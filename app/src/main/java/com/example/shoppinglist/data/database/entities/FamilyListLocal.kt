package com.example.shoppinglist.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "family_lists")
data class FamilyListLocal(
    @PrimaryKey val listId: String,
    val shareCode: String,
    val isOwner: Boolean,
    val memberCount: Int,
    val lastSyncTime: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val isActive: Boolean = true
)