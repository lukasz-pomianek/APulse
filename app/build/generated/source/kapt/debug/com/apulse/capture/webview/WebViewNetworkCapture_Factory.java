package com.apulse.capture.webview;

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
public final class WebViewNetworkCapture_Factory implements Factory<WebViewNetworkCapture> {
  private final Provider<APulseDatabase> databaseProvider;

  private final Provider<CaptureSettings> captureSettingsProvider;

  public WebViewNetworkCapture_Factory(Provider<APulseDatabase> databaseProvider,
      Provider<CaptureSettings> captureSettingsProvider) {
    this.databaseProvider = databaseProvider;
    this.captureSettingsProvider = captureSettingsProvider;
  }

  @Override
  public WebViewNetworkCapture get() {
    return newInstance(databaseProvider.get(), captureSettingsProvider.get());
  }

  public static WebViewNetworkCapture_Factory create(Provider<APulseDatabase> databaseProvider,
      Provider<CaptureSettings> captureSettingsProvider) {
    return new WebViewNetworkCapture_Factory(databaseProvider, captureSettingsProvider);
  }

  public static WebViewNetworkCapture newInstance(APulseDatabase database,
      CaptureSettings captureSettings) {
    return new WebViewNetworkCapture(database, captureSettings);
  }
}
