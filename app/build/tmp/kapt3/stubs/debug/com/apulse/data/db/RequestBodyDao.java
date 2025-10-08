package com.apulse.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\rH\u00a7@\u00a2\u0006\u0002\u0010\u000eJ\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u0010\u001a\u0004\u0018\u00010\rH\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0016\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0014"}, d2 = {"Lcom/apulse/data/db/RequestBodyDao;", "", "deleteBody", "", "body", "Lcom/apulse/data/model/RequestBody;", "(Lcom/apulse/data/model/RequestBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteBodyForRequest", "requestId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteLargeBodies", "maxSize", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBodyForRequest", "getTotalBodySize", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertBody", "updateBody", "app_debug"})
@androidx.room.Dao
public abstract interface RequestBodyDao {
    
    @androidx.room.Query(value = "SELECT * FROM request_bodies WHERE requestId = :requestId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getBodyForRequest(@org.jetbrains.annotations.NotNull
    java.lang.String requestId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.apulse.data.model.RequestBody> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertBody(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.RequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateBody(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.RequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteBody(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.RequestBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM request_bodies WHERE requestId = :requestId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteBodyForRequest(@org.jetbrains.annotations.NotNull
    java.lang.String requestId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(size) FROM request_bodies")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getTotalBodySize(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    @androidx.room.Query(value = "DELETE FROM request_bodies WHERE size > :maxSize")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteLargeBodies(long maxSize, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}