package com.apulse.service;

@dagger.hilt.android.AndroidEntryPoint
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0007\u0018\u0000 .2\u00020\u0001:\u0001.B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020 H\u0002J\u0014\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\b\u0010%\u001a\u00020 H\u0016J\b\u0010&\u001a\u00020 H\u0016J\"\u0010\'\u001a\u00020\u00122\b\u0010#\u001a\u0004\u0018\u00010$2\u0006\u0010(\u001a\u00020\u00122\u0006\u0010)\u001a\u00020\u0012H\u0016J\b\u0010*\u001a\u00020 H\u0002J\b\u0010+\u001a\u00020 H\u0002J\b\u0010,\u001a\u00020 H\u0002J\b\u0010-\u001a\u00020 H\u0002R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0015\u001a\u00020\u00168\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/apulse/service/NetworkCaptureService;", "Landroid/app/Service;", "()V", "captureSettings", "Lcom/apulse/capture/interceptor/CaptureSettings;", "getCaptureSettings", "()Lcom/apulse/capture/interceptor/CaptureSettings;", "setCaptureSettings", "(Lcom/apulse/capture/interceptor/CaptureSettings;)V", "database", "Lcom/apulse/data/db/APulseDatabase;", "getDatabase", "()Lcom/apulse/data/db/APulseDatabase;", "setDatabase", "(Lcom/apulse/data/db/APulseDatabase;)V", "isCapturing", "", "requestCount", "", "serviceScope", "Lkotlinx/coroutines/CoroutineScope;", "sessionManager", "Lcom/apulse/service/SessionManager;", "getSessionManager", "()Lcom/apulse/service/SessionManager;", "setSessionManager", "(Lcom/apulse/service/SessionManager;)V", "statsJob", "Lkotlinx/coroutines/Job;", "createNotification", "Landroid/app/Notification;", "createNotificationChannel", "", "onBind", "Landroid/os/IBinder;", "intent", "Landroid/content/Intent;", "onCreate", "onDestroy", "onStartCommand", "flags", "startId", "startCapture", "startMonitoring", "stopCapture", "toggleCapture", "Companion", "app_release"})
public final class NetworkCaptureService extends android.app.Service {
    private static final int NOTIFICATION_ID = 1001;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CHANNEL_ID = "apulse_capture_channel";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String CHANNEL_NAME = "APulse Network Capture";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_START_CAPTURE = "com.apulse.START_CAPTURE";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_STOP_CAPTURE = "com.apulse.STOP_CAPTURE";
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String ACTION_TOGGLE_CAPTURE = "com.apulse.TOGGLE_CAPTURE";
    @javax.inject.Inject
    public com.apulse.data.db.APulseDatabase database;
    @javax.inject.Inject
    public com.apulse.capture.interceptor.CaptureSettings captureSettings;
    @javax.inject.Inject
    public com.apulse.service.SessionManager sessionManager;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.CoroutineScope serviceScope = null;
    @org.jetbrains.annotations.Nullable
    private kotlinx.coroutines.Job statsJob;
    private int requestCount = 0;
    private boolean isCapturing = false;
    @org.jetbrains.annotations.NotNull
    public static final com.apulse.service.NetworkCaptureService.Companion Companion = null;
    
    public NetworkCaptureService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.data.db.APulseDatabase getDatabase() {
        return null;
    }
    
    public final void setDatabase(@org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.capture.interceptor.CaptureSettings getCaptureSettings() {
        return null;
    }
    
    public final void setCaptureSettings(@org.jetbrains.annotations.NotNull
    com.apulse.capture.interceptor.CaptureSettings p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.service.SessionManager getSessionManager() {
        return null;
    }
    
    public final void setSessionManager(@org.jetbrains.annotations.NotNull
    com.apulse.service.SessionManager p0) {
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable
    android.content.Intent intent) {
        return null;
    }
    
    @java.lang.Override
    public void onCreate() {
    }
    
    @java.lang.Override
    public int onStartCommand(@org.jetbrains.annotations.Nullable
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
    
    private final void createNotificationChannel() {
    }
    
    private final void startCapture() {
    }
    
    private final void stopCapture() {
    }
    
    private final void toggleCapture() {
    }
    
    private final void startMonitoring() {
    }
    
    private final android.app.Notification createNotification() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/apulse/service/NetworkCaptureService$Companion;", "", "()V", "ACTION_START_CAPTURE", "", "ACTION_STOP_CAPTURE", "ACTION_TOGGLE_CAPTURE", "CHANNEL_ID", "CHANNEL_NAME", "NOTIFICATION_ID", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}