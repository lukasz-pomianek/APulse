package com.apulse.data.db

import androidx.room.*
import com.apulse.data.model.RequestBody

@Dao
interface RequestBodyDao {
    
    @Query("SELECT * FROM request_bodies WHERE requestId = :requestId")
    suspend fun getBodyForRequest(requestId: String): RequestBody?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBody(body: RequestBody)
    
    @Update
    suspend fun updateBody(body: RequestBody)
    
    @Delete
    suspend fun deleteBody(body: RequestBody)
    
    @Query("DELETE FROM request_bodies WHERE requestId = :requestId")
    suspend fun deleteBodyForRequest(requestId: String)
    
    @Query("SELECT SUM(size) FROM request_bodies")
    suspend fun getTotalBodySize(): Long?
    
    @Query("DELETE FROM request_bodies WHERE size > :maxSize")
    suspend fun deleteLargeBodies(maxSize: Long)
}