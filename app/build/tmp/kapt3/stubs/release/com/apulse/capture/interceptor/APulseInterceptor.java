package com.apulse.capture.interceptor;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u0000 .2\u00020\u0001:\u0001.B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J \u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u001d\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0002\u00a2\u0006\u0002\u0010\u0017J\u001a\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u001a\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u001a\u0010\u001e\u001a\u0004\u0018\u00010\u001f2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010 \u001a\u00020!H\u0082@\u00a2\u0006\u0002\u0010\"J\u0010\u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020%H\u0016J&\u0010&\u001a\u00020\'2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010(\u001a\u00020)2\u0006\u0010\u0013\u001a\u00020\u0014H\u0082@\u00a2\u0006\u0002\u0010*J\u0016\u0010+\u001a\u00020\'2\u0006\u0010,\u001a\u00020\u000eH\u0082@\u00a2\u0006\u0002\u0010-R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/apulse/capture/interceptor/APulseInterceptor;", "Lokhttp3/Interceptor;", "context", "Landroid/content/Context;", "database", "Lcom/apulse/data/db/APulseDatabase;", "captureSettings", "Lcom/apulse/capture/interceptor/CaptureSettings;", "(Landroid/content/Context;Lcom/apulse/data/db/APulseDatabase;Lcom/apulse/capture/interceptor/CaptureSettings;)V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "createAppMetadata", "Lcom/apulse/data/model/AppMetadata;", "requestId", "", "createNetworkRequest", "Lcom/apulse/data/model/NetworkRequest;", "request", "Lokhttp3/Request;", "startTime", "Lkotlinx/datetime/Instant;", "createRequestBody", "error/NonExistentClass", "(Lokhttp3/Request;Ljava/lang/String;)Lerror/NonExistentClass;", "createRequestHeaders", "Lcom/apulse/data/model/RequestHeaders;", "createResponseBody", "Lcom/apulse/data/model/ResponseBody;", "response", "Lokhttp3/Response;", "createResponseHeaders", "Lcom/apulse/data/model/ResponseHeaders;", "getOrCreateCurrentSession", "Lcom/apulse/data/model/Session;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "intercept", "chain", "Lokhttp3/Interceptor$Chain;", "updateRequestWithError", "", "error", "Ljava/io/IOException;", "(Ljava/lang/String;Ljava/io/IOException;Lkotlinx/datetime/Instant;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateSessionStats", "sessionId", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "app_release"})
public final class APulseInterceptor implements okhttp3.Interceptor {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.apulse.data.db.APulseDatabase database = null;
    @org.jetbrains.annotations.NotNull
    private final com.apulse.capture.interceptor.CaptureSettings captureSettings = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope scope = null;
    private static final long MAX_CONTENT_LENGTH = 10485760L;
    @org.jetbrains.annotations.NotNull
    public static final com.apulse.capture.interceptor.APulseInterceptor.Companion Companion = null;
    
    @javax.inject.Inject
    public APulseInterceptor(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database, @org.jetbrains.annotations.NotNull
    com.apulse.capture.interceptor.CaptureSettings captureSettings) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public okhttp3.Response intercept(@org.jetbrains.annotations.NotNull
    okhttp3.Interceptor.Chain chain) {
        return null;
    }
    
    private final com.apulse.data.model.NetworkRequest createNetworkRequest(okhttp3.Request request, java.lang.String requestId, kotlinx.datetime.Instant startTime) {
        return null;
    }
    
    private final com.apulse.data.model.RequestHeaders createRequestHeaders(okhttp3.Request request, java.lang.String requestId) {
        return null;
    }
    
    private final error.NonExistentClass createRequestBody(okhttp3.Request request, java.lang.String requestId) {
        return null;
    }
    
    private final com.apulse.data.model.ResponseHeaders createResponseHeaders(okhttp3.Response response, java.lang.String requestId) {
        return null;
    }
    
    private final com.apulse.data.model.ResponseBody createResponseBody(okhttp3.Response response, java.lang.String requestId) {
        return null;
    }
    
    private final com.apulse.data.model.AppMetadata createAppMetadata(java.lang.String requestId) {
        return null;
    }
    
    private final java.lang.Object getOrCreateCurrentSession(kotlin.coroutines.Continuation<? super com.apulse.data.model.Session> $completion) {
        return null;
    }
    
    private final java.lang.Object updateRequestWithError(java.lang.String requestId, java.io.IOException error, kotlinx.datetime.Instant startTime, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.Object updateSessionStats(java.lang.String sessionId, kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/apulse/capture/interceptor/APulseInterceptor$Companion;", "", "()V", "MAX_CONTENT_LENGTH", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}