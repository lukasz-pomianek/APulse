package com.apulse.data.db

import androidx.room.*
import com.apulse.data.model.ResponseHeaders

@Dao
interface ResponseHeadersDao {
    
    @Query("SELECT * FROM response_headers WHERE requestId = :requestId")
    suspend fun getHeadersForRequest(requestId: String): ResponseHeaders?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeaders(headers: ResponseHeaders): Long
    
    @Update
    suspend fun updateHeaders(headers: ResponseHeaders): Int
    
    @Delete
    suspend fun deleteHeaders(headers: ResponseHeaders): Int
    
    @Query("DELETE FROM response_headers WHERE requestId = :requestId")
    suspend fun deleteHeadersForRequest(requestId: String): Int
}