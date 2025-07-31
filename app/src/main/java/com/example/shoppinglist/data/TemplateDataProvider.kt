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
                description = "Basic items for the week",
                category = "Weekly",
                isPremium = false,
                icon = "🛒",
                items = listOf(
                    TemplateItem("Milk", 1f, "liter", "Dairy"),
                    TemplateItem("Bread", 1f, "loaf", "Bakery"),
                    TemplateItem("Eggs", 12f, "pcs", "Dairy"),
                    TemplateItem("Bananas", 6f, "pcs", "Produce"),
                    TemplateItem("Apples", 6f, "pcs", "Produce"),
                    TemplateItem("Rice", 1f, "kg", "Pantry"),
                    TemplateItem("Chicken breast", 0.5f, "kg", "Meat")
                )
            ),

            // PREMIUM TEMPLATES
            ListTemplate(
                id = "bbq_party_15",
                name = "BBQ Party (15 people)",
                description = "Complete BBQ setup for 15 guests",
                category = "Party",
                isPremium = true,
                estimatedPeople = 15,
                icon = "🔥",
                items = listOf(
                    TemplateItem("Burger patties", 2f, "kg", "Meat"),
                    TemplateItem("Hot dogs", 20f, "pcs", "Meat"),
                    TemplateItem("Hamburger buns", 15f, "pcs", "Bakery"),
                    TemplateItem("Hot dog buns", 20f, "pcs", "Bakery"),
                    TemplateItem("Cheese slices", 20f, "pcs", "Dairy"),
                    TemplateItem("Lettuce", 2f, "heads", "Produce"),
                    TemplateItem("Tomatoes", 1f, "kg", "Produce"),
                    TemplateItem("Onions", 3f, "pcs", "Produce"),
                    TemplateItem("Ketchup", 2f, "bottles", "Pantry"),
                    TemplateItem("Mustard", 2f, "bottles", "Pantry"),
                    TemplateItem("BBQ sauce", 1f, "bottle", "Pantry"),
                    TemplateItem("Charcoal", 5f, "kg", "Household"),
                    TemplateItem("Sodas", 24f, "cans", "Pantry"),
                    TemplateItem("Beer", 24f, "cans", "Pantry"),
                    TemplateItem("Ice", 2f, "bags", "Frozen"),
                    TemplateItem("Paper plates", 30f, "pcs", "Household"),
                    TemplateItem("Aluminum foil", 1f, "roll", "Household")
                )
            ),

            ListTemplate(
                id = "thanksgiving_dinner",
                name = "Thanksgiving Dinner",
                description = "Traditional Thanksgiving feast ingredients",
                category = "Holiday",
                isPremium = true,
                estimatedPeople = 8,
                icon = "🦃",
                items = listOf(
                    TemplateItem("Whole turkey", 5f, "kg", "Meat"),
                    TemplateItem("Potatoes", 2f, "kg", "Produce"),
                    TemplateItem("Sweet potatoes", 1f, "kg", "Produce"),
                    TemplateItem("Cranberries", 0.5f, "kg", "Produce"),
                    TemplateItem("Stuffing mix", 2f, "boxes", "Pantry"),
                    TemplateItem("Butter", 0.5f, "kg", "Dairy"),
                    TemplateItem("Heavy cream", 1f, "liter", "Dairy"),
                    TemplateItem("Green beans", 0.5f, "kg", "Produce"),
                    TemplateItem("Carrots", 0.5f, "kg", "Produce"),
                    TemplateItem("Celery", 1f, "bunch", "Produce"),
                    TemplateItem("Pie crust", 2f, "pcs", "Bakery"),
                    TemplateItem("Pumpkin puree", 1f, "can", "Pantry"),
                    TemplateItem("Pecans", 1f, "cup", "Pantry"),
                    TemplateItem("Vanilla extract", 1f, "bottle", "Pantry")
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
                description = "7 days of easy 30-minute meals",
                category = "Meal Prep",
                isPremium = true,
                icon = "⚡",
                items = listOf(
                    TemplateItem("Pasta", 2f, "boxes", "Pantry"),
                    TemplateItem("Pasta sauce", 3f, "jars", "Pantry"),
                    TemplateItem("Ground turkey", 1f, "kg", "Meat"),
                    TemplateItem("Chicken thighs", 1f, "kg", "Meat"),
                    TemplateItem("Frozen vegetables", 3f, "bags", "Frozen"),
                    TemplateItem("Rice", 1f, "bag", "Pantry"),
                    TemplateItem("Eggs", 12f, "pcs", "Dairy"),
                    TemplateItem("Shredded cheese", 2f, "bags", "Dairy"),
                    TemplateItem("Tortillas", 1f, "pack", "Bakery"),
                    TemplateItem("Canned tomatoes", 4f, "cans", "Pantry"),
                    TemplateItem("Onions", 3f, "pcs", "Produce"),
                    TemplateItem("Garlic", 1f, "bulb", "Produce"),
                    TemplateItem("Bell peppers", 4f, "pcs", "Produce")
                )
            )
        )
    }
}