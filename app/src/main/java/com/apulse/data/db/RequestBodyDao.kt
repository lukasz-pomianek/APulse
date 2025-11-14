package com.apulse.data.db

import androidx.room.*
import com.apulse.data.model.RequestBody

@Dao
interface RequestBodyDao {
    
    @Query("SELECT * FROM request_bodies WHERE requestId = :requestId")
    fun getBodyForRequest(requestId: String): RequestBody?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBody(body: RequestBody)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBodySuspend(body: RequestBody)
    
    @Update
    fun updateBody(body: RequestBody)
    
    @Delete
    fun deleteBody(body: RequestBody)
    
    @Query("DELETE FROM request_bodies WHERE requestId = :requestId")
    fun deleteBodyForRequest(requestId: String)
    
    @Query("SELECT SUM(size) FROM request_bodies")
    fun getTotalBodySize(): Long?
    
    @Query("DELETE FROM request_bodies WHERE size > :maxSize")
    fun deleteLargeBodies(maxSize: Long)
    
    @Query("SELECT size FROM request_bodies WHERE requestId = :requestId")
    fun getBodySizeForRequest(requestId: String): Long?
    
    @Query("SELECT id, requestId, size, contentType, contentEncoding, isRedacted, filePath, NULL as body, NULL as bodyText FROM request_bodies WHERE requestId = :requestId")
    fun getBodyMetadataForRequest(requestId: String): RequestBody?
}