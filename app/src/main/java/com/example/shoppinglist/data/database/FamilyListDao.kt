package com.example.shoppinglist.data.database

import androidx.room.*
import com.example.shoppinglist.data.database.entities.FamilyListLocal
import kotlinx.coroutines.flow.Flow

@Dao
interface FamilyListDao {
    
    @Query("SELECT * FROM family_lists WHERE isActive = 1")
    fun getAllActiveFamilyLists(): Flow<List<FamilyListLocal>>
    
    @Query("SELECT * FROM family_lists WHERE listId = :listId")
    suspend fun getFamilyListById(listId: String): FamilyListLocal?
    
    @Query("SELECT * FROM family_lists WHERE shareCode = :shareCode")
    suspend fun getFamilyListByShareCode(shareCode: String): FamilyListLocal?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFamilyList(familyList: FamilyListLocal)
    
    @Update
    suspend fun updateFamilyList(familyList: FamilyListLocal)
    
    @Query("UPDATE family_lists SET memberCount = :memberCount, lastSyncTime = :lastSyncTime WHERE listId = :listId")
    suspend fun updateSyncInfo(listId: String, memberCount: Int, lastSyncTime: Long)
    
    @Query("UPDATE family_lists SET isActive = 0 WHERE listId = :listId")
    suspend fun deactivateFamilyList(listId: String)
    
    @Query("DELETE FROM family_lists WHERE listId = :listId")
    suspend fun deleteFamilyList(listId: String)
    
    @Query("SELECT COUNT(*) FROM family_lists WHERE isActive = 1")
    suspend fun getActiveFamilyListCount(): Int
    
    @Query("SELECT shareCode FROM family_lists WHERE listId = :listId")
    suspend fun getShareCodeForList(listId: String): String?
}