package com.apulse.di

import android.content.Context
import com.apulse.data.db.APulseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideAPulseDatabase(
        @ApplicationContext context: Context
    ): APulseDatabase {
        return APulseDatabase.getDatabase(context)
    }
    
    @Provides
    fun provideSessionDao(database: APulseDatabase) = database.sessionDao()
    
    @Provides
    fun provideNetworkRequestDao(database: APulseDatabase) = database.networkRequestDao()
    
    @Provides
    fun provideRequestHeadersDao(database: APulseDatabase) = database.requestHeadersDao()
    
    @Provides
    fun provideResponseHeadersDao(database: APulseDatabase) = database.responseHeadersDao()
    
    @Provides
    fun provideRequestBodyDao(database: APulseDatabase) = database.requestBodyDao()
    
    @Provides
    fun provideResponseBodyDao(database: APulseDatabase) = database.responseBodyDao()
    
    @Provides
    fun provideAppMetadataDao(database: APulseDatabase) = database.appMetadataDao()
}