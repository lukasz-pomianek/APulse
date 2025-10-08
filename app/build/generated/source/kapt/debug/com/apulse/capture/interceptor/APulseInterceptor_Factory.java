package com.apulse.capture.interceptor;

import android.content.Context;
import com.apulse.data.db.APulseDatabase;
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
public final class APulseInterceptor_Factory implements Factory<APulseInterceptor> {
  private final Provider<Context> contextProvider;

  private final Provider<APulseDatabase> databaseProvider;

  private final Provider<CaptureSettings> captureSettingsProvider;

  public APulseInterceptor_Factory(Provider<Context> contextProvider,
      Provider<APulseDatabase> databaseProvider,
      Provider<CaptureSettings> captureSettingsProvider) {
    this.contextProvider = contextProvider;
    this.databaseProvider = databaseProvider;
    this.captureSettingsProvider = captureSettingsProvider;
  }

  @Override
  public APulseInterceptor get() {
    return newInstance(contextProvider.get(), databaseProvider.get(), captureSettingsProvider.get());
  }

  public static APulseInterceptor_Factory create(Provider<Context> contextProvider,
      Provider<APulseDatabase> databaseProvider,
      Provider<CaptureSettings> captureSettingsProvider) {
    return new APulseInterceptor_Factory(contextProvider, databaseProvider, captureSettingsProvider);
  }

  public static APulseInterceptor newInstance(Context context, APulseDatabase database,
      CaptureSettings captureSettings) {
    return new APulseInterceptor(context, database, captureSettings);
  }
}
