package com.apulse.di;

import com.apulse.data.db.APulseDatabase;
import com.apulse.data.db.ResponseBodyDao;
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
public final class DatabaseModule_ProvideResponseBodyDaoFactory implements Factory<ResponseBodyDao> {
  private final Provider<APulseDatabase> databaseProvider;

  public DatabaseModule_ProvideResponseBodyDaoFactory(Provider<APulseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ResponseBodyDao get() {
    return provideResponseBodyDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideResponseBodyDaoFactory create(
      Provider<APulseDatabase> databaseProvider) {
    return new DatabaseModule_ProvideResponseBodyDaoFactory(databaseProvider);
  }

  public static ResponseBodyDao provideResponseBodyDao(APulseDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideResponseBodyDao(database));
  }
}
