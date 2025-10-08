package com.apulse.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\u0010H\'J\u0014\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\u0010H\'J\u001c\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00110\u00102\u0006\u0010\f\u001a\u00020\rH\'J\u001c\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u00110\u00102\u0006\u0010\f\u001a\u00020\rH\'J\u0018\u0010\u0015\u001a\u0004\u0018\u00010\t2\u0006\u0010\u0016\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u0017\u001a\u00020\u00182\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ$\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\u00102\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\rH\'J,\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\u00102\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001d\u001a\u00020\u0018H\'J\u001c\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\u00102\u0006\u0010\f\u001a\u00020\rH\'J\u0018\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010!\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ$\u0010\"\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\u00102\u0006\u0010\f\u001a\u00020\r2\u0006\u0010#\u001a\u00020\rH\'J\u001e\u0010$\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\r2\u0006\u0010%\u001a\u00020&H\u00a7@\u00a2\u0006\u0002\u0010\'J \u0010(\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\r2\b\u0010)\u001a\u0004\u0018\u00010\rH\u00a7@\u00a2\u0006\u0002\u0010*J\u0016\u0010+\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ$\u0010,\u001a\u00020\u00032\u0006\u0010\u0016\u001a\u00020\r2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\r0\u0011H\u00a7@\u00a2\u0006\u0002\u0010.\u00a8\u0006/"}, d2 = {"Lcom/apulse/data/db/NetworkRequestDao;", "", "deleteOldRequests", "", "cutoffTime", "Lkotlinx/datetime/Instant;", "(Lkotlinx/datetime/Instant;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRequest", "request", "Lcom/apulse/data/model/NetworkRequest;", "(Lcom/apulse/data/model/NetworkRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteRequestsForSession", "sessionId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllRequests", "Lkotlinx/coroutines/flow/Flow;", "", "getBookmarkedRequests", "getHostsForSession", "getMethodsForSession", "getRequest", "requestId", "getRequestCountForSession", "", "getRequestsByHost", "host", "getRequestsByStatusRange", "minStatus", "maxStatus", "getRequestsForSession", "getTotalSizeForSession", "", "insertRequest", "searchRequestsInSession", "searchQuery", "updateBookmarkStatus", "isBookmarked", "", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateNotes", "notes", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateRequest", "updateTags", "tags", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface NetworkRequestDao {
    
    @androidx.room.Query(value = "SELECT * FROM network_requests ORDER BY startTime DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.NetworkRequest>> getAllRequests();
    
    @androidx.room.Query(value = "SELECT * FROM network_requests WHERE sessionId = :sessionId ORDER BY startTime DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.NetworkRequest>> getRequestsForSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId);
    
    @androidx.room.Query(value = "SELECT * FROM network_requests WHERE id = :requestId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getRequest(@org.jetbrains.annotations.NotNull
    java.lang.String requestId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.apulse.data.model.NetworkRequest> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM network_requests \n        WHERE sessionId = :sessionId \n        AND (url LIKE \'%\' || :searchQuery || \'%\' OR host LIKE \'%\' || :searchQuery || \'%\')\n        ORDER BY startTime DESC\n    ")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.NetworkRequest>> searchRequestsInSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    java.lang.String searchQuery);
    
    @androidx.room.Query(value = "\n        SELECT * FROM network_requests \n        WHERE sessionId = :sessionId \n        AND statusCode BETWEEN :minStatus AND :maxStatus\n        ORDER BY startTime DESC\n    ")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.NetworkRequest>> getRequestsByStatusRange(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, int minStatus, int maxStatus);
    
    @androidx.room.Query(value = "SELECT * FROM network_requests WHERE sessionId = :sessionId AND host = :host ORDER BY startTime DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.NetworkRequest>> getRequestsByHost(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    java.lang.String host);
    
    @androidx.room.Query(value = "SELECT * FROM network_requests WHERE isBookmarked = 1 ORDER BY startTime DESC")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.NetworkRequest>> getBookmarkedRequests();
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertRequest(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.NetworkRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateRequest(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.NetworkRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteRequest(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.NetworkRequest request, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM network_requests WHERE sessionId = :sessionId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteRequestsForSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM network_requests WHERE startTime < :cutoffTime")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteOldRequests(@org.jetbrains.annotations.NotNull
    kotlinx.datetime.Instant cutoffTime, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE network_requests SET isBookmarked = :isBookmarked WHERE id = :requestId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateBookmarkStatus(@org.jetbrains.annotations.NotNull
    java.lang.String requestId, boolean isBookmarked, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE network_requests SET tags = :tags WHERE id = :requestId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateTags(@org.jetbrains.annotations.NotNull
    java.lang.String requestId, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> tags, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE network_requests SET notes = :notes WHERE id = :requestId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateNotes(@org.jetbrains.annotations.NotNull
    java.lang.String requestId, @org.jetbrains.annotations.Nullable
    java.lang.String notes, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM network_requests WHERE sessionId = :sessionId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getRequestCountForSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(requestSize + responseSize) FROM network_requests WHERE sessionId = :sessionId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getTotalSizeForSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "SELECT DISTINCT host FROM network_requests WHERE sessionId = :sessionId ORDER BY host")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getHostsForSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId);
    
    @androidx.room.Query(value = "SELECT DISTINCT method FROM network_requests WHERE sessionId = :sessionId ORDER BY method")
    @org.jetbrains.annotations.NotNull
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getMethodsForSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId);
}