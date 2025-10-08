package com.apulse.ui.settings;

import com.apulse.capture.interceptor.CaptureSettings;
import com.apulse.data.db.APulseDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<CaptureSettings> captureSettingsProvider;

  private final Provider<APulseDatabase> databaseProvider;

  public SettingsViewModel_Factory(Provider<CaptureSettings> captureSettingsProvider,
      Provider<APulseDatabase> databaseProvider) {
    this.captureSettingsProvider = captureSettingsProvider;
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(captureSettingsProvider.get(), databaseProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<CaptureSettings> captureSettingsProvider,
      Provider<APulseDatabase> databaseProvider) {
    return new SettingsViewModel_Factory(captureSettingsProvider, databaseProvider);
  }

  public static SettingsViewModel newInstance(CaptureSettings captureSettings,
      APulseDatabase database) {
    return new SettingsViewModel(captureSettings, database);
  }
}
