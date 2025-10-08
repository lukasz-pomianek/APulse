package com.apulse.export.model;

@kotlinx.serialization.Serializable
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0087\b\u0018\u0000 ;2\u00020\u0001:\u0002:;B_\b\u0011\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\u0002\u0010\u0014BU\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0002\u0010\u0015J\t\u0010$\u001a\u00020\u0005H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\u000b\u0010\'\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\rH\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u000fH\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0011H\u00c6\u0003J[\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\r2\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u00c6\u0001J\u0013\u0010,\u001a\u00020-2\b\u0010.\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010/\u001a\u00020\u0003H\u00d6\u0001J\t\u00100\u001a\u000201H\u00d6\u0001J&\u00102\u001a\u0002032\u0006\u00104\u001a\u00020\u00002\u0006\u00105\u001a\u0002062\u0006\u00107\u001a\u000208H\u00c1\u0001\u00a2\u0006\u0002\b9R\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0013\u0010\f\u001a\u0004\u0018\u00010\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010#\u00a8\u0006<"}, d2 = {"Lcom/apulse/export/model/APulseRequestExport;", "", "seen1", "", "request", "Lcom/apulse/data/model/NetworkRequest;", "requestHeaders", "Lcom/apulse/data/model/RequestHeaders;", "responseHeaders", "Lcom/apulse/data/model/ResponseHeaders;", "requestBody", "Lcom/apulse/data/model/RequestBody;", "responseBody", "Lcom/apulse/data/model/ResponseBody;", "appMetadata", "Lcom/apulse/data/model/AppMetadata;", "timing", "Lcom/apulse/export/model/APulseTimingData;", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILcom/apulse/data/model/NetworkRequest;Lcom/apulse/data/model/RequestHeaders;Lcom/apulse/data/model/ResponseHeaders;Lcom/apulse/data/model/RequestBody;Lcom/apulse/data/model/ResponseBody;Lcom/apulse/data/model/AppMetadata;Lcom/apulse/export/model/APulseTimingData;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Lcom/apulse/data/model/NetworkRequest;Lcom/apulse/data/model/RequestHeaders;Lcom/apulse/data/model/ResponseHeaders;Lcom/apulse/data/model/RequestBody;Lcom/apulse/data/model/ResponseBody;Lcom/apulse/data/model/AppMetadata;Lcom/apulse/export/model/APulseTimingData;)V", "getAppMetadata", "()Lcom/apulse/data/model/AppMetadata;", "getRequest", "()Lcom/apulse/data/model/NetworkRequest;", "getRequestBody", "()Lcom/apulse/data/model/RequestBody;", "getRequestHeaders", "()Lcom/apulse/data/model/RequestHeaders;", "getResponseBody", "()Lcom/apulse/data/model/ResponseBody;", "getResponseHeaders", "()Lcom/apulse/data/model/ResponseHeaders;", "getTiming", "()Lcom/apulse/export/model/APulseTimingData;", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "toString", "", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "write$Self$app_release", "$serializer", "Companion", "app_release"})
public final class APulseRequestExport {
    @org.jetbrains.annotations.NotNull
    private final com.apulse.data.model.NetworkRequest request = null;
    @org.jetbrains.annotations.Nullable
    private final com.apulse.data.model.RequestHeaders requestHeaders = null;
    @org.jetbrains.annotations.Nullable
    private final com.apulse.data.model.ResponseHeaders responseHeaders = null;
    @org.jetbrains.annotations.Nullable
    private final com.apulse.data.model.RequestBody requestBody = null;
    @org.jetbrains.annotations.Nullable
    private final com.apulse.data.model.ResponseBody responseBody = null;
    @org.jetbrains.annotations.Nullable
    private final com.apulse.data.model.AppMetadata appMetadata = null;
    @org.jetbrains.annotations.Nullable
    private final com.apulse.export.model.APulseTimingData timing = null;
    @org.jetbrains.annotations.NotNull
    public static final com.apulse.export.model.APulseRequestExport.Companion Companion = null;
    
