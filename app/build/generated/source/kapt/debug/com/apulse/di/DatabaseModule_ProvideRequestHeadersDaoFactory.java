package com.apulse.di;

import com.apulse.data.db.APulseDatabase;
import com.apulse.data.db.RequestHeadersDao;
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
public final class DatabaseModule_ProvideRequestHeadersDaoFactory implements Factory<RequestHeadersDao> {
  private final Provider<APulseDatabase> databaseProvider;

  public DatabaseModule_ProvideRequestHeadersDaoFactory(Provider<APulseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public RequestHeadersDao get() {
    return provideRequestHeadersDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideRequestHeadersDaoFactory create(
      Provider<APulseDatabase> databaseProvider) {
    return new DatabaseModule_ProvideRequestHeadersDaoFactory(databaseProvider);
  }

  public static RequestHeadersDao provideRequestHeadersDao(APulseDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRequestHeadersDao(database));
  }
}
