package com.dev.tvmania.featuretvshow.data.local.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class ControlledConverter(private val gson: Gson) {

    @TypeConverter
    fun fromJsonToStringList(json: String): List<String> {
        return try {
            gson.fromJson(
                json,
                object : TypeToken<List<String>>() {}.type
            )
        } catch (exp: JsonSyntaxException) {
            emptyList()
        }
    }

    @TypeConverter
    fun stringListToJsonString(strings: List<String>): String {
        return try {
            gson.toJson(
                strings,
                object : TypeToken<List<String>>() {}.type
            )
        } catch (exp: JsonSyntaxException) {
            "[]"
        }
    }
}