package com.example.shoppinglist.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppinglist.data.database.entities.ListTemplate
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplateDao {
    @Query("SELECT * FROM listtemplate ORDER BY category, name")
    fun getAllTemplates(): Flow<List<ListTemplate>>

    @Query("SELECT * FROM listtemplate WHERE isPremium = 0 ORDER BY category, name")
    fun getFreeTemplates(): Flow<List<ListTemplate>>

    @Query("SELECT * FROM listtemplate WHERE category = :category ORDER BY name")
    fun getTemplatesByCategory(category: String): Flow<List<ListTemplate>>

    @Query("SELECT * FROM listtemplate WHERE id = :id")
    suspend fun getTemplateById(id: String): ListTemplate?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTemplates(templates: List<ListTemplate>)

    @Query("SELECT COUNT(*) FROM listtemplate")
    suspend fun getTemplateCount(): Int
}