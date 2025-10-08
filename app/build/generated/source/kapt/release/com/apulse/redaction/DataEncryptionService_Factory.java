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
public final class DataEncryptionService_Factory implements Factory<DataEncryptionService> {
  private final Provider<Context> contextProvider;

  public DataEncryptionService_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DataEncryptionService get() {
    return newInstance(contextProvider.get());
  }

  public static DataEncryptionService_Factory create(Provider<Context> contextProvider) {
    return new DataEncryptionService_Factory(contextProvider);
  }

  public static DataEncryptionService newInstance(Context context) {
    return new DataEncryptionService(context);
  }
}
