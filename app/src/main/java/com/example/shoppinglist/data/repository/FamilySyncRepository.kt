package com.example.shoppinglist.data.repository

import android.content.Context
import com.example.shoppinglist.data.database.FamilyListDao
import com.example.shoppinglist.data.database.entities.FamilyListLocal
import com.example.shoppinglist.data.database.entities.ShoppingItem
import com.example.shoppinglist.data.database.entities.ShoppingList
import com.example.shoppinglist.data.database.entities.SyncStatus
import com.example.shoppinglist.utils.DeviceUtils
import kotlinx.coroutines.flow.Flow

class FamilySyncRepository(
    private val familyListDao: FamilyListDao,
    private val context: Context
) {
    
    /**
     * Get all active family lists
     */
    fun getAllFamilyLists(): Flow<List<FamilyListLocal>> {
        return familyListDao.getAllActiveFamilyLists()
    }
    
    /**
     * Create a new family list with a share code
     */
    suspend fun createFamilyList(list: ShoppingList): String {
        val shareCode = DeviceUtils.generateFamilyShareCode()
        
        val familyList = FamilyListLocal(
            listId = list.id,
            shareCode = shareCode,
            isOwner = true,
            memberCount = 1,
            lastSyncTime = System.currentTimeMillis()
        )
        
        familyListDao.insertFamilyList(familyList)
        return shareCode
    }
    
    /**
     * Join an existing family list using share code
     */
    suspend fun joinFamilyList(shareCode: String): Result<String> {
        return try {
            if (!DeviceUtils.isValidFamilyCode(shareCode)) {
                return Result.failure(Exception("Invalid family code format"))
            }
            
            // In Phase 1, we just validate the code format and store it locally
            // In Phase 2, this will connect to Firebase to get the actual list data
            
            // For now, return success with a placeholder list ID
            // This will be replaced with actual Firebase integration
            val listId = "family_${shareCode}_${System.currentTimeMillis()}"
            
            val familyList = FamilyListLocal(
                listId = listId,
                shareCode = shareCode,
                isOwner = false,
                memberCount = 1, // Will be updated from Firebase
                lastSyncTime = System.currentTimeMillis()
            )
            
            familyListDao.insertFamilyList(familyList)
            Result.success(listId)
            
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Get family list by list ID
     */
    suspend fun getFamilyList(listId: String): FamilyListLocal? {
        return familyListDao.getFamilyListById(listId)
    }
    
    /**
     * Get share code for a list
     */
    suspend fun getShareCode(listId: String): String? {
        return familyListDao.getShareCodeForList(listId)
    }
    
    /**
     * Check if a list is a family list
     */
    suspend fun isFamilyList(listId: String): Boolean {
        return familyListDao.getFamilyListById(listId) != null
    }
    
    /**
     * Leave a family list
     */
    suspend fun leaveFamilyList(listId: String) {
        familyListDao.deactivateFamilyList(listId)
    }
    
    /**
     * Update sync status for items
     */
    fun updateItemSyncStatus(item: ShoppingItem, status: SyncStatus): ShoppingItem {
        return item.copy(
            syncStatus = status,
            lastModified = System.currentTimeMillis(),
            deviceId = DeviceUtils.getDeviceId(context)
        )
    }
    
    /**
     * Prepare item for family sync
     */
    fun prepareItemForSync(item: ShoppingItem, familyCode: String?): ShoppingItem {
        return item.copy(
            deviceId = DeviceUtils.getDeviceId(context),
            familyCode = familyCode,
            syncStatus = if (familyCode != null) SyncStatus.PENDING else SyncStatus.LOCAL_ONLY,
            lastModified = System.currentTimeMillis()
        )
    }
}