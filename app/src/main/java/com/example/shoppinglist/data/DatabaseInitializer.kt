package com.example.shoppinglist.data

import com.example.shoppinglist.data.database.PredefinedItemDao
import com.example.shoppinglist.data.database.entities.PredefinedItem

object DatabaseInitializer {
    
    suspend fun populatePredefinedItems(predefinedItemDao: PredefinedItemDao) {
        // Check if already populated
        if (predefinedItemDao.getItemCount() > 0) return
        
        val predefinedItems = listOf(
            // Dairy & Eggs
            PredefinedItem(1, "milk", "Dairy & Eggs", "liters", "dairy milk fresh"),
            PredefinedItem(2, "cheese", "Dairy & Eggs", "gm", "dairy cheese cheddar"),
            PredefinedItem(3, "butter", "Dairy & Eggs", "gm", "dairy butter margarine"),
            PredefinedItem(4, "eggs", "Dairy & Eggs", "nos", "protein eggs chicken"),
            PredefinedItem(5, "yogurt", "Dairy & Eggs", "gm", "dairy yogurt curd"),
            
            // Produce
            PredefinedItem(6, "rice", "Pantry", "kg", "staple rice basmati"),
            PredefinedItem(7, "bread", "Bakery", "nos", "bakery bread loaf"),
            PredefinedItem(8, "potatoes", "Produce", "kg", "vegetable potato"),
            PredefinedItem(9, "tomatoes", "Produce", "kg", "vegetable tomato red"),
            PredefinedItem(10, "onions", "Produce", "kg", "vegetable onion"),
            
            // Meat & Seafood
            PredefinedItem(11, "chicken", "Meat & Seafood", "kg", "meat chicken protein"),
            PredefinedItem(12, "ham", "Meat & Seafood", "gm", "meat ham pork"),
            PredefinedItem(13, "fish", "Meat & Seafood", "kg", "seafish fish protein"),
            
            // Personal Care
            PredefinedItem(14, "toothpaste", "Personal Care", "nos", "hygiene dental tooth"),
            PredefinedItem(15, "shampoo", "Personal Care", "nos", "hygiene hair wash"),
            PredefinedItem(16, "soap", "Personal Care", "nos", "hygiene soap bath"),
            
            // Household
            PredefinedItem(17, "toilet paper", "Household", "nos", "hygiene paper tissue"),
            PredefinedItem(18, "detergent", "Household", "nos", "cleaning wash clothes"),
            
            // Pantry
            PredefinedItem(19, "salt", "Pantry", "gm", "spice salt cooking"),
            PredefinedItem(20, "sugar", "Pantry", "gm", "sweet sugar cooking"),
            PredefinedItem(21, "oil", "Pantry", "liters", "cooking oil kitchen"),
            PredefinedItem(22, "pasta", "Pantry", "gm", "carbs pasta italian"),
            
            // Beverages
            PredefinedItem(23, "water", "Beverages", "liters", "drink water mineral"),
            PredefinedItem(24, "juice", "Beverages", "liters", "drink juice fruit"),
            PredefinedItem(25, "tea", "Beverages", "gm", "drink tea leaves"),
            PredefinedItem(26, "coffee", "Beverages", "gm", "drink coffee beans"),
            
            // More Produce
            PredefinedItem(27, "lettuce", "Produce", "nos", "vegetable lettuce salad"),
            PredefinedItem(28, "cucumber", "Produce", "kg", "vegetable cucumber fresh"),
            PredefinedItem(29, "carrots", "Produce", "kg", "vegetable carrot orange"),
            PredefinedItem(30, "apples", "Produce", "kg", "fruit apple red green"),
            PredefinedItem(31, "bananas", "Produce", "kg", "fruit banana yellow"),
            
            // Condiments
            PredefinedItem(32, "ketchup", "Pantry", "nos", "sauce tomato condiment"),
            PredefinedItem(33, "mayonnaise", "Pantry", "nos", "sauce mayo condiment"),
            
            // Frozen
            PredefinedItem(34, "ice cream", "Frozen", "nos", "dessert frozen sweet"),
            PredefinedItem(35, "frozen peas", "Frozen", "gm", "vegetable frozen peas")
        )
        
        predefinedItemDao.insertPredefinedItems(predefinedItems)
    }
}