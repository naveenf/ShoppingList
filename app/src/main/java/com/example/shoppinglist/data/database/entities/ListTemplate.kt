package com.example.shoppinglist.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListTemplate(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val category: String, // e.g., "Party", "Weekly", "Special Diet", "Travel"
    val isPremium: Boolean = false,
    val estimatedPeople: Int? = null, // For party templates
    val icon: String? = null, // Unicode emoji or icon name
    val items: List<TemplateItem> // Will be stored as JSON
)

data class TemplateItem(
    val name: String,
    val quantity: Float = 1f,
    val unit: String = "nos",
    val category: String,
    val notes: String? = null
)