package com.apulse.di;

@dagger.Module
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\t\u001a\u00020\u0004H\u0007\u00a8\u0006\u0016"}, d2 = {"Lcom/apulse/di/DatabaseModule;", "", "()V", "provideAPulseDatabase", "Lcom/apulse/data/db/APulseDatabase;", "context", "Landroid/content/Context;", "provideAppMetadataDao", "Lcom/apulse/data/db/AppMetadataDao;", "database", "provideNetworkRequestDao", "Lcom/apulse/data/db/NetworkRequestDao;", "provideRequestBodyDao", "Lcom/apulse/data/db/RequestBodyDao;", "provideRequestHeadersDao", "Lcom/apulse/data/db/RequestHeadersDao;", "provideResponseBodyDao", "Lcom/apulse/data/db/ResponseBodyDao;", "provideResponseHeadersDao", "Lcom/apulse/data/db/ResponseHeadersDao;", "provideSessionDao", "Lcom/apulse/data/db/SessionDao;", "app_release"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class DatabaseModule {
    @org.jetbrains.annotations.NotNull
    public static final com.apulse.di.DatabaseModule INSTANCE = null;
    
    private DatabaseModule() {
        super();
    }
    
    @dagger.Provides
    @javax.inject.Singleton
    @org.jetbrains.annotations.NotNull
    public final com.apulse.data.db.APulseDatabase provideAPulseDatabase(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.apulse.data.db.SessionDao provideSessionDao(@org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.apulse.data.db.NetworkRequestDao provideNetworkRequestDao(@org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.apulse.data.db.RequestHeadersDao provideRequestHeadersDao(@org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.apulse.data.db.ResponseHeadersDao provideResponseHeadersDao(@org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.apulse.data.db.RequestBodyDao provideRequestBodyDao(@org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.apulse.data.db.ResponseBodyDao provideResponseBodyDao(@org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database) {
        return null;
    }
    
    @dagger.Provides
    @org.jetbrains.annotations.NotNull
    public final com.apulse.data.db.AppMetadataDao provideAppMetadataDao(@org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database) {
        return null;
    }
}