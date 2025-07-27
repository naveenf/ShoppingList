package com.example.shoppinglist.data

import com.example.shoppinglist.data.database.PredefinedItemDao
import com.example.shoppinglist.data.database.entities.PredefinedItem

object DatabaseInitializer {
    
    suspend fun populatePredefinedItems(predefinedItemDao: PredefinedItemDao) {
        // Check if already populated
        if (predefinedItemDao.getItemCount() > 0) return
        
        val predefinedItems = listOf(
            // Dairy & Eggs
            PredefinedItem(1, "Milk", "Dairy & Eggs", "liters", "dairy milk fresh"),
            PredefinedItem(2, "Cheese", "Dairy & Eggs", "gm", "dairy cheese cheddar"),
            PredefinedItem(3, "Butter", "Dairy & Eggs", "gm", "dairy butter margarine"),
            PredefinedItem(4, "Eggs", "Dairy & Eggs", "nos", "protein eggs chicken"),
            PredefinedItem(5, "Yogurt", "Dairy & Eggs", "gm", "dairy yogurt curd"),
            
            // Produce
            PredefinedItem(6, "Rice", "Pantry", "kg", "staple rice basmati"),
            PredefinedItem(7, "Bread", "Bakery", "nos", "bakery bread loaf"),
            PredefinedItem(8, "Potatoes", "Produce", "kg", "vegetable potato"),
            PredefinedItem(9, "Tomatoes", "Produce", "kg", "vegetable tomato red"),
            PredefinedItem(10, "Onions", "Produce", "kg", "vegetable onion"),
            
            // Meat & Seafood
            PredefinedItem(11, "Chicken", "Meat & Seafood", "kg", "meat chicken protein"),
            PredefinedItem(12, "Ham", "Meat & Seafood", "gm", "meat ham pork"),
            PredefinedItem(13, "Fish", "Meat & Seafood", "kg", "seafish fish protein"),
            
            // Personal Care
            PredefinedItem(14, "Toothpaste", "Personal Care", "nos", "hygiene dental tooth"),
            PredefinedItem(15, "Shampoo", "Personal Care", "nos", "hygiene hair wash"),
            PredefinedItem(16, "Soap", "Personal Care", "nos", "hygiene soap bath"),
            
            // Household
            PredefinedItem(17, "Toilet Paper", "Household", "nos", "hygiene paper tissue"),
            PredefinedItem(18, "Detergent", "Household", "nos", "cleaning wash clothes"),
            
            // Pantry
            PredefinedItem(19, "Salt", "Pantry", "gm", "spice salt cooking"),
            PredefinedItem(20, "Sugar", "Pantry", "gm", "sweet sugar cooking"),
            PredefinedItem(21, "Oil", "Pantry", "liters", "cooking oil kitchen"),
            PredefinedItem(22, "Pasta", "Pantry", "gm", "carbs pasta italian"),
            
            // Beverages
            PredefinedItem(23, "Water", "Beverages", "liters", "drink water mineral"),
            PredefinedItem(24, "Juice", "Beverages", "liters", "drink juice fruit"),
            PredefinedItem(25, "Tea", "Beverages", "gm", "drink tea leaves"),
            PredefinedItem(26, "Coffee", "Beverages", "gm", "drink coffee beans"),
            
            // More Produce
            PredefinedItem(27, "Lettuce", "Produce", "nos", "vegetable lettuce salad"),
            PredefinedItem(28, "Cucumber", "Produce", "kg", "vegetable cucumber fresh"),
            PredefinedItem(29, "Carrots", "Produce", "kg", "vegetable carrot orange"),
            PredefinedItem(30, "Apples", "Produce", "kg", "fruit apple red green"),
            PredefinedItem(31, "Bananas", "Produce", "kg", "fruit banana yellow"),
            
            // Condiments
            PredefinedItem(32, "Ketchup", "Pantry", "nos", "sauce tomato condiment"),
            PredefinedItem(33, "Mayonnaise", "Pantry", "nos", "sauce mayo condiment"),
            
            // Frozen
            PredefinedItem(34, "Ice Cream", "Frozen", "nos", "dessert frozen sweet"),
            PredefinedItem(35, "Frozen Peas", "Frozen", "gm", "vegetable frozen peas")
        )
        
        predefinedItemDao.insertPredefinedItems(predefinedItems)
    }
}