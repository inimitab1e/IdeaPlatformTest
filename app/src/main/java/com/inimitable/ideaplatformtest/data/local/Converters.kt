package com.inimitable.ideaplatformtest.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromList(tags: List<String>): String {
        return Gson().toJson(tags)
    }

    @TypeConverter
    fun toList(tagsString: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(tagsString, listType)
    }
}