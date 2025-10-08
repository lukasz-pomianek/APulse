package com.apulse.redaction;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 ,2\u00020\u0001:\u0001,B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0002J\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u000eJ\u0006\u0010\u0018\u001a\u00020\u0019J\u001e\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\u0006\u0010\u001d\u001a\u00020\u00192\u0006\u0010\u001e\u001a\u00020\u000eH\u0002J\u000e\u0010\u001f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u0016\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u00132\u0006\u0010#\u001a\u00020$J*\u0010%\u001a\u00020!2\u0006\u0010\u0014\u001a\u00020\u00132\u0006\u0010&\u001a\u00020\u00132\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00130(J\u000e\u0010)\u001a\u00020\f2\u0006\u0010\u001d\u001a\u00020\u0019J\f\u0010*\u001a\b\u0012\u0004\u0012\u00020+0\u001bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/apulse/redaction/SecurityPolicyManager;", "", "context", "Landroid/content/Context;", "redactionEngine", "Lcom/apulse/redaction/RedactionEngine;", "(Landroid/content/Context;Lcom/apulse/redaction/RedactionEngine;)V", "json", "Lkotlinx/serialization/json/Json;", "prefs", "Landroid/content/SharedPreferences;", "applyComplianceMode", "", "mode", "Lcom/apulse/redaction/ComplianceMode;", "auditSecurityEvent", "event", "Lcom/apulse/redaction/SecurityEvent;", "extractHostFromUrl", "", "url", "generateComplianceReport", "Lcom/apulse/redaction/ComplianceReport;", "getComplianceMode", "getCurrentPolicy", "Lcom/apulse/redaction/SecurityPolicy;", "identifyRisks", "", "Lcom/apulse/redaction/SecurityRisk;", "policy", "complianceMode", "setComplianceMode", "shouldAllowExport", "Lcom/apulse/redaction/SecurityDecision;", "format", "includesBodies", "", "shouldCaptureRequest", "method", "headers", "", "updatePolicy", "validateDataRetention", "Lcom/apulse/redaction/RetentionViolation;", "Companion", "app_debug"})
public final class SecurityPolicyManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.apulse.redaction.RedactionEngine redactionEngine = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_NAME = "apulse_security_policy";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_CURRENT_POLICY = "current_policy";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_POLICY_VERSION = "policy_version";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_COMPLIANCE_MODE = "compliance_mode";
    @org.jetbrains.annotations.NotNull
    private static final com.apulse.redaction.SecurityPolicy DEFAULT_POLICY = null;
    @org.jetbrains.annotations.NotNull
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.serialization.json.Json json = null;
    @org.jetbrains.annotations.NotNull
    public static final com.apulse.redaction.SecurityPolicyManager.Companion Companion = null;
    
    @javax.inject.Inject
    public SecurityPolicyManager(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.apulse.redaction.RedactionEngine redactionEngine) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.redaction.SecurityPolicy getCurrentPolicy() {
        return null;
    }
    
    public final void updatePolicy(@org.jetbrains.annotations.NotNull
    com.apulse.redaction.SecurityPolicy policy) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.redaction.ComplianceMode getComplianceMode() {
        return null;
    }
    
    public final void setComplianceMode(@org.jetbrains.annotations.NotNull
    com.apulse.redaction.ComplianceMode mode) {
    }
    
    private final void applyComplianceMode(com.apulse.redaction.ComplianceMode mode) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.redaction.SecurityDecision shouldCaptureRequest(@org.jetbrains.annotations.NotNull
    java.lang.String url, @org.jetbrains.annotations.NotNull
    java.lang.String method, @org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, java.lang.String> headers) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.redaction.SecurityDecision shouldAllowExport(@org.jetbrains.annotations.NotNull
    java.lang.String format, boolean includesBodies) {
        return null;
    }
    
    public final void auditSecurityEvent(@org.jetbrains.annotations.NotNull
    com.apulse.redaction.SecurityEvent event) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.apulse.redaction.RetentionViolation> validateDataRetention() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.redaction.ComplianceReport generateComplianceReport() {
        return null;
    }
    
    private final java.util.List<com.apulse.redaction.SecurityRisk> identifyRisks(com.apulse.redaction.SecurityPolicy policy, com.apulse.redaction.ComplianceMode complianceMode) {
        return null;
    }
    
    private final java.lang.String extractHostFromUrl(java.lang.String url) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/apulse/redaction/SecurityPolicyManager$Companion;", "", "()V", "DEFAULT_POLICY", "Lcom/apulse/redaction/SecurityPolicy;", "getDEFAULT_POLICY", "()Lcom/apulse/redaction/SecurityPolicy;", "KEY_COMPLIANCE_MODE", "", "KEY_CURRENT_POLICY", "KEY_POLICY_VERSION", "PREFS_NAME", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.apulse.redaction.SecurityPolicy getDEFAULT_POLICY() {
            return null;
        }
    }
}