    public APulseRequestExport(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.NetworkRequest request, @org.jetbrains.annotations.Nullable
    com.apulse.data.model.RequestHeaders requestHeaders, @org.jetbrains.annotations.Nullable
    com.apulse.data.model.ResponseHeaders responseHeaders, @org.jetbrains.annotations.Nullable
    com.apulse.data.model.RequestBody requestBody, @org.jetbrains.annotations.Nullable
    com.apulse.data.model.ResponseBody responseBody, @org.jetbrains.annotations.Nullable
    com.apulse.data.model.AppMetadata appMetadata, @org.jetbrains.annotations.Nullable
    com.apulse.export.model.APulseTimingData timing) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.data.model.NetworkRequest getRequest() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.apulse.data.model.RequestHeaders getRequestHeaders() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.apulse.data.model.ResponseHeaders getResponseHeaders() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.apulse.data.model.RequestBody getRequestBody() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.apulse.data.model.ResponseBody getResponseBody() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.apulse.data.model.AppMetadata getAppMetadata() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.apulse.export.model.APulseTimingData getTiming() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.data.model.NetworkRequest component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.apulse.data.model.RequestHeaders component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.apulse.data.model.ResponseHeaders component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.apulse.data.model.RequestBody component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.apulse.data.model.ResponseBody component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.apulse.data.model.AppMetadata component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.apulse.export.model.APulseTimingData component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.apulse.export.model.APulseRequestExport copy(@org.jetbrains.annotations.NotNull
    com.apulse.data.model.NetworkRequest request, @org.jetbrains.annotations.Nullable
    com.apulse.data.model.RequestHeaders requestHeaders, @org.jetbrains.annotations.Nullable
    com.apulse.data.model.ResponseHeaders responseHeaders, @org.jetbrains.annotations.Nullable
    com.apulse.data.model.RequestBody requestBody, @org.jetbrains.annotations.Nullable
    com.apulse.data.model.ResponseBody responseBody, @org.jetbrains.annotations.Nullable
    com.apulse.data.model.AppMetadata appMetadata, @org.jetbrains.annotations.Nullable
    com.apulse.export.model.APulseTimingData timing) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.jvm.JvmStatic
    public static final void write$Self$app_release(@org.jetbrains.annotations.NotNull
    com.apulse.export.model.APulseRequestExport self, @org.jetbrains.annotations.NotNull
    kotlinx.serialization.encoding.CompositeEncoder output, @org.jetbrains.annotations.NotNull
    kotlinx.serialization.descriptors.SerialDescriptor serialDesc) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tH\u00d6\u0001\u00a2\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002H\u00d6\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VX\u00d6\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"com/apulse/export/model/APulseRequestExport.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Lcom/apulse/export/model/APulseRequestExport;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_release"})
    @java.lang.Deprecated
    public static final class $serializer implements kotlinx.serialization.internal.GeneratedSerializer<com.apulse.export.model.APulseRequestExport> {
        @org.jetbrains.annotations.NotNull
        public static final com.apulse.export.model.APulseRequestExport.$serializer INSTANCE = null;
        
        private $serializer() {
            super();
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public kotlinx.serialization.KSerializer<?>[] childSerializers() {
            return null;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public com.apulse.export.model.APulseRequestExport deserialize(@org.jetbrains.annotations.NotNull
        kotlinx.serialization.encoding.Decoder decoder) {
            return null;
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public kotlinx.serialization.descriptors.SerialDescriptor getDescriptor() {
            return null;
        }
        
        @java.lang.Override
        public void serialize(@org.jetbrains.annotations.NotNull
        kotlinx.serialization.encoding.Encoder encoder, @org.jetbrains.annotations.NotNull
        com.apulse.export.model.APulseRequestExport value) {
        }
        
        @java.lang.Override
        @org.jetbrains.annotations.NotNull
        public kotlinx.serialization.KSerializer<?>[] typeParametersSerializers() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00c6\u0001\u00a8\u0006\u0006"}, d2 = {"Lcom/apulse/export/model/APulseRequestExport$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Lcom/apulse/export/model/APulseRequestExport;", "app_release"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final kotlinx.serialization.KSerializer<com.apulse.export.model.APulseRequestExport> serializer() {
            return null;
        }
    }
}