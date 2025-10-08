package com.apulse.di;

import com.apulse.data.db.APulseDatabase;
import com.apulse.data.db.NetworkRequestDao;
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
public final class DatabaseModule_ProvideNetworkRequestDaoFactory implements Factory<NetworkRequestDao> {
  private final Provider<APulseDatabase> databaseProvider;

  public DatabaseModule_ProvideNetworkRequestDaoFactory(Provider<APulseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public NetworkRequestDao get() {
    return provideNetworkRequestDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideNetworkRequestDaoFactory create(
      Provider<APulseDatabase> databaseProvider) {
    return new DatabaseModule_ProvideNetworkRequestDaoFactory(databaseProvider);
  }

  public static NetworkRequestDao provideNetworkRequestDao(APulseDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideNetworkRequestDao(database));
  }
}
