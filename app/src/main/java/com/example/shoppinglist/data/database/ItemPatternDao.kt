package com.example.shoppinglist.data.database

import androidx.room.*
import com.example.shoppinglist.data.database.entities.ItemPattern
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemPatternDao {
    @Query("SELECT * FROM item_patterns WHERE itemName = :itemName")
    suspend fun getPattern(itemName: String): ItemPattern?
    
    @Query("SELECT * FROM item_patterns ORDER BY usageCount DESC, lastUsed DESC")
    suspend fun getAllPatterns(): List<ItemPattern>
    
    @Query("SELECT * FROM item_patterns WHERE (:searchTerm LIKE '%' || itemName || '%' AND LENGTH(itemName) >= 3) OR (itemName LIKE '%' || :searchTerm || '%' AND LENGTH(:searchTerm) >= 3) ORDER BY usageCount DESC, LENGTH(itemName) DESC LIMIT 5")
    suspend fun findSimilarPatterns(searchTerm: String): List<ItemPattern>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pattern: ItemPattern)
    
    @Update
    suspend fun update(pattern: ItemPattern)
    
    @Query("DELETE FROM item_patterns WHERE itemName = :itemName")
    suspend fun deletePattern(itemName: String)
    
    @Query("DELETE FROM item_patterns")
    suspend fun clearAllPatterns()
}