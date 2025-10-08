package com.apulse.export;

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
public final class ShareService_Factory implements Factory<ShareService> {
  private final Provider<Context> contextProvider;

  private final Provider<ExportService> exportServiceProvider;

  public ShareService_Factory(Provider<Context> contextProvider,
      Provider<ExportService> exportServiceProvider) {
    this.contextProvider = contextProvider;
    this.exportServiceProvider = exportServiceProvider;
  }

  @Override
  public ShareService get() {
    return newInstance(contextProvider.get(), exportServiceProvider.get());
  }

  public static ShareService_Factory create(Provider<Context> contextProvider,
      Provider<ExportService> exportServiceProvider) {
    return new ShareService_Factory(contextProvider, exportServiceProvider);
  }

  public static ShareService newInstance(Context context, ExportService exportService) {
    return new ShareService(context, exportService);
  }
}
