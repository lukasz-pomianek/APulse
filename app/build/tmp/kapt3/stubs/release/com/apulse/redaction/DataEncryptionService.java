package com.apulse.redaction;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0007\u0018\u0000 02\u00020\u0001:\u00010B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J&\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00110\u00152\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00170\u0015J\u000e\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u0017J\u000e\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u001b\u001a\u00020\u0011J&\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00170\u00152\u0012\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00110\u0015J\u000e\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u0011J\b\u0010 \u001a\u00020\u000fH\u0002J\u0010\u0010!\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#J\u0006\u0010$\u001a\u00020%J\b\u0010&\u001a\u00020\'H\u0002J\u000e\u0010(\u001a\u00020\u00112\u0006\u0010)\u001a\u00020\u0011J\b\u0010*\u001a\u00020\u000fH\u0002J\u0006\u0010+\u001a\u00020,J\u0010\u0010-\u001a\u0004\u0018\u00010\u00112\u0006\u0010.\u001a\u00020\u0011J\u0016\u0010/\u001a\u00020\u000f2\u0006\u0010.\u001a\u00020\u00112\u0006\u0010)\u001a\u00020\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u001b\u0010\u000b\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\n\u001a\u0004\b\f\u0010\b\u00a8\u00061"}, d2 = {"Lcom/apulse/redaction/DataEncryptionService;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "encryptedPrefs", "error/NonExistentClass", "getEncryptedPrefs", "()Lerror/NonExistentClass;", "encryptedPrefs$delegate", "Lkotlin/Lazy;", "masterKey", "getMasterKey", "masterKey$delegate", "clearSecureStorage", "", "createDataDigest", "", "data", "", "decryptSensitiveHeaders", "", "encryptedHeaders", "Lcom/apulse/redaction/EncryptedData;", "decryptString", "encryptedData", "encryptRequestBody", "body", "encryptSensitiveHeaders", "headers", "encryptString", "plainText", "generateEncryptionKey", "generateSecureToken", "length", "", "getEncryptionInfo", "Lcom/apulse/redaction/EncryptionInfo;", "getSecretKey", "Ljavax/crypto/SecretKey;", "hashSensitiveValue", "value", "initializeEncryptionKey", "isEncryptionAvailable", "", "securelyRetrieveApiKey", "key", "securelyStoreApiKey", "Companion", "app_release"})
public final class DataEncryptionService {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String KEYSTORE_ALIAS = "apulse_encryption_key";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String TRANSFORMATION = "AES/GCM/NoPadding";
    @org.jetbrains.annotations.NotNull
    private static final java.lang.String ANDROID_KEYSTORE = "AndroidKeyStore";
    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy masterKey$delegate = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy encryptedPrefs$delegate = null;
    @org.jetbrains.annotations.NotNull
    public static final com.apulse.redaction.DataEncryptionService.Companion Companion = null;
    
    @javax.inject.Inject
    public DataEncryptionService(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    private final error.NonExistentClass getMasterKey() {
        return null;
    }
    
    private final error.NonExistentClass getEncryptedPrefs() {
        return null;
    }
    
    private final void initializeEncryptionKey() {
    }
    
    private final void generateEncryptionKey() {
    }
    
    private final javax.crypto.SecretKey getSecretKey() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.redaction.EncryptedData encryptString(@org.jetbrains.annotations.NotNull
    java.lang.String plainText) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String decryptString(@org.jetbrains.annotations.NotNull
    com.apulse.redaction.EncryptedData encryptedData) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, com.apulse.redaction.EncryptedData> encryptSensitiveHeaders(@org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, java.lang.String> headers) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.String> decryptSensitiveHeaders(@org.jetbrains.annotations.NotNull
    java.util.Map<java.lang.String, com.apulse.redaction.EncryptedData> encryptedHeaders) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.redaction.EncryptedData encryptRequestBody(@org.jetbrains.annotations.NotNull
    java.lang.String body) {
        return null;
    }
    
    public final void securelyStoreApiKey(@org.jetbrains.annotations.NotNull
    java.lang.String key, @org.jetbrains.annotations.NotNull
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String securelyRetrieveApiKey(@org.jetbrains.annotations.NotNull
    java.lang.String key) {
        return null;
    }
    
    public final void clearSecureStorage() {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String generateSecureToken(int length) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String hashSensitiveValue(@org.jetbrains.annotations.NotNull
    java.lang.String value) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String createDataDigest(@org.jetbrains.annotations.NotNull
    byte[] data) {
        return null;
    }
    
    public final boolean isEncryptionAvailable() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.redaction.EncryptionInfo getEncryptionInfo() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/apulse/redaction/DataEncryptionService$Companion;", "", "()V", "ANDROID_KEYSTORE", "", "GCM_IV_LENGTH", "", "GCM_TAG_LENGTH", "KEYSTORE_ALIAS", "TRANSFORMATION", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}