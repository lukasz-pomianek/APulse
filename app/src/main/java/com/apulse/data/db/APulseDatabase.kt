package com.apulse.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.apulse.data.model.*

@Database(
    entities = [
        Session::class,
        NetworkRequest::class,
        RequestHeaders::class,
        ResponseHeaders::class,
        RequestBody::class,
        ResponseBody::class,
        AppMetadata::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class APulseDatabase : RoomDatabase() {
    
    abstract fun sessionDao(): SessionDao
    abstract fun networkRequestDao(): NetworkRequestDao
    abstract fun requestHeadersDao(): RequestHeadersDao
    abstract fun responseHeadersDao(): ResponseHeadersDao
    abstract fun requestBodyDao(): RequestBodyDao
    abstract fun responseBodyDao(): ResponseBodyDao
    abstract fun appMetadataDao(): AppMetadataDao
    
    companion object {
        const val DATABASE_NAME = "apulse_database"
        
        @Volatile
        private var INSTANCE: APulseDatabase? = null
        
        fun getDatabase(context: Context): APulseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    APulseDatabase::class.java,
                    DATABASE_NAME
                )
                .enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration() // For now, in production we'd add proper migrations
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}