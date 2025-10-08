package com.apulse.capture.interceptor;

import android.content.Context;
import com.apulse.redaction.RedactionEngine;
import com.apulse.redaction.SecurityPolicyManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class CaptureSettings_Factory implements Factory<CaptureSettings> {
  private final Provider<Context> contextProvider;

  private final Provider<RedactionEngine> redactionEngineProvider;

  private final Provider<SecurityPolicyManager> securityPolicyManagerProvider;

  public CaptureSettings_Factory(Provider<Context> contextProvider,
      Provider<RedactionEngine> redactionEngineProvider,
      Provider<SecurityPolicyManager> securityPolicyManagerProvider) {
    this.contextProvider = contextProvider;
    this.redactionEngineProvider = redactionEngineProvider;
    this.securityPolicyManagerProvider = securityPolicyManagerProvider;
  }

  @Override
  public CaptureSettings get() {
    return newInstance(contextProvider.get(), redactionEngineProvider.get(), securityPolicyManagerProvider.get());
  }

  public static CaptureSettings_Factory create(Provider<Context> contextProvider,
      Provider<RedactionEngine> redactionEngineProvider,
      Provider<SecurityPolicyManager> securityPolicyManagerProvider) {
    return new CaptureSettings_Factory(contextProvider, redactionEngineProvider, securityPolicyManagerProvider);
  }

  public static CaptureSettings newInstance(Context context, RedactionEngine redactionEngine,
      SecurityPolicyManager securityPolicyManager) {
    return new CaptureSettings(context, redactionEngine, securityPolicyManager);
  }
}
