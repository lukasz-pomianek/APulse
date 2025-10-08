package com.apulse.service;

import com.apulse.capture.interceptor.CaptureSettings;
import com.apulse.data.db.APulseDatabase;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class NetworkCaptureService_MembersInjector implements MembersInjector<NetworkCaptureService> {
  private final Provider<APulseDatabase> databaseProvider;

  private final Provider<CaptureSettings> captureSettingsProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  public NetworkCaptureService_MembersInjector(Provider<APulseDatabase> databaseProvider,
      Provider<CaptureSettings> captureSettingsProvider,
      Provider<SessionManager> sessionManagerProvider) {
    this.databaseProvider = databaseProvider;
    this.captureSettingsProvider = captureSettingsProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  public static MembersInjector<NetworkCaptureService> create(
      Provider<APulseDatabase> databaseProvider, Provider<CaptureSettings> captureSettingsProvider,
      Provider<SessionManager> sessionManagerProvider) {
    return new NetworkCaptureService_MembersInjector(databaseProvider, captureSettingsProvider, sessionManagerProvider);
  }

  @Override
  public void injectMembers(NetworkCaptureService instance) {
    injectDatabase(instance, databaseProvider.get());
    injectCaptureSettings(instance, captureSettingsProvider.get());
    injectSessionManager(instance, sessionManagerProvider.get());
  }

  @InjectedFieldSignature("com.apulse.service.NetworkCaptureService.database")
  public static void injectDatabase(NetworkCaptureService instance, APulseDatabase database) {
    instance.database = database;
  }

  @InjectedFieldSignature("com.apulse.service.NetworkCaptureService.captureSettings")
  public static void injectCaptureSettings(NetworkCaptureService instance,
      CaptureSettings captureSettings) {
    instance.captureSettings = captureSettings;
  }

  @InjectedFieldSignature("com.apulse.service.NetworkCaptureService.sessionManager")
  public static void injectSessionManager(NetworkCaptureService instance,
      SessionManager sessionManager) {
    instance.sessionManager = sessionManager;
  }
}
