package com.example.shoppinglist.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.shoppinglist.ui.theme.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.themeDataStore: DataStore<Preferences> by preferencesDataStore(name = "theme_settings")

class ThemeManager(private val context: Context) {
    
    companion object {
        private val SELECTED_THEME = stringPreferencesKey("selected_theme")
    }
    
    val selectedTheme: Flow<AppTheme> = context.themeDataStore.data
        .map { preferences ->
            val themeName = preferences[SELECTED_THEME] ?: AppTheme.MODERN_LIGHT.name
            try {
                AppTheme.valueOf(themeName)
            } catch (e: IllegalArgumentException) {
                AppTheme.MODERN_LIGHT
            }
        }
    
    suspend fun setTheme(theme: AppTheme) {
        context.themeDataStore.edit { preferences ->
            preferences[SELECTED_THEME] = theme.name
        }
    }
    
    suspend fun getCurrentTheme(): AppTheme {
        var theme = AppTheme.MODERN_LIGHT
        context.themeDataStore.data.collect { preferences ->
            val themeName = preferences[SELECTED_THEME] ?: AppTheme.MODERN_LIGHT.name
            theme = try {
                AppTheme.valueOf(themeName)
            } catch (e: IllegalArgumentException) {
                AppTheme.MODERN_LIGHT
            }
        }
        return theme
    }
}