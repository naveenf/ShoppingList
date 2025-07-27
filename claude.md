# ShoppingList App Requirements

## Project Overview
Create a modern, elegant Android shopping list app with a focus on simplicity, usability, and Material Design 3 principles. The app features a sophisticated color palette from Huemint (forest green, mustard gold, dark navy, and off-white) that creates a premium, trustworthy shopping experience. The app should work offline-first and provide a delightful user experience through thoughtful animations and interactions.

## Core Features

### 1. Item Management
- **Add Items**
  - Floating Action Button (FAB) with "+" icon at bottom right
  - Quick add with auto-complete suggestions from previous items
  - Voice input option (microphone icon in input field)
  - Categories auto-assignment based on item name
  
- **Delete Items**
  - Swipe left to reveal red delete action with trash icon
  - Undo option via Snackbar for 3 seconds
  - Long press for multi-select mode with bulk delete
  
- **Edit Items**
  - Tap item to edit inline
  - Modify: name, quantity, notes, category

### 2. Quantity Management
- **Smart Quantity Input**
  - Default quantity: 1
  - Quick increment/decrement buttons (+/-)
  - Support for units: pcs, kg, lbs, liters, etc.
  - Smart unit suggestions based on item type
  
### 3. Item Organization
- **Categories**
  - Pre-defined categories with icons:
    - 🥬 Produce (fruits, vegetables)
    - 🥛 Dairy & Eggs
    - 🥖 Bakery
    - 🥩 Meat & Seafood
    - 🥫 Pantry
    - 🧊 Frozen
    - 🧴 Personal Care
    - 🧹 Household
    - ➕ Custom categories
  
- **Smart Sorting**
  - By category (grouped view)
  - By alphabetical order
  - By recently added
  - By store layout (future feature)

### 4. Visual Design

#### Color Scheme (Huemint 3-Color Brand Palette)
```kotlin
// Material Design 3 Color Palette with Huemint Colors
// This sophisticated palette combines dark navy, warm gold, forest green, and soft off-white
// Creates a premium, organic feel - like a high-end grocery store or farmer's market

// Light Theme
primary = Color(0xFF3F6B4E)       // Forest Green - Primary actions, FAB
onPrimary = Color(0xFFF4F0EF)     // Off-white - Text on primary
secondary = Color(0xFFB89F4E)     // Mustard Gold - Accents, highlights
onSecondary = Color(0xFF2C2E3B)   // Dark Navy - Text on secondary
background = Color(0xFFF4F0EF)    // Off-white - Main background
onBackground = Color(0xFF2C2E3B)  // Dark Navy - Text on background
surface = Color(0xFFFFFFFF)       // Pure white - Cards, sheets
onSurface = Color(0xFF2C2E3B)     // Dark Navy - Text on cards
error = Color(0xFFB3261E)         // Material Red - Errors
tertiary = Color(0xFF2C2E3B)      // Dark Navy - Headers, emphasis

// Dark Theme
primaryDark = Color(0xFF4F7B5E)   // Lighter Forest Green
onPrimaryDark = Color(0xFF2C2E3B) 
secondaryDark = Color(0xFFD4B85A) // Brighter Gold
backgroundDark = Color(0xFF2C2E3B) // Dark Navy background
surfaceDark = Color(0xFF383A47)   // Slightly lighter navy for cards
onSurfaceDark = Color(0xFFF4F0EF) // Off-white text

// Usage Examples:
// - FAB: primary (Forest Green) with onPrimary icon
// - Category headers: tertiary (Dark Navy) on background
// - Selected items: secondary (Gold) highlight
// - App bar: surface with onSurface text
// - Delete swipe: error color background
```

