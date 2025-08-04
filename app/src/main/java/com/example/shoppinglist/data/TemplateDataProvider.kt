package com.example.shoppinglist.data

import com.example.shoppinglist.data.database.entities.ListTemplate
import com.example.shoppinglist.data.database.entities.TemplateItem

object TemplateDataProvider {
    
    fun getPredefinedTemplates(): List<ListTemplate> {
        return listOf(
            // FREE TEMPLATES
            ListTemplate(
                id = "weekly_essentials",
                name = "Weekly Essentials",
                description = "80% of what most families buy weekly - covers all the basics",
                category = "Weekly",
                isPremium = false,
                icon = "🛒",
                items = listOf(
                    // Essential Dairy & Eggs (5 items) - Most common dairy items
                    TemplateItem("Milk", 2f, "liters", "Dairy"),
                    TemplateItem("Eggs", 12f, "pcs", "Dairy"),
                    TemplateItem("Yogurt", 4f, "cups", "Dairy"),
                    TemplateItem("Cheese", 1f, "pack", "Dairy"),
                    TemplateItem("Butter", 1f, "pack", "Dairy"),
                    
                    // Essential Produce (8 items) - Most purchased fruits/vegetables
                    TemplateItem("Bananas", 6f, "pcs", "Produce"),
                    TemplateItem("Apples", 8f, "pcs", "Produce"),
                    TemplateItem("Onions", 3f, "pcs", "Produce"),
                    TemplateItem("Carrots", 1f, "bag", "Produce"),
                    TemplateItem("Potatoes", 2f, "kg", "Produce"),
                    TemplateItem("Tomatoes", 4f, "pcs", "Produce"),
                    TemplateItem("Lettuce", 1f, "head", "Produce"),
                    TemplateItem("Garlic", 1f, "bulb", "Produce"),
                    
                    // Essential Proteins (4 items) - Most common protein sources
                    TemplateItem("Chicken breast", 1f, "kg", "Meat"),
                    TemplateItem("Ground beef", 0.5f, "kg", "Meat"),
                    TemplateItem("Salmon", 0.5f, "kg", "Meat"),
                    TemplateItem("Deli meat", 0.3f, "kg", "Meat"),
                    
                    // Essential Carbs & Bread (4 items)
                    TemplateItem("Bread", 2f, "loaves", "Bakery"),
                    TemplateItem("Rice", 1f, "bag", "Pantry"),
                    TemplateItem("Pasta", 2f, "boxes", "Pantry"),
                    TemplateItem("Oats/Cereal", 1f, "box", "Pantry"),
                    
                    // Cooking Essentials (5 items) - Most used cooking items
                    TemplateItem("Olive oil", 1f, "bottle", "Pantry"),
                    TemplateItem("Salt", 1f, "container", "Pantry"),
                    TemplateItem("Black pepper", 1f, "container", "Pantry"),
                    TemplateItem("Pasta sauce", 2f, "jars", "Pantry"),
                    TemplateItem("Canned tomatoes", 2f, "cans", "Pantry"),
                    
                    // Household Essentials (4 items) - Most needed household items
                    TemplateItem("Toilet paper", 1f, "pack", "Household"),
                    TemplateItem("Paper towels", 1f, "pack", "Household"),
                    TemplateItem("Dish soap", 1f, "bottle", "Household"),
                    TemplateItem("Laundry detergent", 1f, "bottle", "Household")
                )
            ),

            // PREMIUM TEMPLATES
            ListTemplate(
                id = "bbq_party_15",
                name = "BBQ Party (10-15 people)",
                description = "80% of what you need for a successful BBQ party",
                category = "Party",
                isPremium = true,
                estimatedPeople = 15,
                icon = "🔥",
                items = listOf(
                    // Main BBQ Proteins (4 items) - Core BBQ meats
                    TemplateItem("Burger patties", 2f, "kg", "Meat"),
                    TemplateItem("Hot dogs", 20f, "pcs", "Meat"),
                    TemplateItem("Chicken wings", 2f, "kg", "Meat"),
                    TemplateItem("Ribs", 1.5f, "kg", "Meat"),
                    
                    // Bread & Buns (3 items)
                    TemplateItem("Hamburger buns", 16f, "pcs", "Bakery"),
                    TemplateItem("Hot dog buns", 20f, "pcs", "Bakery"),
                    TemplateItem("Garlic bread", 2f, "loaves", "Bakery"),
                    
                    // Essential Toppings (5 items) - Most wanted toppings
                    TemplateItem("Cheese slices", 24f, "pcs", "Dairy"),
                    TemplateItem("Lettuce", 2f, "heads", "Produce"),
                    TemplateItem("Tomatoes", 2f, "kg", "Produce"),
                    TemplateItem("Onions", 4f, "pcs", "Produce"),
                    TemplateItem("Pickles", 2f, "jars", "Pantry"),
                    
                    // BBQ Essentials (4 items) - Must-have sauces and fuel
                    TemplateItem("BBQ sauce", 3f, "bottles", "Pantry"),
                    TemplateItem("Ketchup", 2f, "bottles", "Pantry"),
                    TemplateItem("Mustard", 2f, "bottles", "Pantry"),
                    TemplateItem("Charcoal", 5f, "kg", "Household"),
                    
                    // Drinks & Ice (3 items)
                    TemplateItem("Sodas/Soft drinks", 24f, "cans", "Pantry"),
                    TemplateItem("Beer", 24f, "cans", "Pantry"),
                    TemplateItem("Ice", 3f, "bags", "Frozen"),
                    
                    // Party Supplies (3 items) - Essential disposables
                    TemplateItem("Paper plates", 30f, "pcs", "Household"),
                    TemplateItem("Napkins", 2f, "packs", "Household"),
                    TemplateItem("Aluminum foil", 2f, "rolls", "Household")
                )
            ),

            ListTemplate(
                id = "thanksgiving_dinner",
                name = "Thanksgiving Dinner",
                description = "Everything for traditional Thanksgiving feast (8-10 people) - covers 80% of the essentials",
                category = "Holiday",
                isPremium = true,
                estimatedPeople = 8,
                icon = "🦃",
                items = listOf(
                    // Main Course (2 items)
                    TemplateItem("Whole turkey", 6f, "kg", "Meat"),
                    TemplateItem("Turkey gravy mix", 2f, "packets", "Pantry"),
                    
                    // Essential Sides (6 items) - Most popular Thanksgiving sides
                    TemplateItem("Potatoes", 3f, "kg", "Produce"),
                    TemplateItem("Sweet potatoes", 2f, "kg", "Produce"),
                    TemplateItem("Stuffing mix", 3f, "boxes", "Pantry"),
                    TemplateItem("Cranberry sauce", 2f, "cans", "Pantry"),
                    TemplateItem("Green beans", 1f, "kg", "Produce"),
                    TemplateItem("Carrots", 1f, "kg", "Produce"),
                    
                    // Cooking Essentials (5 items)
                    TemplateItem("Butter", 1f, "kg", "Dairy"),
                    TemplateItem("Heavy cream", 2f, "liters", "Dairy"),
                    TemplateItem("Chicken broth", 3f, "cartons", "Pantry"),
                    TemplateItem("Onions", 4f, "pcs", "Produce"),
                    TemplateItem("Celery", 2f, "bunches", "Produce"),
                    
                    // Dessert Essentials (4 items)
                    TemplateItem("Pumpkin pie", 2f, "pies", "Bakery"),
                    TemplateItem("Whipped cream", 2f, "containers", "Dairy"),
                    TemplateItem("Dinner rolls", 12f, "pcs", "Bakery"),
                    TemplateItem("Apple cider", 2f, "bottles", "Pantry"),
                    
                    // Party Essentials (3 items)
                    TemplateItem("Aluminum foil", 2f, "rolls", "Household"),
                    TemplateItem("Disposable containers", 10f, "pcs", "Household"),
                    TemplateItem("Paper towels", 3f, "rolls", "Household")
                )
            ),

            ListTemplate(
                id = "baby_essentials",
                name = "Baby Essentials",
                description = "Must-have items for baby care",
                category = "Family",
                isPremium = true,
                icon = "👶",
                items = listOf(
                    TemplateItem("Diapers", 1f, "pack", "Personal Care"),
                    TemplateItem("Baby wipes", 3f, "packs", "Personal Care"),
                    TemplateItem("Baby formula", 2f, "cans", "Pantry"),
                    TemplateItem("Baby food jars", 12f, "pcs", "Pantry"),
                    TemplateItem("Baby shampoo", 1f, "bottle", "Personal Care"),
                    TemplateItem("Baby lotion", 1f, "bottle", "Personal Care"),
                    TemplateItem("Pacifiers", 2f, "pcs", "Personal Care"),
                    TemplateItem("Baby bottles", 4f, "pcs", "Household"),
                    TemplateItem("Burp cloths", 6f, "pcs", "Household")
                )
            ),

            ListTemplate(
                id = "keto_shopping",
                name = "Keto Shopping",
                description = "Low-carb, high-fat diet essentials",
                category = "Diet",
                isPremium = true,
                icon = "🥑",
                items = listOf(
                    TemplateItem("Avocados", 6f, "pcs", "Produce"),
                    TemplateItem("Salmon fillets", 0.5f, "kg", "Meat"),
                    TemplateItem("Ground beef", 1f, "kg", "Meat"),
                    TemplateItem("Eggs", 24f, "pcs", "Dairy"),
                    TemplateItem("Cheese", 0.5f, "kg", "Dairy"),
                    TemplateItem("Spinach", 1f, "bag", "Produce"),
                    TemplateItem("Broccoli", 1f, "head", "Produce"),
                    TemplateItem("Cauliflower", 1f, "head", "Produce"),
                    TemplateItem("Olive oil", 1f, "bottle", "Pantry"),
                    TemplateItem("Coconut oil", 1f, "jar", "Pantry"),
                    TemplateItem("Nuts (almonds)", 1f, "bag", "Pantry"),
                    TemplateItem("Greek yogurt", 1f, "container", "Dairy")
                )
            ),

            ListTemplate(
                id = "camping_trip",
                name = "Camping Trip",
                description = "Essential supplies for outdoor camping",
                category = "Travel",
                isPremium = true,
                estimatedPeople = 4,
                icon = "🏕️",
                items = listOf(
                    TemplateItem("Canned beans", 6f, "cans", "Pantry"),
                    TemplateItem("Instant noodles", 8f, "packs", "Pantry"),
                    TemplateItem("Trail mix", 2f, "bags", "Pantry"),
                    TemplateItem("Granola bars", 12f, "pcs", "Pantry"),
                    TemplateItem("Bottled water", 24f, "bottles", "Pantry"),
                    TemplateItem("Coffee", 1f, "bag", "Pantry"),
                    TemplateItem("Hot dogs", 8f, "pcs", "Meat"),
                    TemplateItem("Marshmallows", 1f, "bag", "Pantry"),
                    TemplateItem("Graham crackers", 1f, "box", "Pantry"),
                    TemplateItem("Chocolate bars", 4f, "pcs", "Pantry"),
                    TemplateItem("Matches", 1f, "box", "Household"),
                    TemplateItem("Aluminum foil", 1f, "roll", "Household"),
                    TemplateItem("Paper towels", 2f, "rolls", "Household"),
                    TemplateItem("Trash bags", 1f, "box", "Household")
                )
            ),

            ListTemplate(
                id = "quick_dinners_week",
                name = "Quick Dinners Week",
                description = "Everything for 7 easy 30-minute meals - covers 80% of busy weeknight cooking",
                category = "Meal Prep",
                isPremium = true,
                icon = "⚡",
                items = listOf(
                    // Quick Proteins (5 items) - Fast cooking proteins
                    TemplateItem("Chicken thighs", 1.5f, "kg", "Meat"),
                    TemplateItem("Ground beef", 1f, "kg", "Meat"),
                    TemplateItem("Salmon fillets", 0.5f, "kg", "Meat"),
                    TemplateItem("Eggs", 18f, "pcs", "Dairy"),
                    TemplateItem("Shrimp", 0.5f, "kg", "Meat"),
                    
                    // Quick Carbs (4 items) - Fast cooking starches
                    TemplateItem("Pasta", 3f, "boxes", "Pantry"),
                    TemplateItem("Rice", 2f, "bags", "Pantry"),
                    TemplateItem("Tortillas", 2f, "packs", "Bakery"),
                    TemplateItem("Instant noodles", 6f, "packs", "Pantry"),
                    
                    // Essential Vegetables (6 items) - Most versatile vegetables
                    TemplateItem("Onions", 4f, "pcs", "Produce"),
                    TemplateItem("Bell peppers", 6f, "pcs", "Produce"),
                    TemplateItem("Garlic", 2f, "bulbs", "Produce"),
                    TemplateItem("Frozen vegetables", 4f, "bags", "Frozen"),
                    TemplateItem("Spinach", 2f, "bags", "Produce"),
                    TemplateItem("Mushrooms", 1f, "pack", "Produce"),
                    
                    // Quick Cooking Essentials (7 items) - Sauces and basics for fast meals
                    TemplateItem("Pasta sauce", 4f, "jars", "Pantry"),
                    TemplateItem("Soy sauce", 1f, "bottle", "Pantry"),
                    TemplateItem("Olive oil", 1f, "bottle", "Pantry"),
                    TemplateItem("Canned tomatoes", 4f, "cans", "Pantry"),
                    TemplateItem("Chicken broth", 2f, "cartons", "Pantry"),
                    TemplateItem("Cheese", 2f, "bags", "Dairy"),
                    TemplateItem("Hot sauce", 1f, "bottle", "Pantry")
                )
            )
        )
    }
}