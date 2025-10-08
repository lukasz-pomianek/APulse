package com.apulse.capture.webview;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/apulse/capture/webview/WebViewNetworkCapture;", "", "database", "Lcom/apulse/data/db/APulseDatabase;", "captureSettings", "Lcom/apulse/capture/interceptor/CaptureSettings;", "(Lcom/apulse/data/db/APulseDatabase;Lcom/apulse/capture/interceptor/CaptureSettings;)V", "scope", "Lkotlinx/coroutines/CoroutineScope;", "createWebViewClient", "Landroid/webkit/WebViewClient;", "APulseWebViewClient", "app_release"})
public final class WebViewNetworkCapture {
    @org.jetbrains.annotations.NotNull
    private final com.apulse.data.db.APulseDatabase database = null;
    @org.jetbrains.annotations.NotNull
    private final com.apulse.capture.interceptor.CaptureSettings captureSettings = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope scope = null;
    
    @javax.inject.Inject
    public WebViewNetworkCapture(@org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database, @org.jetbrains.annotations.NotNull
    com.apulse.capture.interceptor.CaptureSettings captureSettings) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.webkit.WebViewClient createWebViewClient() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u000e\u0010\u0007\u001a\u00020\bH\u0082@\u00a2\u0006\u0002\u0010\tJ\u001e\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\r2\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0016\u00a8\u0006\u000e"}, d2 = {"Lcom/apulse/capture/webview/WebViewNetworkCapture$APulseWebViewClient;", "Landroid/webkit/WebViewClient;", "(Lcom/apulse/capture/webview/WebViewNetworkCapture;)V", "captureWebRequest", "", "request", "Landroid/webkit/WebResourceRequest;", "getOrCreateCurrentSession", "Lcom/apulse/data/model/Session;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "shouldInterceptRequest", "Landroid/webkit/WebResourceResponse;", "view", "Landroid/webkit/WebView;", "app_release"})
    final class APulseWebViewClient extends android.webkit.WebViewClient {
        
        public APulseWebViewClient() {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.Nullable
        public android.webkit.WebResourceResponse shouldInterceptRequest(@org.jetbrains.annotations.Nullable
        android.webkit.WebView view, @org.jetbrains.annotations.Nullable
        android.webkit.WebResourceRequest request) {
            return null;
        }
        
        private final void captureWebRequest(android.webkit.WebResourceRequest request) {
        }
        
        private final java.lang.Object getOrCreateCurrentSession(kotlin.coroutines.Continuation<? super com.apulse.data.model.Session> $completion) {
            return null;
        }
    }
}