package com.apulse.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.apulse.data.model.AppLog
import com.apulse.data.model.AppMetadata
import com.apulse.data.model.NetworkRequest
import com.apulse.data.model.RequestBody
import com.apulse.data.model.RequestHeaders
import com.apulse.data.model.ResponseBody
import com.apulse.data.model.ResponseHeaders
import com.apulse.data.model.Session
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
@Database(
    entities = [
        Session::class,
        NetworkRequest::class,
        RequestHeaders::class,
        ResponseHeaders::class,
        RequestBody::class,
        ResponseBody::class,
        AppMetadata::class,
        AppLog::class
    ],
    version = 3,
    exportSchema = true
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
    abstract fun appLogDao(): AppLogDao
    
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
                    .addCallback(object : Callback() {
                        override fun onOpen(connection: SQLiteConnection) {
                            connection.execSQL("PRAGMA foreign_keys=ON")
                        }
                    })
                    .fallbackToDestructiveMigration(false) // For now, in production we'd add proper migrations
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}