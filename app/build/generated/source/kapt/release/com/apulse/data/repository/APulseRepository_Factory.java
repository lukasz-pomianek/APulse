package com.apulse.data.repository;

import com.apulse.data.db.APulseDatabase;
import com.apulse.service.SessionManager;
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
public final class APulseRepository_Factory implements Factory<APulseRepository> {
  private final Provider<APulseDatabase> databaseProvider;

  private final Provider<SessionManager> sessionManagerProvider;

  public APulseRepository_Factory(Provider<APulseDatabase> databaseProvider,
      Provider<SessionManager> sessionManagerProvider) {
    this.databaseProvider = databaseProvider;
    this.sessionManagerProvider = sessionManagerProvider;
  }

  @Override
  public APulseRepository get() {
    return newInstance(databaseProvider.get(), sessionManagerProvider.get());
  }

  public static APulseRepository_Factory create(Provider<APulseDatabase> databaseProvider,
      Provider<SessionManager> sessionManagerProvider) {
    return new APulseRepository_Factory(databaseProvider, sessionManagerProvider);
  }

  public static APulseRepository newInstance(APulseDatabase database,
      SessionManager sessionManager) {
    return new APulseRepository(database, sessionManager);
  }
}
