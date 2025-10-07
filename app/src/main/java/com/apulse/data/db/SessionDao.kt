package com.apulse.data.db

import androidx.room.*
import com.apulse.data.model.Session
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Instant

@Dao
interface SessionDao {
    
    @Query("SELECT * FROM sessions ORDER BY updatedAt DESC")
    fun getAllSessions(): Flow<List<Session>>
    
    @Query("SELECT * FROM sessions WHERE id = :sessionId")
    suspend fun getSession(sessionId: String): Session?
    
    @Query("SELECT * FROM sessions WHERE isActive = 1 ORDER BY updatedAt DESC LIMIT 1")
    suspend fun getActiveSession(): Session?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: Session)
    
    @Update
    suspend fun updateSession(session: Session)
    
    @Delete
    suspend fun deleteSession(session: Session)
    
    @Query("DELETE FROM sessions WHERE id = :sessionId")
    suspend fun deleteSessionById(sessionId: String)
    
    @Query("UPDATE sessions SET isActive = 0 WHERE id != :activeSessionId")
    suspend fun deactivateOtherSessions(activeSessionId: String)
    
    @Query("UPDATE sessions SET totalRequests = :count, totalSize = :size, updatedAt = :updatedAt WHERE id = :sessionId")
    suspend fun updateSessionStats(sessionId: String, count: Int, size: Long, updatedAt: Instant)
    
    @Query("DELETE FROM sessions WHERE updatedAt < :cutoffTime")
    suspend fun deleteOldSessions(cutoffTime: Instant)
    
    @Query("SELECT COUNT(*) FROM sessions")
    suspend fun getSessionCount(): Int
    
    @Query("SELECT SUM(totalSize) FROM sessions")
    suspend fun getTotalSize(): Long?
}