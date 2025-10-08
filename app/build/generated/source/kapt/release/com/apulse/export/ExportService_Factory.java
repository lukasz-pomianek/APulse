package com.apulse.export;

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
public final class ExportService_Factory implements Factory<ExportService> {
  private final Provider<APulseDatabase> databaseProvider;

  private final Provider<Context> contextProvider;

  public ExportService_Factory(Provider<APulseDatabase> databaseProvider,
      Provider<Context> contextProvider) {
    this.databaseProvider = databaseProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public ExportService get() {
    return newInstance(databaseProvider.get(), contextProvider.get());
  }

  public static ExportService_Factory create(Provider<APulseDatabase> databaseProvider,
      Provider<Context> contextProvider) {
    return new ExportService_Factory(databaseProvider, contextProvider);
  }

  public static ExportService newInstance(APulseDatabase database, Context context) {
    return new ExportService(database, context);
  }
}
