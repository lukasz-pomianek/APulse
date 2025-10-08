package com.apulse.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\nJ\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\t0\fH\'J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\fH\'J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\b\u001a\u00020\tH\'J\u0016\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/apulse/data/db/AppMetadataDao;", "", "deleteMetadata", "", "metadata", "Lcom/apulse/data/model/AppMetadata;", "(Lcom/apulse/data/model/AppMetadata;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteMetadataForRequest", "requestId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDistinctScreenNames", "", "getDistinctUserIds", "getMetadataForRequest", "insertMetadata", "updateMetadata", "app_debug"})
@androidx.room.Dao
public abstract interface AppMetadataDao {
    
    @androidx.room.Query(value = "SELECT * FROM app_metadata WHERE requestId = :requestId")
    @org.jetbrains.annotations.Nullable
    public abstract com.apulse.data.model.AppMetadata getMetadataForRequest(@org.jetbrains.annotations.NotNull
    java.lang.String requestId);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object insertMetadata(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.AppMetadata metadata, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object updateMetadata(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.AppMetadata metadata, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteMetadata(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.AppMetadata metadata, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM app_metadata WHERE requestId = :requestId")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object deleteMetadataForRequest(@org.jetbrains.annotations.NotNull
    java.lang.String requestId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT DISTINCT screenName FROM app_metadata WHERE screenName IS NOT NULL ORDER BY screenName")
    @org.jetbrains.annotations.NotNull
    public abstract java.util.List<java.lang.String> getDistinctScreenNames();
    
    @androidx.room.Query(value = "SELECT DISTINCT userId FROM app_metadata WHERE userId IS NOT NULL ORDER BY userId")
    @org.jetbrains.annotations.NotNull
    public abstract java.util.List<java.lang.String> getDistinctUserIds();
}