package com.example.shoppinglist.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglist.data.database.entities.ShoppingItem
import com.example.shoppinglist.data.database.entities.ShoppingList
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM shoppingitem WHERE listId = :listId ORDER BY category, addedDate DESC")
    fun getItemsByList(listId: String): Flow<List<ShoppingItem>>

    @Query("SELECT * FROM shoppingitem WHERE listId = :listId AND category = :category ORDER BY addedDate DESC")
    fun getItemsByListAndCategory(listId: String, category: String): Flow<List<ShoppingItem>>

    @Query("SELECT DISTINCT category FROM shoppingitem WHERE listId = :listId ORDER BY category")
    fun getCategoriesByList(listId: String): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShoppingItem)

    @Update
    suspend fun update(item: ShoppingItem)

    @Delete
    suspend fun delete(item: ShoppingItem)

    @Query("DELETE FROM shoppingitem WHERE id = :itemId")
    suspend fun deleteById(itemId: String)

    @Query("SELECT * FROM shoppingitem WHERE id = :itemId")
    suspend fun getItemById(itemId: String): ShoppingItem?

    // Shopping List operations
    @Query("SELECT * FROM shoppinglist ORDER BY createdDate DESC")
    fun getAllLists(): Flow<List<ShoppingList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: ShoppingList)

    @Update
    suspend fun updateList(list: ShoppingList)

    @Delete
    suspend fun deleteList(list: ShoppingList)

    @Query("SELECT * FROM shoppinglist WHERE id = :listId")
    suspend fun getListById(listId: String): ShoppingList?

    @Query("SELECT COUNT(*) FROM shoppingitem WHERE listId = :listId")
    suspend fun getItemCountForList(listId: String): Int

    @Query("SELECT COUNT(*) FROM shoppingitem WHERE listId = :listId AND isChecked = 1")
    suspend fun getCheckedItemCountForList(listId: String): Int
}
