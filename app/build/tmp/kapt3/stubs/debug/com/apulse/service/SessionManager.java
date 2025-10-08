package com.apulse.service;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\u0014J\u0018\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\nH\u0082@\u00a2\u0006\u0002\u0010\u001bJ<\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u00072\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00072\u000e\b\u0002\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00070 2\b\b\u0002\u0010!\u001a\u00020\u0012H\u0086@\u00a2\u0006\u0002\u0010\"J\u0016\u0010#\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\u0014J\u0016\u0010$\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\u0014J\u0010\u0010%\u001a\u00020\u00072\u0006\u0010&\u001a\u00020\'H\u0002J\u000e\u0010(\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010\u001bJ\u0018\u0010)\u001a\u0004\u0018\u00010*2\u0006\u0010\u0013\u001a\u00020\u0007H\u0086@\u00a2\u0006\u0002\u0010\u0014J$\u0010+\u001a\u00020\u00122\u0006\u0010,\u001a\u00020\u00072\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00070 H\u0086@\u00a2\u0006\u0002\u0010.J@\u0010/\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00072\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u00072\u0010\b\u0002\u0010\u001f\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010 H\u0086@\u00a2\u0006\u0002\u00100J\u0016\u00101\u001a\u00020\u00162\u0006\u0010\u0013\u001a\u00020\u0007H\u0082@\u00a2\u0006\u0002\u0010\u0014R\u0016\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0019\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0019\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lcom/apulse/service/SessionManager;", "", "database", "Lcom/apulse/data/db/APulseDatabase;", "(Lcom/apulse/data/db/APulseDatabase;)V", "_currentSessionId", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "currentSession", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/apulse/data/model/Session;", "getCurrentSession", "()Lkotlinx/coroutines/flow/StateFlow;", "currentSessionId", "getCurrentSessionId", "scope", "Lkotlinx/coroutines/CoroutineScope;", "activateSession", "", "sessionId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cleanupOldSessions", "", "olderThanDays", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createDefaultSession", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createSession", "name", "description", "tags", "", "activateImmediately", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deactivateSession", "deleteSession", "formatDateTime", "instant", "Lkotlinx/datetime/Instant;", "getOrCreateCurrentSession", "getSessionWithStats", "Lcom/apulse/data/model/SessionWithStats;", "mergeSessionsInto", "targetSessionId", "sourceSessionIds", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSessionMetadata", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSessionStatsInternal", "app_debug"})
public final class SessionManager {
    @org.jetbrains.annotations.NotNull
    private final com.apulse.data.db.APulseDatabase database = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope scope = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _currentSessionId = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> currentSessionId = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<com.apulse.data.model.Session> currentSession = null;
    
    @javax.inject.Inject
    public SessionManager(@org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getCurrentSessionId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<com.apulse.data.model.Session> getCurrentSession() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object createSession(@org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.Nullable
    java.lang.String description, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> tags, boolean activateImmediately, @org.jetbrains.annotations.NotNull
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
    public final java.lang.Object deactivateSession(@org.jetbrains.annotations.NotNull
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
    public final java.lang.Object updateSessionMetadata(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.Nullable
    java.lang.String name, @org.jetbrains.annotations.Nullable
    java.lang.String description, @org.jetbrains.annotations.Nullable
    java.util.List<java.lang.String> tags, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getSessionWithStats(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.apulse.data.model.SessionWithStats> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object mergeSessionsInto(@org.jetbrains.annotations.NotNull
    java.lang.String targetSessionId, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> sourceSessionIds, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object cleanupOldSessions(int olderThanDays, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getOrCreateCurrentSession(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.apulse.data.model.Session> $completion) {
        return null;
    }
    
    private final java.lang.Object createDefaultSession(kotlin.coroutines.Continuation<? super com.apulse.data.model.Session> $completion) {
        return null;
    }
    
    private final java.lang.Object updateSessionStatsInternal(java.lang.String sessionId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String formatDateTime(kotlinx.datetime.Instant instant) {
        return null;
    }
}