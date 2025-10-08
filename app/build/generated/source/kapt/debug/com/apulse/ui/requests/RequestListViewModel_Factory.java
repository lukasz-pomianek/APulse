package com.apulse.ui.requests;

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
public final class RequestListViewModel_Factory implements Factory<RequestListViewModel> {
  private final Provider<APulseDatabase> databaseProvider;

  public RequestListViewModel_Factory(Provider<APulseDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public RequestListViewModel get() {
    return newInstance(databaseProvider.get());
  }

  public static RequestListViewModel_Factory create(Provider<APulseDatabase> databaseProvider) {
    return new RequestListViewModel_Factory(databaseProvider);
  }

  public static RequestListViewModel newInstance(APulseDatabase database) {
    return new RequestListViewModel(database);
  }
}
