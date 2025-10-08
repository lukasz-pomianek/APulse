package com.apulse.capture.interceptor;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0010\b\u0007\u0018\u0000 52\u00020\u0001:\u00015B!\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aJ\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aJ\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001eJ\u0006\u0010\u001f\u001a\u00020\nJ\u000e\u0010 \u001a\u00020\u001b2\u0006\u0010!\u001a\u00020\u001bJ\u0016\u0010\"\u001a\u00020\u001b2\u0006\u0010#\u001a\u00020\u001b2\u0006\u0010$\u001a\u00020\u001bJ\u0014\u0010%\u001a\u00020&2\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aJ\u000e\u0010(\u001a\u00020&2\u0006\u0010)\u001a\u00020\nJ\u000e\u0010*\u001a\u00020&2\u0006\u0010)\u001a\u00020\nJ\u0014\u0010+\u001a\u00020&2\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001aJ\u000e\u0010,\u001a\u00020&2\u0006\u0010)\u001a\u00020\nJ\u0014\u0010-\u001a\u00020&2\f\u0010.\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001eJ\u000e\u0010/\u001a\u00020&2\u0006\u00100\u001a\u00020\u0010J\u000e\u00101\u001a\u00020&2\u0006\u00102\u001a\u00020\u0014J\u000e\u00103\u001a\u00020\n2\u0006\u00104\u001a\u00020\u001bR\u0011\u0010\t\u001a\u00020\n8F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\n8F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000f\u001a\u00020\u00108F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00148F\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00066"}, d2 = {"Lcom/apulse/capture/interceptor/CaptureSettings;", "", "context", "Landroid/content/Context;", "redactionEngine", "Lcom/apulse/redaction/RedactionEngine;", "securityPolicyManager", "Lcom/apulse/redaction/SecurityPolicyManager;", "(Landroid/content/Context;Lcom/apulse/redaction/RedactionEngine;Lcom/apulse/redaction/SecurityPolicyManager;)V", "autoRedactAuth", "", "getAutoRedactAuth", "()Z", "autoRedactCookies", "getAutoRedactCookies", "maxBodySize", "", "getMaxBodySize", "()J", "maxRequestsPerSession", "", "getMaxRequestsPerSession", "()I", "prefs", "Landroid/content/SharedPreferences;", "getAllowedHosts", "", "", "getBlockedHosts", "getCustomRedactionRules", "", "isCaptureEnabled", "redactBodyIfNeeded", "body", "redactHeaderIfNeeded", "name", "value", "setAllowedHosts", "", "hosts", "setAutoRedactAuth", "enabled", "setAutoRedactCookies", "setBlockedHosts", "setCaptureEnabled", "setCustomRedactionRules", "rules", "setMaxBodySize", "size", "setMaxRequestsPerSession", "count", "shouldCaptureUrl", "url", "Companion", "app_release"})
public final class CaptureSettings {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.apulse.redaction.RedactionEngine redactionEngine = null;
    @org.jetbrains.annotations.NotNull
    private final com.apulse.redaction.SecurityPolicyManager securityPolicyManager = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_NAME = "apulse_capture_settings";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_CAPTURE_ENABLED = "capture_enabled";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_MAX_BODY_SIZE = "max_body_size";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_MAX_REQUESTS_PER_SESSION = "max_requests_per_session";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_AUTO_REDACT_AUTH = "auto_redact_auth";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_AUTO_REDACT_COOKIES = "auto_redact_cookies";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_BLOCKED_HOSTS = "blocked_hosts";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_ALLOWED_HOSTS = "allowed_hosts";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_CUSTOM_REDACTION_RULES = "custom_redaction_rules";
    private static final long DEFAULT_MAX_BODY_SIZE = 10485760L;
    private static final int DEFAULT_MAX_REQUESTS = 10000;
    @org.jetbrains.annotations.NotNull
    private static final java.util.Set<java.lang.String> SENSITIVE_HEADERS = null;
    @org.jetbrains.annotations.NotNull
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull
    public static final com.apulse.capture.interceptor.CaptureSettings.Companion Companion = null;
    
    @javax.inject.Inject
    public CaptureSettings(@dagger.hilt.android.qualifiers.ApplicationContext
    @org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.apulse.redaction.RedactionEngine redactionEngine, @org.jetbrains.annotations.NotNull
    com.apulse.redaction.SecurityPolicyManager securityPolicyManager) {
        super();
    }
    
    public final boolean isCaptureEnabled() {
        return false;
    }
    
    public final void setCaptureEnabled(boolean enabled) {
    }
    
    public final long getMaxBodySize() {
        return 0L;
    }
    
    public final void setMaxBodySize(long size) {
    }
    
    public final int getMaxRequestsPerSession() {
        return 0;
    }
    
    public final void setMaxRequestsPerSession(int count) {
    }
    
    public final boolean getAutoRedactAuth() {
        return false;
    }
    
    public final void setAutoRedactAuth(boolean enabled) {
    }
    
    public final boolean getAutoRedactCookies() {
        return false;
    }
    
    public final void setAutoRedactCookies(boolean enabled) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Set<java.lang.String> getBlockedHosts() {
        return null;
    }
    
    public final void setBlockedHosts(@org.jetbrains.annotations.NotNull
    java.util.Set<java.lang.String> hosts) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Set<java.lang.String> getAllowedHosts() {
        return null;
    }
    
    public final void setAllowedHosts(@org.jetbrains.annotations.NotNull
    java.util.Set<java.lang.String> hosts) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getCustomRedactionRules() {
        return null;
    }
    
    public final void setCustomRedactionRules(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> rules) {
    }
    
    public final boolean shouldCaptureUrl(@org.jetbrains.annotations.NotNull
    java.lang.String url) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String redactHeaderIfNeeded(@org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.NotNull
    java.lang.String value) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String redactBodyIfNeeded(@org.jetbrains.annotations.NotNull
    java.lang.String body) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\"\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\b0\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lcom/apulse/capture/interceptor/CaptureSettings$Companion;", "", "()V", "DEFAULT_MAX_BODY_SIZE", "", "DEFAULT_MAX_REQUESTS", "", "KEY_ALLOWED_HOSTS", "", "KEY_AUTO_REDACT_AUTH", "KEY_AUTO_REDACT_COOKIES", "KEY_BLOCKED_HOSTS", "KEY_CAPTURE_ENABLED", "KEY_CUSTOM_REDACTION_RULES", "KEY_MAX_BODY_SIZE", "KEY_MAX_REQUESTS_PER_SESSION", "PREFS_NAME", "SENSITIVE_HEADERS", "", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}