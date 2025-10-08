package com.apulse.ui.sessions;

import com.apulse.data.db.APulseDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SessionListViewModel_Factory implements Factory<SessionListViewModel> {
  private final Provider<APulseDatabase> databaseProvider;

  public SessionListViewModel_Factory(Provider<APulseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public SessionListViewModel get() {
    return newInstance(databaseProvider.get());
  }

  public static SessionListViewModel_Factory create(Provider<APulseDatabase> databaseProvider) {
    return new SessionListViewModel_Factory(databaseProvider);
  }

  public static SessionListViewModel newInstance(APulseDatabase database) {
    return new SessionListViewModel(database);
  }
}
