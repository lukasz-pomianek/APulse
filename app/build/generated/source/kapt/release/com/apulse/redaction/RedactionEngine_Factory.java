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
public final class RedactionEngine_Factory implements Factory<RedactionEngine> {
  private final Provider<Context> contextProvider;

  public RedactionEngine_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public RedactionEngine get() {
    return newInstance(contextProvider.get());
  }

  public static RedactionEngine_Factory create(Provider<Context> contextProvider) {
    return new RedactionEngine_Factory(contextProvider);
  }

  public static RedactionEngine newInstance(Context context) {
    return new RedactionEngine(context);
  }
}
