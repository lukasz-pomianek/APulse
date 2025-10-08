package com.apulse.data.db

import androidx.room.*
import com.apulse.data.model.NetworkRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

@Dao
interface NetworkRequestDao {
    
    @Query("SELECT * FROM network_requests ORDER BY startTime DESC")
    fun getAllRequests(): Flow<List<NetworkRequest>>
    
    @Query("SELECT * FROM network_requests WHERE sessionId = :sessionId ORDER BY startTime DESC")
    fun getRequestsForSession(sessionId: String): Flow<List<NetworkRequest>>
    
    @Query("SELECT * FROM network_requests WHERE id = :requestId")
    fun getRequest(requestId: String): NetworkRequest?
    
    @Query("""
        SELECT * FROM network_requests 
        WHERE sessionId = :sessionId 
        AND (url LIKE '%' || :searchQuery || '%' OR host LIKE '%' || :searchQuery || '%')
        ORDER BY startTime DESC
    """)
    fun searchRequestsInSession(sessionId: String, searchQuery: String): Flow<List<NetworkRequest>>
    
    @Query("""
        SELECT * FROM network_requests 
        WHERE sessionId = :sessionId 
        AND statusCode BETWEEN :minStatus AND :maxStatus
        ORDER BY startTime DESC
    """)
    fun getRequestsByStatusRange(sessionId: String, minStatus: Int, maxStatus: Int): Flow<List<NetworkRequest>>
    
    @Query("SELECT * FROM network_requests WHERE sessionId = :sessionId AND host = :host ORDER BY startTime DESC")
    fun getRequestsByHost(sessionId: String, host: String): Flow<List<NetworkRequest>>
    
    @Query("SELECT * FROM network_requests WHERE isBookmarked = 1 ORDER BY startTime DESC")
    fun getBookmarkedRequests(): Flow<List<NetworkRequest>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRequest(request: NetworkRequest)
    
    @Update
    fun updateRequest(request: NetworkRequest)
    
    @Delete
    fun deleteRequest(request: NetworkRequest)
    
    @Query("DELETE FROM network_requests WHERE sessionId = :sessionId")
    fun deleteRequestsForSession(sessionId: String)
    
    @Query("DELETE FROM network_requests WHERE startTime < :cutoffTime")
    fun deleteOldRequests(cutoffTime: Instant)
    
    @Query("UPDATE network_requests SET isBookmarked = :isBookmarked WHERE id = :requestId")
    fun updateBookmarkStatus(requestId: String, isBookmarked: Boolean)
    
    @Query("UPDATE network_requests SET tags = :tags WHERE id = :requestId")
    fun updateTags(requestId: String, tags: List<String>)
    
    @Query("UPDATE network_requests SET notes = :notes WHERE id = :requestId")
    fun updateNotes(requestId: String, notes: String?)
    
    @Query("SELECT COUNT(*) FROM network_requests WHERE sessionId = :sessionId")
    fun getRequestCountForSession(sessionId: String): Int
    
    @Query("SELECT SUM(requestSize + responseSize) FROM network_requests WHERE sessionId = :sessionId")
    fun getTotalSizeForSession(sessionId: String): Long?
    
    @Query("SELECT DISTINCT host FROM network_requests WHERE sessionId = :sessionId ORDER BY host")
    fun getHostsForSession(sessionId: String): Flow<List<String>>
    
    @Query("SELECT DISTINCT method FROM network_requests WHERE sessionId = :sessionId ORDER BY method")
    fun getMethodsForSession(sessionId: String): Flow<List<String>>
}