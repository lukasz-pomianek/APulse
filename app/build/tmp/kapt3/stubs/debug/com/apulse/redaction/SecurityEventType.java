package com.apulse.redaction;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\b\u00a8\u0006\t"}, d2 = {"Lcom/apulse/redaction/SecurityEventType;", "", "(Ljava/lang/String;I)V", "REQUEST_BLOCKED", "SENSITIVE_DATA_DETECTED", "REDACTION_APPLIED", "EXPORT_BLOCKED", "POLICY_VIOLATION", "DATA_RETENTION_CLEANUP", "app_debug"})
public enum SecurityEventType {
    /*public static final*/ REQUEST_BLOCKED /* = new REQUEST_BLOCKED() */,
    /*public static final*/ SENSITIVE_DATA_DETECTED /* = new SENSITIVE_DATA_DETECTED() */,
    /*public static final*/ REDACTION_APPLIED /* = new REDACTION_APPLIED() */,
    /*public static final*/ EXPORT_BLOCKED /* = new EXPORT_BLOCKED() */,
    /*public static final*/ POLICY_VIOLATION /* = new POLICY_VIOLATION() */,
    /*public static final*/ DATA_RETENTION_CLEANUP /* = new DATA_RETENTION_CLEANUP() */;
    
    SecurityEventType() {
    }
    
    @org.jetbrains.annotations.NotNull
    public static kotlin.enums.EnumEntries<com.apulse.redaction.SecurityEventType> getEntries() {
        return null;
    }
}