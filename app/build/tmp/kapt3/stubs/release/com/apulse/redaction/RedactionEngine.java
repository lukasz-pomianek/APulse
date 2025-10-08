package com.apulse.redaction;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u0000 +2\u00020\u0001:\u0001+B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ\u0006\u0010\r\u001a\u00020\u000eJ\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u0010J\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\f0\u0010H\u0002J\u000e\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000eJ\u001a\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u000e2\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u000eJ&\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\u001c2\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\u001cJ\u000e\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u000eJ\u000e\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\u000eJ\u0016\u0010\"\u001a\u00020\n2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\f0\u0010H\u0002J\u0014\u0010$\u001a\u00020\n2\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012J\u0016\u0010&\u001a\u00020\'2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010(\u001a\u00020\u000eJ\u000e\u0010)\u001a\u00020*2\u0006\u0010\u000b\u001a\u00020\fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006,"}, d2 = {"Lcom/apulse/redaction/RedactionEngine;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "json", "Lkotlinx/serialization/json/Json;", "prefs", "Landroid/content/SharedPreferences;", "addCustomRule", "", "rule", "Lcom/apulse/redaction/RedactionRule;", "exportRules", "", "getCustomRules", "", "getEnabledCategories", "", "Lcom/apulse/redaction/RedactionCategory;", "getEnabledRules", "importRules", "Lcom/apulse/redaction/ImportRulesResult;", "rulesJson", "redactBody", "body", "contentType", "redactHeaders", "", "headers", "redactUrl", "url", "removeCustomRule", "ruleName", "saveCustomRules", "rules", "setEnabledCategories", "categories", "testRule", "Lcom/apulse/redaction/RedactionTestResult;", "testInput", "validateRule", "Lcom/apulse/redaction/RedactionValidationResult;", "Companion", "app_release"})
public final class RedactionEngine {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String PREFS_NAME = "apulse_redaction_rules";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_CUSTOM_RULES = "custom_rules";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEY_ENABLED_CATEGORIES = "enabled_categories";
    @org.jetbrains.annotations.NotNull
    private static final java.util.Map<com.apulse.redaction.RedactionCategory, java.util.List<com.apulse.redaction.RedactionRule>> DEFAULT_PATTERNS = null;
    @org.jetbrains.annotations.NotNull
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.serialization.json.Json json = null;
    @org.jetbrains.annotations.NotNull
    public static final com.apulse.redaction.RedactionEngine.Companion Companion = null;
    
    @javax.inject.Inject
    public RedactionEngine(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.String> redactHeaders(@org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, java.lang.String> headers) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String redactBody(@org.jetbrains.annotations.NotNull
    java.lang.String body, @org.jetbrains.annotations.Nullable
    java.lang.String contentType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String redactUrl(@org.jetbrains.annotations.NotNull
    java.lang.String url) {
        return null;
    }
    
    public final void addCustomRule(@org.jetbrains.annotations.NotNull
    com.apulse.redaction.RedactionRule rule) {
    }
    
    public final void removeCustomRule(@org.jetbrains.annotations.NotNull
    java.lang.String ruleName) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.apulse.redaction.RedactionRule> getCustomRules() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Set<com.apulse.redaction.RedactionCategory> getEnabledCategories() {
        return null;
    }
    
    public final void setEnabledCategories(@org.jetbrains.annotations.NotNull
    java.util.Set<? extends com.apulse.redaction.RedactionCategory> categories) {
    }
    
    private final void saveCustomRules(java.util.List<com.apulse.redaction.RedactionRule> rules) {
    }
    
    private final java.util.List<com.apulse.redaction.RedactionRule> getEnabledRules() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.redaction.RedactionValidationResult validateRule(@org.jetbrains.annotations.NotNull
    com.apulse.redaction.RedactionRule rule) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.redaction.RedactionTestResult testRule(@org.jetbrains.annotations.NotNull
    com.apulse.redaction.RedactionRule rule, @org.jetbrains.annotations.NotNull
    java.lang.String testInput) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String exportRules() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.redaction.ImportRulesResult importRules(@org.jetbrains.annotations.NotNull
    java.lang.String rulesJson) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R \u0010\u0003\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/apulse/redaction/RedactionEngine$Companion;", "", "()V", "DEFAULT_PATTERNS", "", "Lcom/apulse/redaction/RedactionCategory;", "", "Lcom/apulse/redaction/RedactionRule;", "KEY_CUSTOM_RULES", "", "KEY_ENABLED_CATEGORIES", "PREFS_NAME", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}