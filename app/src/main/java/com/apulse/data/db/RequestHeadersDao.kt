package com.apulse.data.db

import androidx.room.*
import com.apulse.data.model.RequestHeaders

@Dao
interface RequestHeadersDao {
    
    @Query("SELECT * FROM request_headers WHERE requestId = :requestId")
    suspend fun getHeadersForRequest(requestId: String): RequestHeaders?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeaders(headers: RequestHeaders): Long
    
    @Update
    suspend fun updateHeaders(headers: RequestHeaders): Int
    
    @Delete
    suspend fun deleteHeaders(headers: RequestHeaders): Int
    
    @Query("DELETE FROM request_headers WHERE requestId = :requestId")
    suspend fun deleteHeadersForRequest(requestId: String): Int
}