package com.apulse.data.db

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {
    
    @TypeConverter
    fun fromInstant(value: Instant?): Long? {
        return value?.toEpochMilliseconds()
    }
    
    @TypeConverter
    fun toInstant(value: Long?): Instant? {
        return value?.let { Instant.fromEpochMilliseconds(it) }
    }
    
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return if (value.isNullOrEmpty()) null else Json.encodeToString(value)
    }
    
    @TypeConverter
    fun toStringList(value: String?): List<String> {
        return if (value.isNullOrEmpty()) emptyList() 
               else Json.decodeFromString<List<String>>(value)
    }
    
    @TypeConverter
    fun fromStringMap(value: Map<String, String>?): String? {
        return if (value.isNullOrEmpty()) null else Json.encodeToString(value)
    }
    
    @TypeConverter
    fun toStringMap(value: String?): Map<String, String> {
        return if (value.isNullOrEmpty()) emptyMap() 
               else Json.decodeFromString<Map<String, String>>(value)
    }
}