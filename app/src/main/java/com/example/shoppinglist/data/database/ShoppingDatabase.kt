package com.example.shoppinglist.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shoppinglist.data.database.entities.ListTemplate
import com.example.shoppinglist.data.database.entities.PredefinedItem
import com.example.shoppinglist.data.database.entities.ShoppingItem
import com.example.shoppinglist.data.database.entities.ShoppingList
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [ShoppingItem::class, ShoppingList::class, PredefinedItem::class, ListTemplate::class], 
    version = 4, 
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun predefinedItemDao(): PredefinedItemDao
    abstract fun templateDao(): TemplateDao

    companion object {
        @Volatile
        private var INSTANCE: ShoppingDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ShoppingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    "shopping_database_v4"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
