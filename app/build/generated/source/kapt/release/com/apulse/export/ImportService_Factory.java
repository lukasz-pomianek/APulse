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
public final class ImportService_Factory implements Factory<ImportService> {
  private final Provider<APulseDatabase> databaseProvider;

  private final Provider<Context> contextProvider;

  public ImportService_Factory(Provider<APulseDatabase> databaseProvider,
      Provider<Context> contextProvider) {
    this.databaseProvider = databaseProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public ImportService get() {
    return newInstance(databaseProvider.get(), contextProvider.get());
  }

  public static ImportService_Factory create(Provider<APulseDatabase> databaseProvider,
      Provider<Context> contextProvider) {
    return new ImportService_Factory(databaseProvider, contextProvider);
  }

  public static ImportService newInstance(APulseDatabase database, Context context) {
    return new ImportService(database, context);
  }
}