#### Color Application Guide
- **Primary Actions**: Forest green (#3F6B4E) for FAB, primary buttons, selected states
- **Accent Elements**: Mustard gold (#B89F4E) for badges, highlights, important info
- **Text & Icons**: Dark navy (#2C2E3B) for main content, headers
- **Backgrounds**: Off-white (#F4F0EF) for main background, pure white for cards
- **Interactive States**: 
  - Pressed: 12% opacity overlay
  - Hover: 8% opacity overlay
  - Selected: Gold accent with 20% opacity

#### Typography
- Headlines: Roboto Medium 24sp
- Body: Roboto Regular 16sp
- Captions: Roboto Regular 14sp

#### Implementing the Huemint Color Theme
```kotlin
// Theme.kt
@Composable
fun ShoppingListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF4F7B5E),
            onPrimary = Color(0xFF2C2E3B),
            secondary = Color(0xFFD4B85A),
            onSecondary = Color(0xFF2C2E3B),
            background = Color(0xFF2C2E3B),
            onBackground = Color(0xFFF4F0EF),
            surface = Color(0xFF383A47),
            onSurface = Color(0xFFF4F0EF)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF3F6B4E),
            onPrimary = Color(0xFFF4F0EF),
            secondary = Color(0xFFB89F4E),
            onSecondary = Color(0xFF2C2E3B),
            background = Color(0xFFF4F0EF),
            onBackground = Color(0xFF2C2E3B),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF2C2E3B)
        )
    }
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

#### Visual Hierarchy with Colors
1. **Primary Focus**: Forest green FAB draws attention to main action
2. **Secondary Info**: Gold badges for quantities, special offers
3. **Content**: Dark navy text ensures excellent readability
4. **Subtle Backgrounds**: Off-white creates calm, uncluttered feel

#### Category Color Coding (Optional)
While maintaining the brand palette, use opacity variations:
- Produce: Forest green at 20% opacity
- Dairy: Gold at 15% opacity
- Meat: Navy at 10% opacity
- Other categories: Rotating between the three at low opacity

#### Layout Principles
- 16dp standard margins
- 8dp grid system
- Card-based list items with subtle elevation (2dp)
- Rounded corners (12dp radius)

#### Specific UI Element Styling
```kotlin
// Shopping Item Card
Card(
    colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.surface, // White in light mode
    ),
    elevation = CardDefaults.cardElevation(2.dp)
)

// FAB
FloatingActionButton(
    containerColor = MaterialTheme.colorScheme.primary, // Forest Green
    contentColor = MaterialTheme.colorScheme.onPrimary  // Off-white icon
)

// Category Headers
Text(
    text = categoryName,
    color = MaterialTheme.colorScheme.tertiary, // Dark Navy
    style = MaterialTheme.typography.titleMedium,
    modifier = Modifier.background(
        MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f) // Subtle gold tint
    )
)

// Checked Item Style
if (item.isChecked) {
    Text(
        modifier = Modifier.alpha(0.6f),
        textDecoration = TextDecoration.LineThrough,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    )
}

// Delete Action Background
Box(
    modifier = Modifier
        .background(MaterialTheme.colorScheme.error) // Red for delete
        .fillMaxSize()
)
```

### 5. User Experience Features

#### Micro-interactions
- **Check Animation**: Item fades and strikes through when marked complete
- **Add Animation**: New items slide in from bottom with subtle bounce
- **Delete Animation**: Item slides out with red background reveal
- **FAB Animation**: Morphs into input field when tapped

#### Empty States
- Friendly illustration using brand colors (gold accent on forest green)
- Message: "Your list is empty! Tap + to add your first item"
- Text in dark navy (#2C2E3B) on off-white background
- Suggested items based on day/time (e.g., "Morning essentials" at 7 AM)

#### Smart Features
- **Auto-suggestions**: Based on shopping history
- **Quick Add**: Long press FAB to add common items quickly
- **Voice Input**: "Add milk, 2 liters"
- **Barcode Scanner**: (Optional) Scan to add items

### 6. Data Architecture

#### Local Storage (Room Database)
```kotlin
// Item Entity
@Entity
data class ShoppingItem(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val quantity: Float = 1f,
    val unit: String = "pcs",
    val category: String,
    val isChecked: Boolean = false,
    val notes: String? = null,
    val addedDate: Long = System.currentTimeMillis(),
    val lastModified: Long = System.currentTimeMillis()
)

// Shopping List Entity
@Entity
data class ShoppingList(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val createdDate: Long = System.currentTimeMillis(),
    val color: Int? = null
)
```

### 7. Screen Specifications

#### Main List Screen
- **App Bar**
  - Title: "Shopping List" (or custom list name)
  - Search icon (right)
  - Menu icon (right) - Settings, Sort, Share
  
- **List View**
  - RecyclerView with items grouped by category
  - Sticky headers for categories
  - Pull-to-refresh gesture
  
- **Bottom Area**
  - FAB for adding items
  - Optional: Mini stats (3 items, $25 estimated)

#### Add/Edit Item Dialog
- Material Design 3 bottom sheet
- Fields: Name, Quantity, Unit, Category, Notes
- Recent items section for quick re-add

#### Settings Screen
- Theme toggle (Light/Dark/System)
- Default units preference
- Categories management
- Clear history option
- About section

### 8. Advanced Features (Phase 2)

- **Multiple Lists**: Work, Home, Party shopping lists
- **Share Lists**: Generate shareable link or QR code
- **Price Tracking**: Add prices, see total estimate
- **Store Mode**: Reorder items by store layout
- **Recipes Integration**: Import ingredients from recipes
- **Widget**: Home screen widget for quick view/add

### 9. Technical Requirements

#### Minimum SDK
- minSdk: 24 (Android 7.0)
- targetSdk: 35 (Android 15)
- Kotlin version: Latest stable
- Gradle version: Latest stable

#### Dependencies
```gradle
// Core
implementation 'androidx.core:core-ktx:latest'
implementation 'androidx.lifecycle:lifecycle-runtime-ktx:latest'
implementation 'androidx.activity:activity-compose:latest'

// UI
implementation 'androidx.compose.ui:ui:latest'
implementation 'androidx.compose.material3:material3:latest'
implementation 'androidx.compose.ui:ui-tooling-preview:latest'

// Database
implementation 'androidx.room:room-runtime:latest'
implementation 'androidx.room:room-ktx:latest'
kapt 'androidx.room:room-compiler:latest'

// Navigation
implementation 'androidx.navigation:navigation-compose:latest'
```

### 10. Accessibility
- Content descriptions for all icons
- Minimum touch target: 48dp
- High contrast mode support
- Screen reader optimization
- Keyboard navigation support

### 11. Performance Goals
- App launch: < 2 seconds
- List scrolling: 60 FPS
- Add item: < 500ms response
- Database operations: Background thread
- APK size: < 10MB

### 12. Testing Requirements
- Unit tests for ViewModels
- Integration tests for database
- UI tests for critical user flows
- Manual testing on different screen sizes

## File Structure
```
app/src/main/java/com/yourname/shoppinglist/
├── data/
│   ├── database/
│   │   ├── ShoppingDatabase.kt
│   │   ├── ItemDao.kt
│   │   └── entities/
│   └── repository/
│       └── ShoppingRepository.kt
├── ui/
│   ├── screens/
│   │   ├── MainListScreen.kt
│   │   ├── AddItemScreen.kt
│   │   └── SettingsScreen.kt
│   ├── components/
│   │   ├── ShoppingItemCard.kt
│   │   ├── CategoryHeader.kt
│   │   └── EmptyState.kt
│   └── theme/
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
├── viewmodel/
│   └── ShoppingViewModel.kt
└── MainActivity.kt
```

## Success Metrics
- User can add item in < 3 taps
- Zero crashes in first week
- 4.0+ star rating goal
- 70% user retention after 1 week
- < 2MB storage usage for 100 items

## Development Phases
1. **Phase 1 (MVP)**: Core features - add, edit, delete, check items
2. **Phase 2**: Categories, search, dark mode
3. **Phase 3**: Multiple lists, sharing
4. **Phase 4**: Advanced features (prices, barcode, widgets)

## Design Inspiration
- Google Keep (simplicity)
- Todoist (organization)
- Any.do (animations)
- Bring! (visual appeal)
- Huemint palette (sophisticated color harmony)

## Color Psychology & Brand Identity
The Huemint 3-color palette creates a premium, trustworthy shopping experience:
- **Forest Green (#3F6B4E)**: Evokes freshness, nature, and healthy choices
- **Mustard Gold (#B89F4E)**: Adds warmth, energy, and draws attention to important elements
- **Dark Navy (#2C2E3B)**: Provides sophistication, reliability, and excellent readability
- **Off-white (#F4F0EF)**: Creates breathing room and a clean, modern feel

This color combination stands out from typical shopping apps while maintaining professionalism and accessibility.

Remember: The goal is to create an app that users will love to use daily. Focus on speed, simplicity, and delight in every interaction.