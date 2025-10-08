package com.apulse.data.db

import androidx.room.*
import com.apulse.data.model.ResponseBody

@Dao
interface ResponseBodyDao {
    
    @Query("SELECT * FROM response_bodies WHERE requestId = :requestId")
    fun getBodyForRequest(requestId: String): ResponseBody?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBody(body: ResponseBody)
    
    @Update
    fun updateBody(body: ResponseBody)
    
    @Delete
    fun deleteBody(body: ResponseBody)
    
    @Query("DELETE FROM response_bodies WHERE requestId = :requestId")
    fun deleteBodyForRequest(requestId: String)
    
    @Query("SELECT SUM(size) FROM response_bodies")
    fun getTotalBodySize(): Long?
    
    @Query("DELETE FROM response_bodies WHERE size > :maxSize")
    fun deleteLargeBodies(maxSize: Long): Int
    
    @Query("SELECT * FROM response_bodies WHERE isImage = 1 ORDER BY requestId")
    fun getImageBodies(): List<ResponseBody>
    
    @Query("SELECT * FROM response_bodies WHERE isJson = 1 ORDER BY requestId")
    fun getJsonBodies(): List<ResponseBody>
}