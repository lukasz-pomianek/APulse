package com.apulse.data.db

import androidx.room.*
import com.apulse.data.model.ResponseBody

@Dao
interface ResponseBodyDao {
    
    @Query("SELECT * FROM response_bodies WHERE requestId = :requestId")
    suspend fun getBodyForRequest(requestId: String): ResponseBody?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBody(body: ResponseBody): Long
    
    @Update
    suspend fun updateBody(body: ResponseBody): Int
    
    @Delete
    suspend fun deleteBody(body: ResponseBody): Int
    
    @Query("DELETE FROM response_bodies WHERE requestId = :requestId")
    suspend fun deleteBodyForRequest(requestId: String): Int
    
    @Query("SELECT SUM(size) FROM response_bodies")
    suspend fun getTotalBodySize(): Long?
    
    @Query("DELETE FROM response_bodies WHERE size > :maxSize")
    fun deleteLargeBodies(maxSize: Long): Int
    
    @Query("SELECT * FROM response_bodies WHERE isImage = 1 ORDER BY requestId")
    suspend fun getImageBodies(): List<ResponseBody>
    
    @Query("SELECT * FROM response_bodies WHERE isJson = 1 ORDER BY requestId")
    suspend fun getJsonBodies(): List<ResponseBody>
}