package com.apulse.ui.settings;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\b\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0013\u001a\u00020\u0014J\u0006\u0010\u0015\u001a\u00020\u0014J\u000e\u0010\u0016\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\tJ\u000e\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\tJ\u000e\u0010\u0019\u001a\u00020\u00142\u0006\u0010\u0017\u001a\u00020\tJ\u000e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u0011R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000bR\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000b\u00a8\u0006\u001c"}, d2 = {"Lcom/apulse/ui/settings/SettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "captureSettings", "Lcom/apulse/capture/interceptor/CaptureSettings;", "database", "Lcom/apulse/data/db/APulseDatabase;", "(Lcom/apulse/capture/interceptor/CaptureSettings;Lcom/apulse/data/db/APulseDatabase;)V", "autoRedactAuth", "Lkotlinx/coroutines/flow/StateFlow;", "", "getAutoRedactAuth", "()Lkotlinx/coroutines/flow/StateFlow;", "autoRedactCookies", "getAutoRedactCookies", "captureEnabled", "getCaptureEnabled", "maxBodySize", "", "getMaxBodySize", "clearAllData", "", "exportAllSessions", "setAutoRedactAuth", "enabled", "setAutoRedactCookies", "setCaptureEnabled", "setMaxBodySize", "size", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel
public final class SettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull
    private final com.apulse.capture.interceptor.CaptureSettings captureSettings = null;
    @org.jetbrains.annotations.NotNull
    private final com.apulse.data.db.APulseDatabase database = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> captureEnabled = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Long> maxBodySize = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> autoRedactAuth = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> autoRedactCookies = null;
    
    @javax.inject.Inject
    public SettingsViewModel(@org.jetbrains.annotations.NotNull
    com.apulse.capture.interceptor.CaptureSettings captureSettings, @org.jetbrains.annotations.NotNull
    com.apulse.data.db.APulseDatabase database) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getCaptureEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Long> getMaxBodySize() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getAutoRedactAuth() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.StateFlow<java.lang.Boolean> getAutoRedactCookies() {
        return null;
    }
    
    public final void setCaptureEnabled(boolean enabled) {
    }
    
    public final void setMaxBodySize(long size) {
    }
    
    public final void setAutoRedactAuth(boolean enabled) {
    }
    
    public final void setAutoRedactCookies(boolean enabled) {
    }
    
    public final void clearAllData() {
    }
    
    public final void exportAllSessions() {
    }
}