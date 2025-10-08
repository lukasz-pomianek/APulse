package com.apulse.ui.requests;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000>\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\u0010\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000bH\u0002\u001a\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002\u001a\u0015\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\tH\u0003\u00a2\u0006\u0002\u0010\u0014\u001a\u0015\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0017H\u0003\u00a2\u0006\u0002\u0010\u0018\u00a8\u0006\u0019"}, d2 = {"RequestItem", "", "request", "Lcom/apulse/data/model/NetworkRequest;", "onClick", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "formatDuration", "", "durationMs", "", "formatSize", "bytes", "formatTime", "instant", "Lkotlinx/datetime/Instant;", "getMethodColor", "Landroidx/compose/ui/graphics/Color;", "method", "(Ljava/lang/String;)J", "getStatusColor", "statusCode", "", "(I)J", "app_release"})
public final class RequestItemKt {
    
    @androidx.compose.runtime.Composable
    public static final void RequestItem(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.NetworkRequest request, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    private static final long getMethodColor(java.lang.String method) {
        return 0L;
    }
    
    @androidx.compose.runtime.Composable
    private static final long getStatusColor(int statusCode) {
        return 0L;
    }
    
    private static final java.lang.String formatTime(kotlinx.datetime.Instant instant) {
        return null;
    }
    
    private static final java.lang.String formatDuration(long durationMs) {
        return null;
    }
    
    private static final java.lang.String formatSize(long bytes) {
        return null;
    }
}