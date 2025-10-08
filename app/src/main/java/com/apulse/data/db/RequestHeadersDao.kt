package com.apulse.data.db

import androidx.room.*
import com.apulse.data.model.RequestHeaders

@Dao
interface RequestHeadersDao {
    
    @Query("SELECT * FROM request_headers WHERE requestId = :requestId")
    fun getHeadersForRequest(requestId: String): RequestHeaders?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertHeaders(headers: RequestHeaders)
    
    @Update
    fun updateHeaders(headers: RequestHeaders)
    
    @Delete
    fun deleteHeaders(headers: RequestHeaders)
    
    @Query("DELETE FROM request_headers WHERE requestId = :requestId")
    fun deleteHeadersForRequest(requestId: String)
}