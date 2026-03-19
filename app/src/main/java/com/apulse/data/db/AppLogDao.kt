package com.apulse.data.db

import androidx.room.*
import com.apulse.data.model.AppLog
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

@Dao
interface AppLogDao {
    
    @Query("SELECT * FROM app_logs ORDER BY timestamp DESC")
    fun getAllLogs(): Flow<List<AppLog>>
    
    @Query("SELECT * FROM app_logs WHERE sessionId = :sessionId ORDER BY timestamp DESC")
    fun getLogsForSession(sessionId: String): Flow<List<AppLog>>
    
    // Safe methods that limit message size to prevent cursor window overflow
    @Query("""
        SELECT 
            id, sessionId, priority, tag, 
            CASE 
                WHEN LENGTH(message) > 200 THEN SUBSTR(message, 1, 200) || '...' 
                ELSE message 
            END as message,
            CASE 
                WHEN LENGTH(COALESCE(error, '')) > 100 THEN SUBSTR(error, 1, 100) || '...' 
                ELSE error 
            END as error,
            NULL as stackTrace,  -- Skip stack traces in list view
            timestamp, threadName, className, methodName, lineNumber
        FROM app_logs 
        ORDER BY timestamp DESC
    """)
    fun getAllLogsSummary(): Flow<List<AppLog>>
    
    @Query("""
        SELECT 
            id, sessionId, priority, tag, 
            CASE 
                WHEN LENGTH(message) > 200 THEN SUBSTR(message, 1, 200) || '...' 
                ELSE message 
            END as message,
            CASE 
                WHEN LENGTH(COALESCE(error, '')) > 100 THEN SUBSTR(error, 1, 100) || '...' 
                ELSE error 
            END as error,
            NULL as stackTrace,  -- Skip stack traces in list view
            timestamp, threadName, className, methodName, lineNumber
        FROM app_logs 
        WHERE sessionId = :sessionId 
        ORDER BY timestamp DESC
    """)
    fun getLogsForSessionSummary(sessionId: String): Flow<List<AppLog>>
    
    @Query("SELECT * FROM app_logs WHERE priority >= :minPriority ORDER BY timestamp DESC")
    fun getLogsByPriority(minPriority: Int): Flow<List<AppLog>>
    
    @Query("SELECT * FROM app_logs WHERE tag = :tag ORDER BY timestamp DESC")
    fun getLogsByTag(tag: String): Flow<List<AppLog>>
    
    @Query("""
        SELECT * FROM app_logs 
        WHERE sessionId = :sessionId 
        AND priority >= :minPriority 
        ORDER BY timestamp DESC
    """)
    fun getLogsForSessionByPriority(sessionId: String, minPriority: Int): Flow<List<AppLog>>
    
    @Query("""
        SELECT * FROM app_logs 
        WHERE sessionId = :sessionId 
        AND (tag LIKE '%' || :searchQuery || '%' OR message LIKE '%' || :searchQuery || '%')
        ORDER BY timestamp DESC
    """)
    fun searchLogsInSession(sessionId: String, searchQuery: String): Flow<List<AppLog>>
    
    @Query("SELECT * FROM app_logs WHERE id = :logId")
    fun getLog(logId: String): AppLog?
    
    @Query("SELECT * FROM app_logs WHERE id = :logId")
    suspend fun getLogById(logId: String): AppLog?
    
    @Query("SELECT * FROM app_logs WHERE sessionId = :sessionId ORDER BY timestamp DESC")
    suspend fun getLogsBySession(sessionId: String): List<AppLog>
    
    @Query("""
        SELECT * FROM app_logs 
        WHERE sessionId = :sessionId 
        AND (message LIKE :searchQuery OR tag LIKE :searchQuery)
        ORDER BY timestamp DESC
    """)
    suspend fun searchLogs(sessionId: String, searchQuery: String): List<AppLog>
    
    @Query("""
        SELECT * FROM app_logs 
        WHERE sessionId = :sessionId 
        AND tag IN (:tags)
        ORDER BY timestamp DESC
    """)
    suspend fun getLogsBySessionAndTags(sessionId: String, tags: List<String>): List<AppLog>
    
    @Query("""
        SELECT * FROM app_logs 
        WHERE sessionId = :sessionId 
        AND priority IN (:priorities)
        ORDER BY timestamp DESC
    """)
    suspend fun getLogsBySessionAndPriorities(sessionId: String, priorities: List<Int>): List<AppLog>
    
    @Query("DELETE FROM app_logs WHERE sessionId = :sessionId")
    suspend fun deleteLogsBySession(sessionId: String)
    
    @Query("SELECT DISTINCT tag FROM app_logs WHERE tag IS NOT NULL ORDER BY tag")
    fun getAllTags(): Flow<List<String>>
    
    @Query("SELECT DISTINCT tag FROM app_logs WHERE sessionId = :sessionId AND tag IS NOT NULL ORDER BY tag")
    fun getTagsForSession(sessionId: String): Flow<List<String>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: AppLog)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogs(logs: List<AppLog>)
    
    @Update
    suspend fun updateLog(log: AppLog)
    
    @Delete
    suspend fun deleteLog(log: AppLog)
    
    @Query("DELETE FROM app_logs WHERE sessionId = :sessionId")
    suspend fun deleteLogsForSession(sessionId: String)
    
    @Query("DELETE FROM app_logs WHERE priority < :minPriority")
    suspend fun deleteLowPriorityLogs(minPriority: Int): Int
    
    @Query("DELETE FROM app_logs WHERE timestamp < :beforeDate")
    suspend fun deleteOldLogs(beforeDate: Instant): Int
    
    @Query("SELECT COUNT(*) FROM app_logs WHERE sessionId = :sessionId")
    suspend fun getLogCountForSession(sessionId: String): Int
    
    @Query("SELECT COUNT(*) FROM app_logs WHERE priority = :priority")
    suspend fun getLogCountByPriority(priority: Int): Int
}