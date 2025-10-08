package com.apulse.redaction;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class SecurityPolicyManager_Factory implements Factory<SecurityPolicyManager> {
  private final Provider<Context> contextProvider;

  private final Provider<RedactionEngine> redactionEngineProvider;

  public SecurityPolicyManager_Factory(Provider<Context> contextProvider,
      Provider<RedactionEngine> redactionEngineProvider) {
    this.contextProvider = contextProvider;
    this.redactionEngineProvider = redactionEngineProvider;
  }

  @Override
  public SecurityPolicyManager get() {
    return newInstance(contextProvider.get(), redactionEngineProvider.get());
  }

  public static SecurityPolicyManager_Factory create(Provider<Context> contextProvider,
      Provider<RedactionEngine> redactionEngineProvider) {
    return new SecurityPolicyManager_Factory(contextProvider, redactionEngineProvider);
  }

  public static SecurityPolicyManager newInstance(Context context,
      RedactionEngine redactionEngine) {
    return new SecurityPolicyManager(context, redactionEngine);
  }
}
