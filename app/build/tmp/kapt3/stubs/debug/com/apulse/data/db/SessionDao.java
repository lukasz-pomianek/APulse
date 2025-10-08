package com.apulse.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\b\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\'J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\'J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u0005H\'J\n\u0010\u000e\u001a\u0004\u0018\u00010\u000bH\'J\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00110\u0010H\'J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\r\u001a\u00020\u0005H\'J\b\u0010\u0013\u001a\u00020\u0003H\'J\u000f\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\'\u00a2\u0006\u0002\u0010\u0016J\u0010\u0010\u0017\u001a\u00020\u00152\u0006\u0010\n\u001a\u00020\u000bH\'J\u0010\u0010\u0018\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\'J(\u0010\u0019\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u00152\u0006\u0010\u001c\u001a\u00020\bH\'\u00a8\u0006\u001d"}, d2 = {"Lcom/apulse/data/db/SessionDao;", "", "deactivateOtherSessions", "", "activeSessionId", "", "deleteOldSessions", "cutoffTime", "Lkotlinx/datetime/Instant;", "deleteSession", "session", "Lcom/apulse/data/model/Session;", "deleteSessionById", "sessionId", "getActiveSession", "getAllSessions", "Lkotlinx/coroutines/flow/Flow;", "", "getSession", "getSessionCount", "getTotalSize", "", "()Ljava/lang/Long;", "insertSession", "updateSession", "updateSessionStats", "count", "size", "updatedAt", "app_debug"})
@androidx.room.Dao
public abstract interface SessionDao {
    
    @androidx.room.Query(value = "SELECT * FROM sessions ORDER BY updatedAt DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.Session>> getAllSessions();
    
    @androidx.room.Query(value = "SELECT * FROM sessions WHERE id = :sessionId")
    @org.jetbrains.annotations.Nullable
    public abstract com.apulse.data.model.Session getSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId);
    
    @androidx.room.Query(value = "SELECT * FROM sessions WHERE isActive = 1 ORDER BY updatedAt DESC LIMIT 1")
    @org.jetbrains.annotations.Nullable
    public abstract com.apulse.data.model.Session getActiveSession();
    
    @androidx.room.Insert(onConflict = 1)
    public abstract long insertSession(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.Session session);
    
    @androidx.room.Update
    public abstract int updateSession(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.Session session);
    
    @androidx.room.Delete
    public abstract int deleteSession(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.Session session);
    
    @androidx.room.Query(value = "DELETE FROM sessions WHERE id = :sessionId")
    public abstract int deleteSessionById(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId);
    
    @androidx.room.Query(value = "UPDATE sessions SET isActive = 0 WHERE id != :activeSessionId")
    public abstract int deactivateOtherSessions(@org.jetbrains.annotations.NotNull
    java.lang.String activeSessionId);
    
    @androidx.room.Query(value = "UPDATE sessions SET totalRequests = :count, totalSize = :size, updatedAt = :updatedAt WHERE id = :sessionId")
    public abstract int updateSessionStats(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, int count, long size, @org.jetbrains.annotations.NotNull
    kotlinx.datetime.Instant updatedAt);
    
    @androidx.room.Query(value = "DELETE FROM sessions WHERE updatedAt < :cutoffTime")
    public abstract int deleteOldSessions(@org.jetbrains.annotations.NotNull
    kotlinx.datetime.Instant cutoffTime);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM sessions")
    public abstract int getSessionCount();
    
    @androidx.room.Query(value = "SELECT SUM(totalSize) FROM sessions")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Long getTotalSize();
}