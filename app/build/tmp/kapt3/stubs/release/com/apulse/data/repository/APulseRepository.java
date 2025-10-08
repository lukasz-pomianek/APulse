package com.apulse.data.repository;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u001e\u0010\f\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u001e\u0010\u0010\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\u0018\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0016J\u000e\u0010\u0017\u001a\u00020\u0013H\u0086@\u00a2\u0006\u0002\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ2\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\n2\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\n2\u000e\b\u0002\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\n0\u001fH\u0086@\u00a2\u0006\u0002\u0010 J\u0016\u0010!\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\"\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001b0#J\u0012\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u001f0#J\u0012\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001b0\u001f0#J\u0012\u0010\'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u001f0#J\u0012\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u001f0#J\u000e\u0010)\u001a\u00020*H\u0086@\u00a2\u0006\u0002\u0010\u0018J\u001a\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u001f0#2\u0006\u0010\t\u001a\u00020\nJ\u001a\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u001f0#2\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010\r\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ*\u0010/\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u001f0#2\u0006\u0010\t\u001a\u00020\n2\u0006\u00100\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000fJ2\u00101\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u001f0#2\u0006\u0010\t\u001a\u00020\n2\u0006\u00102\u001a\u00020\u00152\u0006\u00103\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u00104J\u001a\u00105\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u001f0#2\u0006\u0010\t\u001a\u00020\nJ\u0012\u00106\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002070\u001f0#J\u0018\u00108\u001a\u0004\u0018\u0001072\u0006\u0010\t\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u000e\u00109\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0018J\u000e\u0010:\u001a\u00020\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0018J\u001e\u0010;\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\"\u0010<\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020%0\u001f0#2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010=\u001a\u00020\nJ\u0016\u0010>\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006?"}, d2 = {"Lcom/apulse/data/repository/APulseRepository;", "", "database", "Lcom/apulse/data/db/APulseDatabase;", "sessionManager", "Lcom/apulse/service/SessionManager;", "(Lcom/apulse/data/db/APulseDatabase;Lcom/apulse/service/SessionManager;)V", "activateSession", "", "sessionId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addNoteToRequest", "requestId", "note", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addTagToRequest", "tag", "cleanupOldData", "", "olderThanDays", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearAllData", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "clearSession", "createSession", "Lcom/apulse/data/model/Session;", "name", "description", "tags", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteSession", "getActiveSession", "Lkotlinx/coroutines/flow/Flow;", "getAllRequests", "Lcom/apulse/data/model/NetworkRequest;", "getAllSessions", "getBookmarkedRequests", "getCurrentSessionRequests", "getDatabaseSize", "", "getHostsForSession", "getMethodsForSession", "getRequestDetails", "Lcom/apulse/data/model/NetworkRequestDetails;", "getRequestsByHost", "host", "getRequestsByStatusRange", "minStatus", "maxStatus", "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getRequestsForSession", "getSessionStats", "Lcom/apulse/data/model/SessionWithStats;", "getSessionWithStats", "getTotalRequestCount", "getTotalSessionCount", "removeTagFromRequest", "searchRequests", "query", "toggleBookmark", "app_release"})
public final class APulseRepository {
    @org.jetbrains.annotations.NotNull
    private final com.apulse.data.db.APulseDatabase database = null;
    @org.jetbrains.annotations.NotNull
    private final com.apulse.service.SessionManager sessionManager = null;
    
    @javax.inject.Inject
    public APulseRepository(@org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database, @org.jetbrains.annotations.NotNull
    com.apulse.service.SessionManager sessionManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.Session>> getAllSessions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.apulse.data.model.Session> getActiveSession() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object createSession(@org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.Nullable
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> tags, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.apulse.data.model.Session> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object activateSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object deleteSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getSessionWithStats(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.apulse.data.model.SessionWithStats> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.NetworkRequest>> getAllRequests() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.NetworkRequest>> getRequestsForSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.NetworkRequest>> getCurrentSessionRequests() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.NetworkRequest>> searchRequests(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    java.lang.String query) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.NetworkRequest>> getBookmarkedRequests() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getRequestDetails(@org.jetbrains.annotations.NotNull
    java.lang.String requestId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.apulse.data.model.NetworkRequestDetails> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object toggleBookmark(@org.jetbrains.annotations.NotNull
    java.lang.String requestId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object addTagToRequest(@org.jetbrains.annotations.NotNull
    java.lang.String requestId, @org.jetbrains.annotations.NotNull
    java.lang.String tag, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object removeTagFromRequest(@org.jetbrains.annotations.NotNull
    java.lang.String requestId, @org.jetbrains.annotations.NotNull
    java.lang.String tag, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object addNoteToRequest(@org.jetbrains.annotations.NotNull
    java.lang.String requestId, @org.jetbrains.annotations.NotNull
    java.lang.String note, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.apulse.data.model.SessionWithStats>> getSessionStats() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getRequestsByStatusRange(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, int minStatus, int maxStatus, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends java.util.List<com.apulse.data.model.NetworkRequest>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getRequestsByHost(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    java.lang.String host, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<? extends java.util.List<com.apulse.data.model.NetworkRequest>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getHostsForSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<java.lang.String>> getMethodsForSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object clearSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object clearAllData(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object cleanupOldData(int olderThanDays, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getDatabaseSize(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getTotalRequestCount(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getTotalSessionCount(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
}