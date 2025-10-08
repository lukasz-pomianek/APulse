package com.apulse.data.db;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&J\b\u0010\t\u001a\u00020\nH&J\b\u0010\u000b\u001a\u00020\fH&J\b\u0010\r\u001a\u00020\u000eH&J\b\u0010\u000f\u001a\u00020\u0010H&\u00a8\u0006\u0012"}, d2 = {"Lcom/apulse/data/db/APulseDatabase;", "Landroidx/room/RoomDatabase;", "()V", "appMetadataDao", "Lcom/apulse/data/db/AppMetadataDao;", "networkRequestDao", "Lcom/apulse/data/db/NetworkRequestDao;", "requestBodyDao", "Lcom/apulse/data/db/RequestBodyDao;", "requestHeadersDao", "Lcom/apulse/data/db/RequestHeadersDao;", "responseBodyDao", "Lcom/apulse/data/db/ResponseBodyDao;", "responseHeadersDao", "Lcom/apulse/data/db/ResponseHeadersDao;", "sessionDao", "Lcom/apulse/data/db/SessionDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.apulse.data.model.Session.class, com.apulse.data.model.NetworkRequest.class, com.apulse.data.model.RequestHeaders.class, com.apulse.data.model.ResponseHeaders.class, com.apulse.data.model.RequestBody.class, com.apulse.data.model.ResponseBody.class, com.apulse.data.model.AppMetadata.class}, version = 1, exportSchema = false)
@androidx.room.TypeConverters(value = {com.apulse.data.db.Converters.class})
public abstract class APulseDatabase extends androidx.room.RoomDatabase {
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String DATABASE_NAME = "apulse_database";
    @kotlin.jvm.Volatile
    @org.jetbrains.annotations.Nullable
    private static volatile com.apulse.data.db.APulseDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull
    public static final com.apulse.data.db.APulseDatabase.Companion Companion = null;
    
    public APulseDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract com.apulse.data.db.SessionDao sessionDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.apulse.data.db.NetworkRequestDao networkRequestDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.apulse.data.db.RequestHeadersDao requestHeadersDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.apulse.data.db.ResponseHeadersDao responseHeadersDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.apulse.data.db.RequestBodyDao requestBodyDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.apulse.data.db.ResponseBodyDao responseBodyDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.apulse.data.db.AppMetadataDao appMetadataDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/apulse/data/db/APulseDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "INSTANCE", "Lcom/apulse/data/db/APulseDatabase;", "getDatabase", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.apulse.data.db.APulseDatabase getDatabase(@org.jetbrains.annotations.NotNull
        android.content.Context context) {
            return null;
        }
    }
}