package com.apulse.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\'J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\'J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0011H\'J\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0011H\'J\u000f\u0010\u0013\u001a\u0004\u0018\u00010\u000eH\'\u00a2\u0006\u0002\u0010\u0014J\u0016\u0010\u0015\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0017"}, d2 = {"Lcom/apulse/data/db/ResponseBodyDao;", "", "deleteBody", "", "body", "Lcom/apulse/data/model/ResponseBody;", "(Lcom/apulse/data/model/ResponseBody;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteBodyForRequest", "requestId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteLargeBodies", "", "maxSize", "", "getBodyForRequest", "getImageBodies", "", "getJsonBodies", "getTotalBodySize", "()Ljava/lang/Long;", "insertBody", "updateBody", "app_debug"})
@androidx.room.Dao
public abstract interface ResponseBodyDao {
    
    @androidx.room.Query(value = "SELECT * FROM response_bodies WHERE requestId = :requestId")
    @org.jetbrains.annotations.Nullable
    public abstract com.apulse.data.model.ResponseBody getBodyForRequest(@org.jetbrains.annotations.NotNull
    java.lang.String requestId);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertBody(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.ResponseBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateBody(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.ResponseBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteBody(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.ResponseBody body, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM response_bodies WHERE requestId = :requestId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteBodyForRequest(@org.jetbrains.annotations.NotNull
    java.lang.String requestId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT SUM(size) FROM response_bodies")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Long getTotalBodySize();
    
    @androidx.room.Query(value = "DELETE FROM response_bodies WHERE size > :maxSize")
    public abstract int deleteLargeBodies(long maxSize);
    
    @androidx.room.Query(value = "SELECT * FROM response_bodies WHERE isImage = 1 ORDER BY requestId")
    @org.jetbrains.annotations.NotNull
    public abstract java.util.List<com.apulse.data.model.ResponseBody> getImageBodies();
    
    @androidx.room.Query(value = "SELECT * FROM response_bodies WHERE isJson = 1 ORDER BY requestId")
    @org.jetbrains.annotations.NotNull
    public abstract java.util.List<com.apulse.data.model.ResponseBody> getJsonBodies();
}