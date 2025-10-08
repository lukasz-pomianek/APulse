package com.apulse.di;

import com.apulse.data.db.APulseDatabase;
import com.apulse.data.db.RequestBodyDao;
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
public final class DatabaseModule_ProvideRequestBodyDaoFactory implements Factory<RequestBodyDao> {
  private final Provider<APulseDatabase> databaseProvider;

  public DatabaseModule_ProvideRequestBodyDaoFactory(Provider<APulseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public RequestBodyDao get() {
    return provideRequestBodyDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideRequestBodyDaoFactory create(
      Provider<APulseDatabase> databaseProvider) {
    return new DatabaseModule_ProvideRequestBodyDaoFactory(databaseProvider);
  }

  public static RequestBodyDao provideRequestBodyDao(APulseDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRequestBodyDao(database));
  }
}
