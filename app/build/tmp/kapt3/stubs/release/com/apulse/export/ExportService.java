package com.apulse.export;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0002J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0002J,\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019J$\u0010\u001a\u001a\u00020\u00132\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0082@\u00a2\u0006\u0002\u0010\u001dJ$\u0010\u001e\u001a\u00020\u00132\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0082@\u00a2\u0006\u0002\u0010\u001dJ$\u0010\u001f\u001a\u00020\u00132\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0082@\u00a2\u0006\u0002\u0010\u001dJ$\u0010 \u001a\u00020\u00132\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0082@\u00a2\u0006\u0002\u0010\u001dJ$\u0010!\u001a\u00020\u00132\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0082@\u00a2\u0006\u0002\u0010\u001dJ\u001c\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001c0\f2\u0006\u0010\u0014\u001a\u00020\u0015H\u0082@\u00a2\u0006\u0002\u0010#J&\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\'\u001a\u00020(H\u0082@\u00a2\u0006\u0002\u0010)R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006*"}, d2 = {"Lcom/apulse/export/ExportService;", "", "database", "Lcom/apulse/data/db/APulseDatabase;", "context", "Landroid/content/Context;", "(Lcom/apulse/data/db/APulseDatabase;Landroid/content/Context;)V", "json", "Lkotlinx/serialization/json/Json;", "calculateSessionStats", "Lcom/apulse/export/model/APulseSessionStats;", "requests", "", "Lcom/apulse/export/RequestWithFullData;", "convertToHAREntry", "Lcom/apulse/export/model/HAREntry;", "requestData", "exportSessions", "Lkotlin/Result;", "", "options", "Lcom/apulse/export/model/ExportOptions;", "outputUri", "Landroid/net/Uri;", "exportSessions-0E7RQCE", "(Lcom/apulse/export/model/ExportOptions;Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportToAPulseFull", "sessions", "Lcom/apulse/export/SessionWithFullData;", "(Ljava/util/List;Lcom/apulse/export/model/ExportOptions;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exportToAPulseLight", "exportToCSV", "exportToHAR", "exportToJSON", "getSessionsToExport", "(Lcom/apulse/export/model/ExportOptions;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "writeToFile", "", "data", "format", "Lcom/apulse/export/model/ExportFormat;", "(Ljava/lang/String;Landroid/net/Uri;Lcom/apulse/export/model/ExportFormat;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_release"})
public final class ExportService {
    @org.jetbrains.annotations.NotNull
    private final com.apulse.data.db.APulseDatabase database = null;
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.serialization.json.Json json = null;
    
    @javax.inject.Inject
    public ExportService(@org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database, @org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    private final java.lang.Object getSessionsToExport(com.apulse.export.model.ExportOptions options, kotlin.coroutines.Continuation<? super java.util.List<com.apulse.export.SessionWithFullData>> $completion) {
        return null;
    }
    
    private final java.lang.Object exportToAPulseFull(java.util.List<com.apulse.export.SessionWithFullData> sessions, com.apulse.export.model.ExportOptions options, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final java.lang.Object exportToAPulseLight(java.util.List<com.apulse.export.SessionWithFullData> sessions, com.apulse.export.model.ExportOptions options, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final java.lang.Object exportToHAR(java.util.List<com.apulse.export.SessionWithFullData> sessions, com.apulse.export.model.ExportOptions options, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final com.apulse.export.model.HAREntry convertToHAREntry(com.apulse.export.RequestWithFullData requestData) {
        return null;
    }
    
    private final java.lang.Object exportToJSON(java.util.List<com.apulse.export.SessionWithFullData> sessions, com.apulse.export.model.ExportOptions options, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final java.lang.Object exportToCSV(java.util.List<com.apulse.export.SessionWithFullData> sessions, com.apulse.export.model.ExportOptions options, kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    private final com.apulse.export.model.APulseSessionStats calculateSessionStats(java.util.List<com.apulse.export.RequestWithFullData> requests) {
        return null;
    }
    
    private final java.lang.Object writeToFile(java.lang.String data, android.net.Uri outputUri, com.apulse.export.model.ExportFormat format, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}