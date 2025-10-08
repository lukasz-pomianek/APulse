package com.apulse.di;

import com.apulse.data.db.APulseDatabase;
import com.apulse.data.db.AppMetadataDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideAppMetadataDaoFactory implements Factory<AppMetadataDao> {
  private final Provider<APulseDatabase> databaseProvider;

  public DatabaseModule_ProvideAppMetadataDaoFactory(Provider<APulseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public AppMetadataDao get() {
    return provideAppMetadataDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideAppMetadataDaoFactory create(
      Provider<APulseDatabase> databaseProvider) {
    return new DatabaseModule_ProvideAppMetadataDaoFactory(databaseProvider);
  }

  public static AppMetadataDao provideAppMetadataDao(APulseDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAppMetadataDao(database));
  }
}
