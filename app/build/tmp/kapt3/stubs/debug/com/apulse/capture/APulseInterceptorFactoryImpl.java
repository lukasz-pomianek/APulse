package com.apulse.capture;

/**
 * Factory implementation that creates the actual APulseInterceptor
 * This is in the main app module which has access to all dependencies
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2 = {"Lcom/apulse/capture/APulseInterceptorFactoryImpl;", "Lcom/apulse/core/APulseInterceptorFactory;", "()V", "createInterceptor", "Lokhttp3/Interceptor;", "context", "Landroid/content/Context;", "config", "Lcom/apulse/core/APulseConfig;", "app_debug"})
public final class APulseInterceptorFactoryImpl implements com.apulse.core.APulseInterceptorFactory {
    
    public APulseInterceptorFactoryImpl() {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public okhttp3.Interceptor createInterceptor(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.apulse.core.APulseConfig config) {
        return null;
    }
}