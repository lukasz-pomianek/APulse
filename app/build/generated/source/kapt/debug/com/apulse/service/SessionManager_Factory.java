package com.apulse.service;

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
public final class SessionManager_Factory implements Factory<SessionManager> {
  private final Provider<APulseDatabase> databaseProvider;

  public SessionManager_Factory(Provider<APulseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SessionManager get() {
    return newInstance(databaseProvider.get());
  }

  public static SessionManager_Factory create(Provider<APulseDatabase> databaseProvider) {
    return new SessionManager_Factory(databaseProvider);
  }

  public static SessionManager newInstance(APulseDatabase database) {
    return new SessionManager(database);
  }
}
