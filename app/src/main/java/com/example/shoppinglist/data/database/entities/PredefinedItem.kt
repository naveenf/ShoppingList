package com.example.shoppinglist.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PredefinedItem(
    @PrimaryKey val id: Int,
    val name: String,
    val category: String,
    val defaultUnit: String = "nos",
    val searchKeywords: String = ""
)