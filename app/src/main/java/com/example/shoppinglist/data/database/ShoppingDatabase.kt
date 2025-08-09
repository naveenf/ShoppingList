package com.example.shoppinglist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shoppinglist.data.database.entities.ItemPattern
import com.example.shoppinglist.data.database.entities.ListTemplate
import com.example.shoppinglist.data.database.entities.PredefinedItem
import com.example.shoppinglist.data.database.entities.ShoppingItem
import com.example.shoppinglist.data.database.entities.ShoppingList
import com.example.shoppinglist.data.database.entities.FamilyListLocal
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [ShoppingItem::class, ShoppingList::class, PredefinedItem::class, ListTemplate::class, ItemPattern::class, FamilyListLocal::class], 
    version = 7, 
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun predefinedItemDao(): PredefinedItemDao
    abstract fun templateDao(): TemplateDao
    abstract fun itemPatternDao(): ItemPatternDao
    abstract fun familyListDao(): FamilyListDao

    companion object {
        @Volatile
        private var INSTANCE: ShoppingDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ShoppingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    "shopping_database_v7"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
