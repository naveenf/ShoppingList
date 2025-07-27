package com.example.shoppinglist.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinglist.data.database.entities.PredefinedItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PredefinedItemDao {
    @Query("SELECT * FROM PredefinedItem WHERE name LIKE '%' || :query || '%' OR searchKeywords LIKE '%' || :query || '%' ORDER BY name ASC LIMIT 10")
    fun searchItems(query: String): Flow<List<PredefinedItem>>

    @Query("SELECT * FROM PredefinedItem ORDER BY name ASC")
    fun getAllItems(): Flow<List<PredefinedItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPredefinedItems(items: List<PredefinedItem>)

    @Query("SELECT COUNT(*) FROM PredefinedItem")
    suspend fun getItemCount(): Int
}