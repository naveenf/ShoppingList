package com.example.shoppinglist.data.database

import androidx.room.TypeConverter
import com.example.shoppinglist.data.database.entities.TemplateItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromTemplateItemList(items: List<TemplateItem>): String {
        return Gson().toJson(items)
    }

    @TypeConverter
    fun toTemplateItemList(itemsJson: String): List<TemplateItem> {
        val type = object : TypeToken<List<TemplateItem>>() {}.type
        return Gson().fromJson(itemsJson, type)
    }
}