package com.apulse.data.db

import androidx.room.*
import com.apulse.data.model.AppMetadata

@Dao
interface AppMetadataDao {
    
    @Query("SELECT * FROM app_metadata WHERE requestId = :requestId")
    suspend fun getMetadataForRequest(requestId: String): AppMetadata?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetadata(metadata: AppMetadata)
    
    @Update
    suspend fun updateMetadata(metadata: AppMetadata)
    
    @Delete
    suspend fun deleteMetadata(metadata: AppMetadata)
    
    @Query("DELETE FROM app_metadata WHERE requestId = :requestId")
    suspend fun deleteMetadataForRequest(requestId: String)
    
    @Query("SELECT DISTINCT screenName FROM app_metadata WHERE screenName IS NOT NULL ORDER BY screenName")
    suspend fun getDistinctScreenNames(): List<String>
    
    @Query("SELECT DISTINCT userId FROM app_metadata WHERE userId IS NOT NULL ORDER BY userId")
    suspend fun getDistinctUserIds(): List<String>
}