package com.apulse.data.db

import androidx.room.*
import com.apulse.data.model.ResponseHeaders

@Dao
interface ResponseHeadersDao {
    
    @Query("SELECT * FROM response_headers WHERE requestId = :requestId")
    fun getHeadersForRequest(requestId: String): ResponseHeaders?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeaders(headers: ResponseHeaders)
    
    @Update
    fun updateHeaders(headers: ResponseHeaders)
    
    @Delete
    fun deleteHeaders(headers: ResponseHeaders)
    
    @Query("DELETE FROM response_headers WHERE requestId = :requestId")
    fun deleteHeadersForRequest(requestId: String)
}