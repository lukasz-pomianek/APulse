package com.apulse.export;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0002J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fH\u0002J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\fH\u0002J\u0012\u0010\u0010\u001a\u0004\u0018\u00010\f2\u0006\u0010\u000e\u001a\u00020\fH\u0002J\u001e\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0082@\u00a2\u0006\u0002\u0010\u0015J\u001e\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0082@\u00a2\u0006\u0002\u0010\u0015J.\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00120\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u0014H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u001e\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0082@\u00a2\u0006\u0002\u0010\u0015J\u001e\u0010\u001f\u001a\u00020\u00122\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0082@\u00a2\u0006\u0002\u0010\u0015J\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\fH\u0002J\u0016\u0010#\u001a\u00020\f2\u0006\u0010$\u001a\u00020%H\u0082@\u00a2\u0006\u0002\u0010&R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\'"}, d2 = {"Lcom/apulse/export/ImportService;", "", "database", "Lcom/apulse/data/db/APulseDatabase;", "context", "Landroid/content/Context;", "(Lcom/apulse/data/db/APulseDatabase;Landroid/content/Context;)V", "json", "Lkotlinx/serialization/json/Json;", "detectFormat", "Lcom/apulse/export/DetectedFormat;", "content", "", "extractHostFromUrl", "url", "extractPathFromUrl", "extractQueryFromUrl", "importAPulseFull", "Lcom/apulse/export/ImportResult;", "options", "Lcom/apulse/export/ImportOptions;", "(Ljava/lang/String;Lcom/apulse/export/ImportOptions;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "importAPulseLight", "importFromUri", "Lkotlin/Result;", "inputUri", "Landroid/net/Uri;", "importOptions", "importFromUri-0E7RQCE", "(Landroid/net/Uri;Lcom/apulse/export/ImportOptions;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "importHAR", "parseAndImport", "parseHARDateTime", "Lkotlinx/datetime/Instant;", "dateTimeString", "readAPulseZipFile", "inputStream", "Ljava/io/InputStream;", "(Ljava/io/InputStream;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public final class ImportService {
    @org.jetbrains.annotations.NotNull
    private final com.apulse.data.db.APulseDatabase database = null;
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.serialization.json.Json json = null;
    
    @javax.inject.Inject
    public ImportService(@org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database, @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    private final java.lang.Object readAPulseZipFile(java.io.InputStream inputStream, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final java.lang.Object parseAndImport(java.lang.String content, com.apulse.export.ImportOptions options, kotlin.coroutines.Continuation<? super com.apulse.export.ImportResult> $completion) {
        return null;
    }
    
    private final com.apulse.export.DetectedFormat detectFormat(java.lang.String content) {
        return null;
    }
    
    private final java.lang.Object importAPulseFull(java.lang.String content, com.apulse.export.ImportOptions options, kotlin.coroutines.Continuation<? super com.apulse.export.ImportResult> $completion) {
        return null;
    }
    
    private final java.lang.Object importAPulseLight(java.lang.String content, com.apulse.export.ImportOptions options, kotlin.coroutines.Continuation<? super com.apulse.export.ImportResult> $completion) {
        return null;
    }
    
    private final java.lang.Object importHAR(java.lang.String content, com.apulse.export.ImportOptions options, kotlin.coroutines.Continuation<? super com.apulse.export.ImportResult> $completion) {
        return null;
    }
    
    private final java.lang.String extractHostFromUrl(java.lang.String url) {
        return null;
    }
    
    private final java.lang.String extractPathFromUrl(java.lang.String url) {
        return null;
    }
    
    private final java.lang.String extractQueryFromUrl(java.lang.String url) {
        return null;
    }
    
    private final kotlinx.datetime.Instant parseHARDateTime(java.lang.String dateTimeString) {
        return null;
    }
}