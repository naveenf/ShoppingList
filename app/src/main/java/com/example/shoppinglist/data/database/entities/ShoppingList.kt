package com.example.shoppinglist.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class ShoppingList(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val createdDate: Long = System.currentTimeMillis(),
    val color: Int? = null
)
