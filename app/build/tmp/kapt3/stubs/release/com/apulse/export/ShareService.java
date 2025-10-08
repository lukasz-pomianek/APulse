package com.apulse.export;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\b\b\u0007\u0018\u0000 /2\u00020\u0001:\u0001/B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\"\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\n2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\nH\u0086@\u00a2\u0006\u0002\u0010\u0011J\u0018\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\n2\b\b\u0002\u0010\u0013\u001a\u00020\nJ \u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0015\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\nH\u0002J\u000e\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u0017\u001a\u00020\nJ\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\t\u001a\u00020\nH\u0002J@\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\b0\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\u001d2\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001fJ&\u0010 \u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010!\u001a\u00020\"2\n\b\u0002\u0010#\u001a\u0004\u0018\u00010\nH\u0002J\u0016\u0010$\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\nH\u0086@\u00a2\u0006\u0002\u0010%J,\u0010&\u001a\u00020\u000e2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\'\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020(H\u0086@\u00a2\u0006\u0002\u0010*J4\u0010+\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\'\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020(H\u0086@\u00a2\u0006\u0002\u0010,J:\u0010-\u001a\u00020\u000e2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\n0\u001d2\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\'\u001a\u00020(2\b\b\u0002\u0010)\u001a\u00020(H\u0086@\u00a2\u0006\u0002\u0010.R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u00060"}, d2 = {"Lcom/apulse/export/ShareService;", "", "context", "Landroid/content/Context;", "exportService", "Lcom/apulse/export/ExportService;", "(Landroid/content/Context;Lcom/apulse/export/ExportService;)V", "createDownloadsUri", "Landroid/net/Uri;", "fileName", "", "format", "Lcom/apulse/export/model/ExportFormat;", "createEmailShareIntent", "Landroid/content/Intent;", "sessionId", "recipientEmail", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createQuickShareIntent", "title", "createShareIntent", "uri", "createSlackShareIntent", "sessionSummary", "createTempFile", "Ljava/io/File;", "exportToDownloads", "Lkotlin/Result;", "sessionIds", "", "exportToDownloads-BWLJW6A", "(Ljava/util/List;Lcom/apulse/export/model/ExportFormat;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "generateFileName", "sessionCount", "", "suffix", "generateSessionQRData", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "shareAllSessions", "includeHeaders", "", "includeBodies", "(Lcom/apulse/export/model/ExportFormat;ZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "shareSession", "(Ljava/lang/String;Lcom/apulse/export/model/ExportFormat;ZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "shareSessions", "(Ljava/util/List;Lcom/apulse/export/model/ExportFormat;ZZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_release"})
public final class ShareService {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.apulse.export.ExportService exportService = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String AUTHORITY = "com.apulse.fileprovider";
    @org.jetbrains.annotations.NotNull
    public static final com.apulse.export.ShareService.Companion Companion = null;
    
    @javax.inject.Inject
    public ShareService(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.apulse.export.ExportService exportService) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object shareSession(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    com.apulse.export.model.ExportFormat format, boolean includeHeaders, boolean includeBodies, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super android.content.Intent> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object shareSessions(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> sessionIds, @org.jetbrains.annotations.NotNull
    com.apulse.export.model.ExportFormat format, boolean includeHeaders, boolean includeBodies, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super android.content.Intent> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object shareAllSessions(@org.jetbrains.annotations.NotNull
    com.apulse.export.model.ExportFormat format, boolean includeHeaders, boolean includeBodies, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super android.content.Intent> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.content.Intent createQuickShareIntent(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    java.lang.String title) {
        return null;
    }
    
    private final java.lang.String generateFileName(com.apulse.export.model.ExportFormat format, int sessionCount, java.lang.String suffix) {
        return null;
    }
    
    private final java.io.File createTempFile(java.lang.String fileName) {
        return null;
    }
    
    private final android.net.Uri createDownloadsUri(java.lang.String fileName, com.apulse.export.model.ExportFormat format) {
        return null;
    }
    
    private final android.content.Intent createShareIntent(android.net.Uri uri, com.apulse.export.model.ExportFormat format, java.lang.String fileName) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object generateSessionQRData(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object createEmailShareIntent(@org.jetbrains.annotations.NotNull
    java.lang.String sessionId, @org.jetbrains.annotations.Nullable
    java.lang.String recipientEmail, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super android.content.Intent> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.content.Intent createSlackShareIntent(@org.jetbrains.annotations.NotNull
    java.lang.String sessionSummary) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/apulse/export/ShareService$Companion;", "", "()V", "AUTHORITY", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}